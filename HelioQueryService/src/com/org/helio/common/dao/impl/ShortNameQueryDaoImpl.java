/* #ident	"%W%" */
package com.org.helio.common.dao.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.apache.log4j.Logger;
import uk.ac.starlink.table.ColumnInfo;
import uk.ac.starlink.table.StarTable;
import uk.ac.starlink.table.jdbc.SequentialResultSetStarTable;

import com.org.helio.common.dao.exception.DetailsNotFoundException;
import com.org.helio.common.dao.exception.ShortNameQueryException;
import com.org.helio.common.dao.interfaces.ShortNameQueryDao;
import com.org.helio.common.transfer.CommonTO;
import com.org.helio.common.transfer.criteriaTO.CommonCriteriaTO;
import com.org.helio.common.util.CommonUtils;
import com.org.helio.common.util.ConfigurationProfiler;
import com.org.helio.common.util.ConnectionManager;
import com.org.helio.common.util.StandardTypeTable;
import com.org.helio.common.util.VOTableMaker;


public class ShortNameQueryDaoImpl implements ShortNameQueryDao {
		
	public ShortNameQueryDaoImpl() { 
						
	}
	
	protected final  Logger logger = Logger.getLogger(this.getClass());
		
	public void getSNQueryResult(CommonCriteriaTO comCriteriaTO) throws ShortNameQueryException 
	{
		 getSNQueryResult(comCriteriaTO,0,-1); 
	}
	
	@SuppressWarnings("deprecation")
	public void getSNQueryResult(CommonCriteriaTO comCriteriaTO, int startRow, int noOfRecords) throws ShortNameQueryException 
	{		
		Connection con = null;
		Statement st = null;
		ResultSetMetaData rms =null;
		ResultSet rs=null;
		BufferedWriter output = new BufferedWriter( comCriteriaTO.getPrintWriter() );
		
		try 
		{
			// Getting Access URL
			String sAccessUrl=ConfigurationProfiler.getInstance().getProperty("sql.votable.accesurl");
			//Getting Format
			String sFormat=ConfigurationProfiler.getInstance().getProperty("sql.votable.format");
						
			//Creating VOTable
			String[] listName=comCriteriaTO.getListName().split(",");
			
			VOTableMaker.writeVOTableHeader(output,comCriteriaTO.getStatus());
			
			//For loop start
			for(int intCnt=0;intCnt<listName.length;intCnt++){
				
				String sRepSql = CommonUtils.replaceParams(generateQuery(listName[intCnt],comCriteriaTO), comCriteriaTO.getParamData());
				logger.info(" : Query String After Replacing Value :"+sRepSql);	
				
				//Setting Table Name.
				comCriteriaTO.setTableName(listName[intCnt]);
				//Setting query with values.
				comCriteriaTO.setQuery(sRepSql);
				//Connecting to database.						
				con = ConnectionManager.getConnection();
				st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				rs= st.executeQuery(sRepSql);
	     		rms = rs.getMetaData();
	     		int colCount = rms.getColumnCount();
	     		//Column Names
	     		String[] colNames=getColumnNamesAndType(rms,colCount);
	     		//Column name;Type and size.
	     		HashMap<String,CommonTO> hmbColumnList=getColumnNamesAndType(rms);
	     		comCriteriaTO.setHmbColumnList(hmbColumnList);
	     		
	     		int i=0; // This Is for mySQL 
				ArrayList<Object[]> arr = new ArrayList<Object[]>();
				rs.last();
				int cnt = rs.getRow();	
				
	     		//Create VOTable 
	     		VOTableMaker voTableMarker=createVOTableMaker(comCriteriaTO);		
				voTableMarker.writeBeginVOTable(output,ConfigurationProfiler.getInstance().getProperty("sql.votable.head.desc."+listName[intCnt]));
	     														
				/*if noOfRecords == -1 means we need complete Result till end*/
				if(noOfRecords==-1)
				{
					startRow =0;
					noOfRecords = cnt;
				}
				
				if(cnt!=0)
				{
					rs.absolute(startRow + 1);
					do {
						
						i++;
						int count=0;
						//code for setting access url.
						if(sAccessUrl!=null && !sAccessUrl.equals(""))
						voTableMarker.getValues()[count]=ConfigurationProfiler.getInstance().getProperty("sql.votable.accesurl");
						//code for getting database data.
						for (int g = 0; g < colCount; g++) {
							//This is done; to change index to '1' . Bcoz first index value is Access url.
							if(voTableMarker.getValues()[0]!=null && !voTableMarker.getValues()[0].equals("") && g==0){
								count=count+1;
							}
							
							voTableMarker.getValues()[count] = rs.getString(colNames[g]);
							count++;
						}
						// code for setting format
						if(sFormat!=null && !sFormat.equals(""))
						voTableMarker.getValues()[voTableMarker.getValues().length-1]=ConfigurationProfiler.getInstance().getProperty("sql.votable.format");
						
						voTableMarker.addRow();
						
					 }while(rs.next()&& i<noOfRecords); 	
					
					if(voTableMarker.getRowCount() > 0) {
						 voTableMarker.writeTable(output);
			     	} 
					voTableMarker.writeEndVOTable(output);
				}
				}//end of for
			
				//Writing end of VOTable.
				VOTableMaker.writeVOTableFooter(output,comCriteriaTO.getStatus());	
				
				if(rms!=null)
				{
					rms = null;
				}
				if(rs!=null)
				{
					rs.close();
					rs=null;
				}
				if(st!=null)
				{
					st.close();
					st=null;
				}
				if(con!=null)
				{
					con.close();
					con=null;
				}
			} catch (Exception e) {			
				throw new ShortNameQueryException("EXCEPTION ", e);
			}
			
			finally
			{
				try {
					if(rms!=null)
					{
						rms = null;
					}
					if(rs!=null)
					{
						rs.close();
						rs=null;
					}
					if(st!=null)
					{
						st.close();
						st=null;
					}
					if(con!=null)
					{
						con.close();
						con=null;
					}
					if(output!=null){
						output=null;
						output.close();
					}
				} catch (Exception e) {
					
				}
		}		
	
	}


