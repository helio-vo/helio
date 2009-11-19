package ch.i4ds.helio.core;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.jws.*;

import com.sun.tools.hat.internal.parser.Reader;

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
  /**
   * A list of web service addresses to remap (key=host, value=service location)
   */
  private Map<String,String> remapWebservice;
  
  
  private TavernaExecutor taverna;
  private QueryService query;  
  
  @WebMethod(exclude=true)
  public void setTaverna(TavernaExecutor _taverna)
  {
    taverna=_taverna;
  }
  
  @WebMethod(exclude=true)
  public void setRemapWebservice(Map<String,String> _remapWebservice)
  {
    remapWebservice=_remapWebservice;
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
   * Returns the version number of the backend.
   * 
   * @return Version number
   */
  @WebMethod(operationName="get_version")
  public String getVersion()
  {
    return "Revision 88, Initial workflow v8";
  }
  
  @WebMethod(operationName="get_host_name")
  public String getHostName() throws UnknownHostException
  {
    return InetAddress.getLocalHost().getHostName();
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
  public ResultItem[] query1(
      @WebParam(name="instrument") String _instrument,
      @WebParam(name="date_from") String _dateFrom,
      @WebParam(name="date_to") String _dateTo,
      @WebParam(name="max_results") int _max_results) throws Exception
  {
    return query.query(_instrument,_dateFrom,_dateTo,_max_results);
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
  /*@WebMethod(operationName="query_v2_array")
  public ResultItem[] query2_array(
      @WebParam(name="TIME") String _time,
      @WebParam(name="INSTRUMENT") String _instrument,
      @WebParam(name="WHERE") String _where,
      @WebParam(name="MAXROWS") int _max_results,
      @WebParam(name="STARTINDEX") int _start_index) throws Exception
  {
    String[] timeRange=_time.split("/");
    Calendar from=Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    Calendar to=Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    from.setTime(DateUtils.parseIso8601DateTime(timeRange[0]));
    to.setTime(DateUtils.parseIso8601DateTime(timeRange[1]));
    
    ResultItem[] results=query.query(_instrument,from,to,_max_results+_start_index);
    
    //remove first _start_index elements
    if(_start_index!=0)
      results=Arrays.copyOfRange(results,_start_index,Math.min(results.length,_start_index+_max_results));
    
    //TODO: apply WHERE clause
    
    //return the data in the requested format (by default VOTable)
    return results;
  }
  
  
  @WebMethod(operationName="query_v2_csv")
  public String query2_csv(
      @WebParam(name="TIME") String _time,
      @WebParam(name="INSTRUMENT") String _instrument,
      @WebParam(name="WHERE") String _where,
      @WebParam(name="MAXROWS") int _max_results,
      @WebParam(name="STARTINDEX") int _start_index) throws Exception
  {
    String res=ResultItem.FIELD_NAMES[0];
    for(int i=1;i<ResultItem.FIELD_NAMES.length;i++)
      res+=","+ResultItem.FIELD_NAMES[i];
    res+="\n";
    
    for(ResultItem ri:query2_array(_time,_instrument,_where,_max_results,_start_index))
    {
      String line=ri.toCSVEscapedString(0);
      
      for(int i=1;i<ResultItem.FIELD_NAMES.length;i++)
        line+=","+ri.toCSVEscapedString(i);
      
      res+=line+"\n";
    }
    
    return res;
  }*/
  
  
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
      @WebParam(name="date_to") String _dateTo,
      @WebParam(name="goes_min") String _GOESmin,
      @WebParam(name="goes_max") String _GOESmax) throws Exception
  {
    //prepare the inputs of the workflow
    Map<String,Object> inputs=new LinkedHashMap<String,Object>();
    inputs.put("date_start",_dateFrom);
    inputs.put("date_end",_dateTo);
    inputs.put("GOES_min",_GOESmin);
    inputs.put("GOES_max",_GOESmax);
    
    //load the workflow in a string
    String wf="";
    BufferedReader fis=new BufferedReader(new FileReader(new File(getClass().getResource("workflows/initial.t2flow").toURI())));
    char[] buf=new char[8192];
    int read=0;
    while((read=fis.read(buf))>0)
      wf+=String.valueOf(buf,0,read);
    fis.close();
    
    //replace the webservice location depending on the current host
    String host=InetAddress.getLocalHost().getHostName();
    if(remapWebservice.containsKey(host))
      wf=wf.replace("<wsdl>http://helio.i4ds.technik.fhnw.ch:8080/core/services/","<wsdl>"+remapWebservice.get(host));
    
    //execute the workflow
    Map<String,List<Object>> wf_results=taverna.executeWorkflow(wf,inputs);
    
    //get the output of the workflow
    return (String)(wf_results.get("VOTable").get(0));
  }
}
