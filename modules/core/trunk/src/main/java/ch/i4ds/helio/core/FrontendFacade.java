package ch.i4ds.helio.core;

import java.util.*;
import javax.jws.*;
import ch.i4ds.helio.dpas.*;

/**
 * Facade for the web sites. This class contains various convenience functions
 * which the web sites will call to get their information. The functions load
 * the appropriate Taverna workflow, execute it and then return the results in
 * a convenient fashion.
 * 
 * @author Simon Felix (de@iru.ch)
 */
@WebService
public class FrontendFacade
{
  private TavernaExecutor taverna;
  private QueryService query;
  
  @WebMethod(exclude=true)
  public void setTaverna(TavernaExecutor _taverna)
  {
    taverna=_taverna;
  }
  
  @WebMethod(exclude=true)
  public void setQueryService(QueryService _query)
  {
    query=_query;
  }
  
  
  
  /**
   * Gives a list of supported instruments when querying.
   * 
   * @return List of names (strings) of the supported instruments
   */
  @WebMethod(operationName="get_instruments_v1")
  public String[] getInstruments()
  {
    return query.getInstruments();
  }
  
  
  
  /**
   * Queries the HELIO DPAS. All results will be sorted by date.
   * 
   * @param _instrument The instrument to query
   * @param _dateFrom The beginning of the time period to search (2001-01-31 00:00:00)
   * @param _dateTo The end of the time period to search (2001-01-31 00:00:00)
   * @param _max_results The maximum number of results.
   * @return A list of ResultItems
   * @throws Exception
   */
  @WebMethod(operationName="query_v1")
  public ResultItem[] query(
      @WebParam(name="instrument") String _instrument,
      @WebParam(name="date_from") String _dateFrom,
      @WebParam(name="date_to") String _dateTo,
      @WebParam(name="max_results") int _max_results) throws Exception
  {
    return query.query(_instrument,_dateFrom,_dateTo,_max_results);
  }
  
  
  /**
   * Gets a list of files from HESSI over a specified time period.
   * 
   * @param _dateFrom A string in the following format YYYY-MM-DD HH:MM:SS, specifying the beginning of the time period
   * @param _dateTo A string in the following format YYYY-MM-DD HH:MM:SS, specifying the end of the time period
   * @return A list of URLs in the time period pointing to quickview and FITS files
   */
  @WebMethod(operationName="run_initial_workflow")
  public String runInitialWorkflow(
      @WebParam(name="date_from") String _dateFrom,
      @WebParam(name="date_to") String _dateTo) throws Exception
  {
    //prepare the inputs of the workflow
    Map<String,Object> inputs=new LinkedHashMap<String,Object>();
    inputs.put("start_date",_dateFrom);
    inputs.put("end_date",_dateTo);
    
    //load the workflow and execute it
    Map<String,Object> wf_results=taverna.executeWorkflow(getClass().getResourceAsStream("workflows/initial.t2flow"),inputs);
    
    //get the output of the workflow
    return (String)wf_results.get("out_VOTabel");
  }
  
  /**
   * Gets a list of files from HESSI over a specified time period.
   * 
   * @param _dateFrom A string in the following format YYYY-MM-DD HH:MM:SS, specifying the beginning of the time period
   * @param _dateTo A string in the following format YYYY-MM-DD HH:MM:SS, specifying the end of the time period
   * @return A list of URLs in the time period pointing to quickview and FITS files
   */
  /*@SuppressWarnings("unchecked")
  @WebMethod(operationName="get_hessi_events")
  public HessiService.HessiURLs[] getHessiEvents(
      @WebParam(name="date_from") String _dateFrom,
      @WebParam(name="date_to") String _dateTo) throws Exception
  {
    //prepare the inputs of the workflow
    Map<String,Object> inputs=new LinkedHashMap<String,Object>();
    inputs.put("dateFrom",_dateFrom);
    inputs.put("dateTo",_dateTo);
    
    //load the workflow and execute it
    Map<String,Object> wf_results=taverna.executeWorkflow(getClass().getResourceAsStream("workflows/hessi-get-filelist.t2flow"),inputs);
    
    //get the output of the workflow
    List<String> stringlist=(List<String>)wf_results.get("list");
    
    //convert the output of the workflow from XML to pojo's
    HessiService.HessiURLs[] results=new HessiService.HessiURLs[stringlist.size()];
    
    DocumentBuilderFactory domFactory=DocumentBuilderFactory.newInstance();
    domFactory.setNamespaceAware(true);
    
    XPath xpath=XPathFactory.newInstance().newXPath();
    XPathExpression selectMeasurementStart=xpath.compile("/return/measurementStart");
    XPathExpression selectUrlFITS=xpath.compile("/return/urlFITS");
    XPathExpression selectUrlCorrectedRate=xpath.compile("/return/urlCorrectedRate");
    XPathExpression selectUrlFrontRate=xpath.compile("/return/urlFrontRate");
    XPathExpression selectUrlPartRate=xpath.compile("/return/urlPartRate");
    XPathExpression selectUrlRate=xpath.compile("/return/urlRate");
    XPathExpression selectUrlRearRate=xpath.compile("/return/urlRearRate");
    
    //go trough all results and convert every single one
    for(int i=0;i<stringlist.size();i++)
    {
      Document doc=domFactory.newDocumentBuilder().parse(new StringInputStream(stringlist.get(i)));
      
      results[i]=new HessiService.HessiURLs();
      
      Calendar c=Calendar.getInstance();
      c.setTime(DateUtils.parseIso8601DateTime((String)selectMeasurementStart.evaluate(doc,XPathConstants.STRING)));
      results[i].measurementStart=c;
      
      results[i].urlFITS=(String)selectUrlFITS.evaluate(doc,XPathConstants.STRING);
      results[i].urlCorrectedRate=(String)selectUrlCorrectedRate.evaluate(doc,XPathConstants.STRING);
      results[i].urlFrontRate=(String)selectUrlFrontRate.evaluate(doc,XPathConstants.STRING);
      results[i].urlPartRate=(String)selectUrlPartRate.evaluate(doc,XPathConstants.STRING);
      results[i].urlRate=(String)selectUrlRate.evaluate(doc,XPathConstants.STRING);
      results[i].urlRearRate=(String)selectUrlRearRate.evaluate(doc,XPathConstants.STRING);
    }
    
    return results;
  }*/
}