	public void generateVOTableDetails(CommonCriteriaTO comCriteriaTO) throws DetailsNotFoundException,Exception {
		Connection con = null;
		Statement st = null;
		ResultSetMetaData rms =null;
		ResultSet rs=null;
		String[] listName=comCriteriaTO.getListName().split(",");
		StarTable[] tables=new StarTable[listName.length];
		try{
		//For loop start
		for(int intCnt=0;intCnt<listName.length;intCnt++){
			String sRepSql = CommonUtils.replaceParams(generateQuery(listName[intCnt],comCriteriaTO), comCriteriaTO.getParamData());
			logger.info(" : Query String After Replacing Value :"+sRepSql);	
			
			//Setting Table Name.
			comCriteriaTO.setTableName(listName[intCnt]);
			//Setting query with values.
			comCriteriaTO.setQuery(sRepSql);
			//Connecting to database.						
			con = ConnectionManager.getConnection();
			st = con.createStatement();
			rs= st.executeQuery(sRepSql);
			tables[intCnt] = new StandardTypeTable( new SequentialResultSetStarTable( rs ) );
		}
		comCriteriaTO.setTables(tables);
		//Editing column property.
		VOTableMaker.setColInfoProperty(tables, listName);
		//Writing all details into table.
		VOTableMaker.writeTables(comCriteriaTO);
		
		if(rms!=null)
		{
			rms = null;
		}
		if(rs!=null)
		{
			rs.close();
			rs=null;
		}
		if(st!=null)
		{
			st.close();
			st=null;
		}
		if(con!=null)
		{
			con.close();
			con=null;
		}
		
		} catch (Exception e) {			
			throw new ShortNameQueryException("EXCEPTION ", e);
		}
		finally
		{
			try {
				if(rms!=null)
				{
					rms = null;
				}
				if(rs!=null)
				{
					rs.close();
					rs=null;
				}
				if(st!=null)
				{
					st.close();
					st=null;
				}
				if(con!=null)
				{
					con.close();
					con=null;
				}
				
			} catch (Exception e) {
				
			}
	}		
	}
	
