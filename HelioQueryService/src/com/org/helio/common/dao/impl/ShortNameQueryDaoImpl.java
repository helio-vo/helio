/* #ident	"%W%" */
package com.org.helio.common.dao.impl;

import java.io.BufferedWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import uk.ac.starlink.table.ColumnInfo;
import com.org.helio.common.dao.exception.ShortNameQueryException;
import com.org.helio.common.dao.interfaces.ShortNameQueryDao;
import com.org.helio.common.transfer.CommonResultTO;
import com.org.helio.common.transfer.CommonTO;
import com.org.helio.common.transfer.criteriaTO.CommonCriteriaTO;
import com.org.helio.common.util.CommonUtils;
import com.org.helio.common.util.ConfigurationProfiler;
import com.org.helio.common.util.ConnectionManager;
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
			String sRepSql = CommonUtils.replaceParams(comCriteriaTO.getQuery(), comCriteriaTO.getParamData());
			// Getting Access URL
			String sAccessUrl=ConfigurationProfiler.getInstance().getProperty("sql.votable.accesurl");
			//Getting Format
			String sFormat=ConfigurationProfiler.getInstance().getProperty("sql.votable.format");
			logger.info(" : Query String After Replacing Value :"+sRepSql);	
			con = ConnectionManager.getConnection();
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs= st.executeQuery(sRepSql);
     		rms = rs.getMetaData();
     		int i=0; // This Is for mySQL 
			ArrayList<Object[]> arr = new ArrayList<Object[]>();
			int colCount = rms.getColumnCount();			
			String[] colNames=getColumnNamesAndType(rms,colCount);
			rs.last();
			int cnt = rs.getRow();			
			
			//Creating VOTable
			VOTableMaker voTableMarker=createVOTableMaker(comCriteriaTO);		
			voTableMarker.writeBeginVOTable(output,ConfigurationProfiler.getInstance().getProperty("sql.votable.head.desc"),comCriteriaTO.getStatus());
						
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
				
				//Writing end of VOTable.
				voTableMarker.writeEndVOTable(output,comCriteriaTO.getStatus());		 
				
			}
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
	
	private  VOTableMaker createVOTableMaker(CommonCriteriaTO comCriteriaTO) {
		HashMap<String,CommonTO> hmbColumnList=comCriteriaTO.getHmbColumnList();
		 logger.info(ConfigurationProfiler.getInstance().getProperty("sql.columnnames"));
		 String[] columnNames=ConfigurationProfiler.getInstance().getProperty("sql.columnnames").split("::");
		 logger.info(" : Column Name String  : "+columnNames);
		 String[] columnDesc=ConfigurationProfiler.getInstance().getProperty("sql.columndesc").split("::");
		 logger.info(" : Column Desc String  : "+columnDesc);
		 String[] columnUcd=ConfigurationProfiler.getInstance().getProperty("sql.columnucd").split("::");
		 logger.info(" : Column UCD String  : "+columnUcd); 
		ColumnInfo [] defValues = new ColumnInfo[columnNames.length];
		for(int inColCount=0;inColCount<columnNames.length;inColCount++){			
			//CommonTO commonTO=hmbColumnList.get(columnNames[inColCount]);				
			defValues[inColCount] = new ColumnInfo(columnNames[inColCount],String.class,columnDesc[inColCount]);			
	        defValues[inColCount].setUCD(columnUcd[inColCount]);
		}
		return new VOTableMaker(defValues);
	}
	
}
