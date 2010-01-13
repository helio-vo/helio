package ch.i4ds.helio.core;

import java.sql.*;
import java.util.*;
import java.util.Map.Entry;
import javax.jws.*;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import ch.i4ds.helio.dpas.*;

@WebService
public class DBObjectInterface
{
  //!!! USE LATE BINDING FOR THE DATABASE !!!
  //only when using late binding we can deploy this on the prod. server which
  //is missing the jndi setup
  
  
  private QueryService query;
  
  @WebMethod(exclude=true)
  public void setQueryService(QueryService _query)
  {
    query=_query;
  }
  
  
  @WebMethod(operationName="get_table_votable_v1")
  public String getVOTable1(
      @WebParam(name="table") String _tableName
    ) throws Exception
  {
    LinkedHashMap<String,String> cols=getColumns(_tableName);
    
    StringBuffer result=new StringBuffer("");
    result.append("<VOTABLE version='1.0'>");
    result.append("<DEFINITIONS>");
    result.append("</DEFINITIONS>");
    result.append("<RESOURCE type='results'>");
    result.append("<DESCRIPTION>"+_tableName+"</DESCRIPTION>");
    result.append("<TABLE>");
    for(Entry<String,String> col:cols.entrySet())
      result.append("<FIELD "+sqlToVOTableType(col.getValue())+" name='"+col.getKey()+"' />");
    result.append("<DATA>");
    result.append("<TABLEDATA>");
    
    Connection con=((DataSource)(new InitialContext()).lookup("jdbc/helio")).getConnection();
    ResultSet rs=con.createStatement().executeQuery("SELECT * FROM "+_tableName+";");
    
    while(rs.next())
    {
      result.append("<TR>");
      for(Entry<String,String> col:cols.entrySet())
      {
        result.append("<TD>"+rs.getString(col.getKey())+"</TD>");
      }
      result.append("</TR>");
    }
    
    result.append("</TABLEDATA>");
    result.append("</DATA>");
    result.append("</TABLE>");
    result.append("</RESOURCE>");
    return result.toString();
  }
  