	/*
	 * Get the list of Column Names;Column Type in a Table.
	 */
	@SuppressWarnings("unused")
	private  HashMap<String,CommonTO> getColumnNamesAndType(ResultSetMetaData rsMetaData) throws Exception {
	    if (rsMetaData == null) {
	      return null;
	    }
	    HashMap<String,CommonTO> hmbColumnList=new HashMap<String,CommonTO>();	  
	    int numberOfColumns = rsMetaData.getColumnCount();
	    CommonTO commonTO;
	    // get the column names; column indexes start from 1
	    for (int intColNo = 1; intColNo < numberOfColumns + 1; intColNo++) {
	      String columnName = rsMetaData.getColumnName(intColNo);
	      String columnType=  rsMetaData.getColumnTypeName(intColNo);
	      String columnSize=  String.valueOf(rsMetaData.getColumnDisplaySize(intColNo));
	      logger.info(" : Column Name :  "+columnName +" : Column Type :  "+columnType);
	      commonTO=new CommonTO();
	      commonTO.setColumnName(columnName);
	      commonTO.setColumnType(columnType); 
	      commonTO.setColumnSize(columnSize);
	      hmbColumnList.put(columnName, commonTO);
	    } 	   
	    return hmbColumnList;
	  }
	

	/*
	 * Get the list of Column Names in a Table.
	 */
	@SuppressWarnings("unused")
	private String[] getColumnNamesAndType(ResultSetMetaData rsMetaData,int colCount) throws Exception {
	    if (rsMetaData == null) {
	      return null;
	    }	    
	    String[] colNames = new String[colCount];
	    int numberOfColumns = rsMetaData.getColumnCount();
	    
	    // get the column names; column indexes start from 1
	    for (int intColNo = 0; intColNo < numberOfColumns ; intColNo++) {
	    	colNames[intColNo]=rsMetaData.getColumnName(intColNo+1);   
	    }
	    
	    logger.info("   :  Column Names  :  "+colNames.toString());
	    return colNames;
	  }
	
	
	/*
	 * Get the list of Tables in a Database.
	 */
	@SuppressWarnings("unused")
	public HashMap<String,String> getDatabaseTableNames(Connection con) throws Exception 
	{
		HashMap<String,String> hmbDatabaseTableList=new HashMap<String,String>();	  
		try{
			DatabaseMetaData md = con.getMetaData();
		    ResultSet rs = md.getTables(null, null, "%", null);
		    while (rs.next()) {
		      // System.out.println(rs.getString(3));
		      hmbDatabaseTableList.put(rs.getString(3), rs.getString(3));
		    }
		} catch (Exception e) {			
			throw new Exception("EXCEPTION ", e);
		}
		finally
		{
			try {
				if(con!=null)
				{
					con.close();
					con=null;
				}
				
			} catch (Exception e) {
				
			}
	}	
	    return hmbDatabaseTableList;
	}
	
	/*
	 * Get the list of Columns in a Table.
	 */
	public CommonTO[] getTableColumnNames(Connection con,String tableName) throws Exception
	{
		ResultSet rsColumns = null;
		CommonTO[] columnTO = null;
		try{
			DatabaseMetaData meta = con.getMetaData();
		    rsColumns = meta.getColumns(null, null, tableName, null);
		    rsColumns.last();
		    int intCount=rsColumns.getRow();
		    rsColumns.beforeFirst();
		    if(rsColumns!=null){
		    	columnTO = new CommonTO[intCount];
	     		int i=0;
			    while (rsColumns.next()) {
			      columnTO[i]=new CommonTO();
			      columnTO[i].setColumnName(rsColumns.getString("COLUMN_NAME"));
			      columnTO[i].setColumnType(rsColumns.getString("TYPE_NAME"));
			      i++;
			    }
		    }
		} catch (Exception e) {			
			throw new Exception("EXCEPTION ", e);
		}
		finally
		{
			try {
				if(rsColumns!=null)
				{
					rsColumns.close();
					rsColumns=null;
				}
				if(con!=null)
				{
					con.close();
					con=null;
				}
				
			} catch (Exception e) {
				
			}
	}	
		return columnTO;
	}
	
		
	/*
	 * It creates the VOTable.
	 */
	@SuppressWarnings("unused")
	private  VOTableMaker createVOTableMaker(CommonCriteriaTO comCriteriaTO) {
		
		HashMap<String,CommonTO> hmbColumnList=comCriteriaTO.getHmbColumnList();
		String[] columnNames=ConfigurationProfiler.getInstance().getProperty("sql.columnnames."+comCriteriaTO.getTableName()).split("::");
		logger.info(" : Column Name String  : "+columnNames);
		String[] columnDesc=ConfigurationProfiler.getInstance().getProperty("sql.columndesc."+comCriteriaTO.getTableName()).split("::");
		logger.info(" : Column Desc String  : "+columnDesc);
		String[] columnUcd=ConfigurationProfiler.getInstance().getProperty("sql.columnucd."+comCriteriaTO.getTableName()).split("::");
		logger.info(" : Column UCD String  : "+columnUcd); 
		 
		ColumnInfo[] defValues = new ColumnInfo[columnNames.length];
		for(int inColCount=0;inColCount<columnNames.length;inColCount++){
			Class c=null;
			CommonTO commonTO=hmbColumnList.get(columnNames[inColCount]);
			if(commonTO.getColumnType()!=null && commonTO.getColumnType().contains("VARCHAR")){
				 c=String.class;
			}else if(commonTO.getColumnType()!=null && commonTO.getColumnType().contains("INTEGER")){
				 c=Integer.class;
			}else if(commonTO.getColumnType()!=null && commonTO.getColumnType().contains("DOUBLE")){
				 c=Double.class;
			}else if(commonTO.getColumnType()!=null && commonTO.getColumnType().contains("CHAR")){
				 c=Character.class;
			}else if(commonTO.getColumnType()!=null && commonTO.getColumnType().contains("DATETIME")){
				 c=String.class;
			}else{
				 c=String.class;
			}
			defValues[inColCount] = new ColumnInfo(columnNames[inColCount],c,columnDesc[inColCount]);
		      defValues[inColCount].setUCD(columnUcd[inColCount]);
	      }
		
		return new VOTableMaker(defValues);
	}
	
