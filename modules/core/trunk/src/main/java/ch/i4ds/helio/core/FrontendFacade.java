package ch.i4ds.helio.core;

import java.sql.*;
import java.util.*;

import javax.jws.*;
import javax.naming.*;
import javax.sql.DataSource;

import org.apache.tools.ant.util.DateUtils;
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
   * Returns the version number of the backend.
   * 
   * @return Version number
   */
  @WebMethod(operationName="get_version")
  public String getVersion()
  {
    return "Revision 82, Initial workflow v7";
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
  
  private String createTable() throws Exception
  {
    Connection con=((DataSource)(new InitialContext()).lookup("jdbc/helio")).getConnection();
    String tableName="tmp"+System.currentTimeMillis();
    con.createStatement().execute("CREATE TABLE `helio`.`"+tableName+"` (`measurementStart` DATETIME,`measurementEnd` DATETIME,`urlFITS` VARCHAR(500),`urlCorrectedRate` VARCHAR(500),`urlFrontRate` VARCHAR(500),`urlPartRate` VARCHAR(500),`urlRate` VARCHAR(500),`urlRearRate` VARCHAR(500),`flareNr` INTEGER,`measurementPeak` DATETIME,`peakCS` INTEGER,`totalCounts` INTEGER,`energyKeVFrom` INTEGER,`energyKeVTo` INTEGER,`xPos` INTEGER,`yPos` INTEGER,`radial` INTEGER,`AR` INTEGER,`urlPhaseFITSGZ` VARCHAR(500),`urlIntensityFITSGZ` VARCHAR(500),`urlPreview` VARCHAR(500),`urlPreviewThumb` VARCHAR(500)) ENGINE = MyISAM;");
    con.close();
    return tableName;
  }
  
  /*@WebMethod(operationName="join_measurementdatetime_v1")
  public String joinDateTime1(
      @WebParam(name="tableA") String _tableA,
      @WebParam(name="tableB") String _tableB
      ) throws Exception
  {
    String tableName=createTable();
    
    Connection con=((DataSource)(new InitialContext()).lookup("jdbc/helio")).getConnection();
    con.createStatement().execute(sql);
    con.close();
    
    return tableName;
  }*/
  
  @WebMethod(operationName="query_v2")
  public String query2(
      @WebParam(name="instrument") String _instrument,
      @WebParam(name="date_from") String _dateFrom,
      @WebParam(name="date_to") String _dateTo,
      @WebParam(name="max_results") int _max_results) throws Exception
  {
    //!!! USE LATE BINDING !!!
    //only when using late binding we can deploy this on the prod. server which
    //is missing the jndi setup
    
    ResultItem[] results=query.query(_instrument,_dateFrom,_dateTo,_max_results);
    
    String tableName=createTable();
    
    Connection con=((DataSource)(new InitialContext()).lookup("jdbc/helio")).getConnection();
    PreparedStatement ps=con.prepareStatement("INSERT INTO "+tableName+"(`measurementStart`,`measurementEnd`,`urlFITS`,`urlCorrectedRate`,`urlFrontRate`,`urlPartRate`,`urlRate`,`urlRearRate`,`flareNr`,`measurementPeak`,`peakCS`,`totalCounts`,`energyKeVFrom`,`energyKeVTo`,`xPos`,`yPos`,`radial`,`AR`,`urlPhaseFITSGZ`,`urlIntensityFITSGZ`,`urlPreview`,`urlPreviewThumb`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    
    for(ResultItem ri:results)
    {
      if(ri.measurementStart!=null)
        ps.setDate(1,new java.sql.Date(ri.measurementStart.getTimeInMillis()));
      if(ri.measurementEnd!=null)
        ps.setDate(2,new java.sql.Date(ri.measurementEnd.getTimeInMillis()));
      
      ps.setString(3,ri.urlFITS);
      ps.setString(4,ri.urlCorrectedRate);
      ps.setString(5,ri.urlFrontRate);
      ps.setString(6,ri.urlPartRate);
      ps.setString(7,ri.urlRate);
      ps.setString(8,ri.urlRearRate);
      ps.setInt(9,ri.flareNr);
      
      if(ri.measurementPeak!=null)
        ps.setDate(10,new java.sql.Date(ri.measurementPeak.getTimeInMillis()));
      
      ps.setInt(11,ri.peakCS);
      ps.setInt(12,ri.totalCounts);
      ps.setInt(13,ri.energyKeVFrom);
      ps.setInt(14,ri.energyKeVTo);
      ps.setInt(15,ri.xPos);
      ps.setInt(16,ri.yPos);
      ps.setInt(17,ri.radial);
      ps.setInt(18,ri.AR);
      ps.setString(19,ri.urlPhaseFITSGZ);
      ps.setString(20,ri.urlIntensityFITSGZ);
      ps.setString(21,ri.urlPreview);
      ps.setString(22,ri.urlPreviewThumb);
      ps.addBatch();
    }
    
    ps.executeBatch();
    con.close();
    
    return tableName;
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
    
    //load the workflow and execute it
    Map<String,Object> wf_results=taverna.executeWorkflow(getClass().getResourceAsStream("workflows/initial.t2flow"),inputs);
    
    //get the output of the workflow
    return (String)wf_results.get("VOTable");
  }
}
