package ch.i4ds.helio.workflows;

import java.io.*;
import java.util.*;
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
   * Gets a list of files from HESSI over a specified time period.
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
    /*
     * This code is a 1:1 implementation of Anja LeBlanc's initial workflow. Check the Taverna
     * workflow file to see a visual representation of this workflow.
     * 
     */
    
    //get events from the goes event list (via HEC)
    String goes = new String("");
    String sql_base = "SELECT * FROM goes_xray_flare WHERE  time_start>='%start_date%' AND time_start<'%stop_date%' %goes% ORDER BY ntime_start;";
    String sql_string = sql_base.replace("%start_date%",_dateFrom);
    sql_string = sql_string.replace("%stop_date%",_dateTo);
    if(_GOESmin.length() > 0) {
      goes = goes.concat(" AND xray_class > '"+_GOESmin+"'");
    }
    if (_GOESmax.length() > 0) {
       goes = goes.concat(" AND xray_class < '"+_GOESmax+"'");
    }
    sql_string = sql_string.replace("%goes%",goes);
    String sql_output=new HECService().getHECPort().sql(sql_string);
    
    //parse the results
    List<String> startDates=new ArrayList<String>();
    List<String> endDates=new ArrayList<String>();
    List<Integer> positions=new ArrayList<Integer>();
    InitialWorkflowHelpers.getAllEventDates(sql_output,startDates,endDates,positions);
    
    List<String> startDate=new ArrayList<String>();
    List<String> endDate=new ArrayList<String>();
    InitialWorkflowHelpers.datesSM(sql_output,startDate,endDate);
    
    
    //query the three available data sources
    List<String> query_v1_hessi_ec=new ArrayList<String>();
    List<String> query_v1_phoenix2=new ArrayList<String>();
    List<String> query_v1_solarmonitor=new ArrayList<String>();
    
    FrontendFacade port=new FrontendFacadeService().getFrontendFacadePort();
    ObjectFactory of=new ObjectFactory();
    JAXBContext jc=JAXBContext.newInstance(QueryV1Response.class);
    Marshaller m=jc.createMarshaller();
    QueryV1Response res;
    StringWriter sw;
    for(int i=0;i<startDates.size();i++)
    {
      res=of.createQueryV1Response();
      res.getReturn().addAll(port.queryV1("hessi-ec",startDates.get(i),endDates.get(i),0));
      sw=new StringWriter();
      m.marshal(res,sw);
      query_v1_hessi_ec.add(sw.toString());
      sw.close();
      
      res=of.createQueryV1Response();
      res.getReturn().addAll(port.queryV1("phoenix2",startDates.get(i),endDates.get(i),0));
      sw=new StringWriter();
      m.marshal(res,sw);
      query_v1_phoenix2.add(sw.toString());
      sw.close();
    }
    
    for(int i=0;i<startDate.size();i++)
    {
      res=of.createQueryV1Response();
      res.getReturn().addAll(port.queryV1("sm-seit",startDate.get(i),endDate.get(i),0));
      sw=new StringWriter();
      m.marshal(res,sw);
      query_v1_solarmonitor.add(sw.toString());
      sw.close();
    }
    
    //call the combineData beanshell script
    return InitialWorkflowHelpers.combineData(query_v1_hessi_ec,query_v1_phoenix2,positions,query_v1_solarmonitor,sql_output);
  }
}