	/*
	 * Creating column names.
	 */
	private String getColumnNamesFromProperty(String tableName)
	{
		String[] columnNames=ConfigurationProfiler.getInstance().getProperty("sql.columnnames."+tableName).split("::");
		String colNamesForTable="";
		String colColName="";
		
		for(int i=0;i<columnNames.length;i++)
		{
			colNamesForTable=colNamesForTable+columnNames[i]+",";
		}
		
		if(colNamesForTable.endsWith(",")){
			colNamesForTable=colNamesForTable.substring(0, colNamesForTable.length()-1);
		}
		logger.info(" : Table Name : "+tableName +" List of columns "+colNamesForTable);
		
		return colNamesForTable;
	}
	
	
	@SuppressWarnings("unused")
	private String  generateQuery(String listName,CommonCriteriaTO comCriteriaTO){
			 String queryConstraint="";
			 String query="";
		
			 HashMap<String,String> params  = new HashMap<String,String>();
			 
			 params.put("kwstartdate."+listName, comCriteriaTO.getStartDateTime());
			 params.put("kwenddate."+listName, comCriteriaTO.getEndDateTime());
			 params.put("kwinstrument."+listName, comCriteriaTO.getInstruments());
					
			 //Setting param value
			 comCriteriaTO.setParamData(params);
			 query="SELECT "+getColumnNamesFromProperty(listName)+" FROM "+listName;
			 logger.info(" : Query String with 'Select' and 'From' : "+query);
			 
			 //Appending Time clause.
			 String queryTimeContraint=ConfigurationProfiler.getInstance().getProperty("sql.query.time.constraint."+listName);
			 if(queryTimeContraint!=null && !queryTimeContraint.equals("") ){
				 queryConstraint=queryConstraint+" "+queryTimeContraint;
			 }
			 
			 logger.info(" : Appending Time Constraint If Avialable : "+queryConstraint);
			 
			 //Appending Instrument clause.
			 String queryInstContraint=ConfigurationProfiler.getInstance().getProperty("sql.query.instr.constraint."+listName);
			 if(queryInstContraint!=null && !queryInstContraint.equals("")){
				 if(queryConstraint!="")
					 queryConstraint=queryConstraint+" AND "+queryInstContraint; 
				 else
					 queryConstraint=queryConstraint+" "+queryInstContraint; 
				
			 }
			
			 logger.info(" : Appending Instrument Constraint If Avialable : "+queryConstraint);
			 
			 //Appending Order By clause.
			 String queryOrderByContraint=ConfigurationProfiler.getInstance().getProperty("sql.query.orderby.constraint."+listName);
			
			 //Appending 'Select Part' ; 'Where Constraints' .
			 if(queryConstraint!=null && queryConstraint!=""){
				 query=query+" WHERE "+queryConstraint;
			 }
			 
			 //Appending ; 'Order By Constraints' .
			 query=query+" "+queryOrderByContraint;
			 
			 logger.info(" : Full Query String To Execute : "+query);
			 
		 return query;
	}
	
	
}