  private String sqlToVOTableType(String _dataType)
  {
    if(_dataType.startsWith("varchar"))
      return "datatype='char' arraysize='3400'";
    if(_dataType.startsWith("int"))
      return "datatype='int'";
    if(_dataType.startsWith("float"))
      return "datatype='float'";
    return "";
  }
  
  
  @WebMethod(operationName="join_measurementdatetime_v1")
  public String joinDateTime1(
      @WebParam(name="tableA") String _tableA,
      @WebParam(name="tableB") String _tableB
      ) throws Exception
  {
    LinkedHashMap<String,String> colsA=getColumns(_tableA);
    LinkedHashMap<String,String> colsB=getColumns(_tableB);
    
    Connection con=((DataSource)(new InitialContext()).lookup("jdbc/helio")).getConnection();
    
    String tableName=generateTableName();
    
    String sql="CREATE TABLE "+tableName+" SELECT";
    sql+=" IFNULL(A.measurementStart,B.measurementStart) measurementStart,";
    sql+=" IFNULL(A.measurementEnd,B.measurementEnd) measurementEnd";
    
    for(Entry<String,String> col:colsA.entrySet())
      sql+=",A."+col.getKey()+" A_"+col.getKey();
    
    for(Entry<String,String> col:colsB.entrySet())
      sql+=",B."+col.getKey()+" B_"+col.getKey();
    
    sql+=" FROM "+_tableA+" A INNER JOIN "+_tableB+" B ON";
    sql+=" ((A.measurementStart>=B.measurementStart) AND (A.measurementStart<B.measurementEnd))";
    sql+=" OR ((A.measurementEnd>B.measurementStart) AND (A.measurementEnd<=B.measurementEnd))";
    sql+=" OR ((A.measurementStart<=B.measurementStart) AND (A.measurementEnd>=B.measurementEnd))";
    sql+=" OR ((B.measurementStart>=A.measurementStart) AND (B.measurementStart<A.measurementEnd))";
    
    con.createStatement().execute(sql);
    
    con.close();
    
    return tableName;
  }
  
  
  @SuppressWarnings("serial")
  @WebMethod(operationName="query_v2")
  public String query2(
      @WebParam(name="instrument") String _instrument,
      @WebParam(name="date_from") String _dateFrom,
      @WebParam(name="date_to") String _dateTo,
      @WebParam(name="max_results") int _max_results) throws Exception
  {
    ResultItem[] results=query.query(_instrument,_dateFrom,_dateTo,_max_results);
    
    String tableName=createTable(new LinkedHashMap<String,String>(){{
      put("measurementStart","DATETIME");
      put("measurementEnd","DATETIME");
      put("urlFITS","VARCHAR(500)");
      put("urlCorrectedRate","VARCHAR(500)");
      put("urlFrontRate","VARCHAR(500)");
      put("urlPartRate","VARCHAR(500)");
      put("urlRate","VARCHAR(500)");
      put("urlRearRate","VARCHAR(500)");
      put("flareNr","INTEGER");
      put("measurementPeak","DATETIME");
      put("peakCS","INTEGER");
      put("totalCounts","INTEGER");
      put("energyKeVFrom","INTEGER");
      put("energyKeVTo","INTEGER");
      put("xPos","INTEGER");
      put("yPos","INTEGER");
      put("radial","INTEGER");
      put("AR","INTEGER");
      put("urlPhaseFITSGZ","VARCHAR(500)");
      put("urlIntensityFITSGZ","VARCHAR(500)");
      put("urlPreview","VARCHAR(500)");
      put("urlPreviewThumb","VARCHAR(500)");
    }});
    
    Connection con=((DataSource)(new InitialContext()).lookup("jdbc/helio")).getConnection();
    PreparedStatement ps=con.prepareStatement("INSERT INTO "+tableName+"(`measurementStart`,`measurementEnd`,`urlFITS`,`urlCorrectedRate`,`urlFrontRate`,`urlPartRate`,`urlRate`,`urlRearRate`,`flareNr`,`measurementPeak`,`peakCS`,`totalCounts`,`energyKeVFrom`,`energyKeVTo`,`xPos`,`yPos`,`radial`,`AR`,`urlPhaseFITSGZ`,`urlIntensityFITSGZ`,`urlPreview`,`urlPreviewThumb`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    
    for(ResultItem ri:results)
    {
      if(ri.measurementStart!=null)
        ps.setTimestamp(1,new java.sql.Timestamp(ri.measurementStart.getTimeInMillis()));
      else
        ps.setTimestamp(1,null);
      
      if(ri.measurementEnd!=null)
        ps.setTimestamp(2,new java.sql.Timestamp(ri.measurementEnd.getTimeInMillis()));
      else
        ps.setTimestamp(2,null);
      
      ps.setString(3,ri.urlFITS);
      ps.setString(4,ri.urlCorrectedRate);
      ps.setString(5,ri.urlFrontRate);
      ps.setString(6,ri.urlPartRate);
      ps.setString(7,ri.urlRate);
      ps.setString(8,ri.urlRearRate);
      ps.setInt(9,ri.flareNr);
      
      if(ri.measurementPeak!=null)
        ps.setTimestamp(10,new java.sql.Timestamp(ri.measurementPeak.getTimeInMillis()));
      else
        ps.setTimestamp(10,null);
      
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
  
  private String generateTableName()
  {
    return "tmp"+System.currentTimeMillis(); 
  }
  
  private LinkedHashMap<String,String> getColumns(String _tableName) throws Exception
  {
    Connection con=((DataSource)(new InitialContext()).lookup("jdbc/helio")).getConnection();
    ResultSet rs=con.createStatement().executeQuery("SELECT COLUMN_NAME,COLUMN_TYPE FROM INFORMATION_SCHEMA.columns WHERE TABLE_SCHEMA='helio' AND TABLE_NAME='"+_tableName+"' ORDER BY ORDINAL_POSITION;");
    LinkedHashMap<String,String> result=new LinkedHashMap<String,String>();
    while(rs.next())
      result.put(rs.getString("COLUMN_NAME"),rs.getString("COLUMN_TYPE"));
    rs.close();
    con.close();
    
    return result;
  }
  
  private String createTable(LinkedHashMap<String,String> _columns) throws Exception
  {
    Connection con=((DataSource)(new InitialContext()).lookup("jdbc/helio")).getConnection();
    String tableName=generateTableName();
    
    String sql="CREATE TABLE `helio`.`"+tableName+"` (";
    int i=0;
    for(Entry<String,String> col:_columns.entrySet())
    {
      if(i!=0)
        sql+=",";
      sql+="`"+col.getKey()+"` "+col.getValue();
      i++;
    }
    sql+=") ENGINE = MyISAM;";
    
    System.out.println(sql);
    
    con.createStatement().execute(sql);
    con.close();
    return tableName;
  }
}
