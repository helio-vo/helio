/* #ident	"%W%" */
package com.org.helio.common.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;


import com.org.helio.common.dao.exception.DataNotFoundException;
import com.org.helio.common.dao.exception.ShortNameQueryException;
import com.org.helio.common.dao.interfaces.ShortNameQueryDao;
import com.org.helio.common.transfer.CommonResultTO;
import com.org.helio.common.transfer.CommonTO;
import com.org.helio.common.util.CommonUtils;
import com.org.helio.common.util.ConfigurationProfiler;
import com.org.helio.common.util.ConnectionManager;
import com.org.helio.common.util.HibernateSessionFactory;


public class ShortNameQueryDaoImpl implements ShortNameQueryDao {
		
	public ShortNameQueryDaoImpl() { 
						
	}
	
	protected final  Logger logger = Logger.getLogger(this.getClass());
	
	public CommonResultTO getSNQueryResult(String sSql, HashMap<String,String> hmArgs) throws ShortNameQueryException 
	{
		return getSNQueryResult(sSql, hmArgs,0,-1);
	}

	
	@SuppressWarnings("deprecation")
	public CommonResultTO getSNQueryResult(String sSql, HashMap<String,String> hmArgs, int startRow, int noOfRecords) throws ShortNameQueryException 
	{
		long lStart=System.currentTimeMillis();
	
		Connection con = null;
		Statement st = null;
		ResultSetMetaData rms =null;
		ResultSet rs=null;
		CommonResultTO result = new CommonResultTO();
 
		try 
		{
			String sRepSql = CommonUtils.replaceParams(sSql, hmArgs);
			logger.info(" : Query String After Replacing Value :"+sRepSql);	
			con = ConnectionManager.getConnection();
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs= st.executeQuery(sRepSql);
     		rms = rs.getMetaData();
     		result.setResultSet(rs);
     		result.setColumnNameList(getColumnNamesAndType(rms));
     		int i=0; // This Is for mySQL 
			ArrayList<Object[]> arr = new ArrayList<Object[]>();
			int colCount = rms.getColumnCount();			
			String[] colNames=getColumnNamesAndType(rms,colCount);
			result.setColNames(colNames);
			rs.last();
			int cnt = rs.getRow();			
			result.setCount(cnt);
			
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
					//Added '2' for acces url & format.
					Object[] X = new Object[colCount+2];
					//code for setting access url.
					X[count]=ConfigurationProfiler.getInstance().getProperty("sql.votable.accesurl");
					//code for getting database data.
					for (int g = 0; g < colCount; g++) {
						logger.info(" : Access URL value :"+X[0]);
						//This is done; to change index to '1' . Bcoz first index value is Access url.
						if(X[0]!=null && !X[0].equals("") && g==0){
							count=count+1;
						}
						X[count] = rs.getString(colNames[g]);
						logger.info(" Column Value  "+X[count]+" Column name "+colNames[g]);
						count++;
					}
					// code for setting format
					X[X.length-1]=ConfigurationProfiler.getInstance().getProperty("sql.votable.format");
					arr.add(X);
				 }while(rs.next()&& i<noOfRecords); 				
				result.setResult(arr.toArray()); 
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
		long lEnd=System.currentTimeMillis();		

		return result;
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
	      logger.info(" columnName   "+columnName +" columnType "+columnType);
	      commonTO=new CommonTO();
	      commonTO.setColumnName(columnName);
	      commonTO.setColumnType(columnType); 
	      commonTO.setColumnSize(columnSize);
	      hmbColumnList.put(columnName, commonTO);
	    } 
	    logger.info("   hmbColumnList   "+hmbColumnList);
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
	    logger.info("   colNames   "+colNames);
	    return colNames;
	  }
	
}
