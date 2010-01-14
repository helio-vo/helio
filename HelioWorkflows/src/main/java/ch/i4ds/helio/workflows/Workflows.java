package ch.i4ds.helio.workflows;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import javax.jws.*;
import javax.xml.bind.*;
import ws.clients.FrontendServices.*;
import ws.clients.HEC.HECService;

/**
 * Interface to all integrated, hard-coded workflows
 * 
 * @author Simon Felix (de@iru.ch)
 */
@WebService
public class Workflows
{
  /**
   * Gets a list of files from HESSI over a specified time period. This workflow is a Java
   * conversion of the Taverna2 workflow created by Anja LeBlanc.
   * 
   * @param _dateFrom A string in the following format YYYY-MM-DD HH:MM:SS, specifying the beginning of the time period
   * @param _dateTo A string in the following format YYYY-MM-DD HH:MM:SS, specifying the end of the time period
   * @param _GOESmin The lower bound of the GOES class or empty string "" if no filtering should be done
   * @param _GOESmax The upper bound of the GOES class or empty string "" if no filtering should be done
   * @param _serviceLocation The address of the services to be called from the workflow
   * @return A list of URLs in the time period pointing to quickview and FITS files
   */
  @WebMethod(operationName="run_initial_workflow")
  public String runInitialWorkflow(
      @WebParam(name="date_from") String _dateFrom,
      @WebParam(name="date_to") String _dateTo,
      @WebParam(name="goes_min") String _GOESmin,
      @WebParam(name="goes_max") String _GOESmax,
      @WebParam(name="service_location") String _serviceLocation) throws java.lang.Exception
  {
    //prepare the query to get events from the goes event list (via HEC)
    String goes="";
    String sql_base="SELECT * FROM goes_xray_flare WHERE  time_start>='%start_date%' AND time_start<'%stop_date%' %goes% ORDER BY ntime_start;";

    if(_GOESmin.length()>0)
      goes+=" AND xray_class > '"+_GOESmin+"'";
    
    if (_GOESmax.length()>0)
       goes+=" AND xray_class < '"+_GOESmax+"'";
    
    String sql_string=sql_base.replace("%start_date%",_dateFrom).replace("%stop_date%",_dateTo).replace("%goes%",goes);
    
    //query the HEC
    String sql_output=new HECService().getHECPort().sql(sql_string);
    
    //parse the results
    final List<String> startDates=new ArrayList<String>();
    final List<String> endDates=new ArrayList<String>();
    List<Integer> positions=new ArrayList<Integer>();
    InitialWorkflowHelpers.getAllEventDates(sql_output,startDates,endDates,positions);
    
    final List<String> startDate=new ArrayList<String>();
    final List<String> endDate=new ArrayList<String>();
    InitialWorkflowHelpers.datesSM(sql_output,startDate,endDate);
    
    
    //prepare threaded execution
    List<String> query_v1_hessi_ec=new ArrayList<String>();
    List<String> query_v1_phoenix2=new ArrayList<String>();
    List<String> query_v1_solarmonitor=new ArrayList<String>();
    
    Executor ex=Executors.newCachedThreadPool();  
    CompletionService<String> ecs_hessi_ec=new ExecutorCompletionService<String>(ex);    
    CompletionService<String> ecs_phoenix2=new ExecutorCompletionService<String>(ex);    
    CompletionService<String> ecs_solarmonitor=new ExecutorCompletionService<String>(ex);    
    
    
    /**
     * Note that the web service invocation is a bit ugly. This is done in this way so we
     * can get the XML-serialized resutlt of a web service invocation, because the workflow
     * parses "un-deserialized" XML snippets. We therefore call the web service first as usual
     * and then serialize the results again for the workflow. This is done multi-threaded as
     * well and should scale very well (in theory - never tested).
     */
    
    //query the data sources; hessi, phoenix II
    for(int i=0;i<startDates.size();i++)
    {
      final int counter=i;
      ecs_hessi_ec.submit(new Callable<String>(){
        public String call() throws java.lang.Exception
        {
          FrontendFacade port=new FrontendFacadeService().getFrontendFacadePort();
          JAXBContext jc=JAXBContext.newInstance(QueryV1Response.class);
          Marshaller m=jc.createMarshaller();
          QueryV1Response res=new ObjectFactory().createQueryV1Response();
          res.getReturn().addAll(port.queryV1("hessi-ec",startDates.get(counter),endDates.get(counter),0));
          StringWriter sw=new StringWriter();
          m.marshal(res,sw);
          String stringResult=sw.toString();
          sw.close();
          return stringResult;
        }});
      
      ecs_phoenix2.submit(new Callable<String>(){
        public String call() throws java.lang.Exception
        {
          FrontendFacade port=new FrontendFacadeService().getFrontendFacadePort();
          JAXBContext jc=JAXBContext.newInstance(QueryV1Response.class);
          Marshaller m=jc.createMarshaller();
          QueryV1Response res=new ObjectFactory().createQueryV1Response();
          res.getReturn().addAll(port.queryV1("phoenix2",startDates.get(counter),endDates.get(counter),0));
          StringWriter sw=new StringWriter();
          m.marshal(res,sw);
          String stringResult=sw.toString();
          sw.close();
          return stringResult;
        }});
    }
    
    //query solar monitor for quicklook data
    for(int i=0;i<startDate.size();i++)
    {
      final int counter=i;
      ecs_solarmonitor.submit(new Callable<String>(){
        public String call() throws java.lang.Exception
        {
          FrontendFacade port=new FrontendFacadeService().getFrontendFacadePort();
          JAXBContext jc=JAXBContext.newInstance(QueryV1Response.class);
          Marshaller m=jc.createMarshaller();
          QueryV1Response res=new ObjectFactory().createQueryV1Response();
          res.getReturn().addAll(port.queryV1("sm-seit",startDate.get(counter),endDate.get(counter),0));
          StringWriter sw=new StringWriter();
          m.marshal(res,sw);
          String stringResult=sw.toString();
          sw.close();
          return stringResult;
        }});
    }
    
    //collect the results from the asynchronous queries
    for(int i=0;i<startDates.size();i++)
    {
      query_v1_hessi_ec.add(ecs_hessi_ec.take().get());
      query_v1_phoenix2.add(ecs_phoenix2.take().get());
    }
    for(int i=0;i<startDate.size();i++)
      query_v1_solarmonitor.add(ecs_solarmonitor.take().get());
    
    
    //call the combineData beanshell script and return the result
    return InitialWorkflowHelpers.combineData(query_v1_hessi_ec,query_v1_phoenix2,positions,query_v1_solarmonitor,sql_output);
  }
}