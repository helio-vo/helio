/* #ident	"%W%" */
package com.org.helio.common.dao.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import uk.ac.starlink.table.ColumnInfo;

import com.org.helio.common.dao.CommonDaoFactory;
import com.org.helio.common.dao.exception.DetailsNotFoundException;
import com.org.helio.common.dao.interfaces.CommonDao;
import com.org.helio.common.dao.interfaces.ShortNameQueryDao;
import com.org.helio.common.transfer.CommonResultTO;
import com.org.helio.common.transfer.CommonTO;
import com.org.helio.common.transfer.criteriaTO.CommonCriteriaTO;
import com.org.helio.common.util.ConfigurationProfiler;
import com.org.helio.common.util.VOTableMaker;



public class CommonDaoImpl implements CommonDao { 

	public void generateVOTableDetails(CommonCriteriaTO comCriteriaTO) throws DetailsNotFoundException {
		Object[] inArray = null;	
		 BufferedWriter output = new BufferedWriter( comCriteriaTO.getPrintWriter() );
		try{			
		 HashMap<String,String> params  = new HashMap<String,String>();
		 params.put("kwstartdate", comCriteriaTO.getStartDateTime());
		 params.put("kwenddate", comCriteriaTO.getEndDateTime());
		 String query=ConfigurationProfiler.getInstance().getProperty("sql.query");
		 System.out.println(" : Query String  : "+query);		
		 ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
		 CommonResultTO result=shortNameDao.getSNQueryResult(query,params);
		 HashMap<String,CommonTO> hmbColumnList=result.getColumnNameList();
		 String[] colNames=result.getColNames();		
		 //Create VOTABLE 
		 comCriteriaTO.setHmbColumnList(hmbColumnList);
		 VOTableMaker voTableMarker=createVOTableMaker(comCriteriaTO);		
		 voTableMarker.writeBeginVOTable(output,ConfigurationProfiler.getInstance().getProperty("sql.votable.head.desc"),comCriteriaTO.getStatus());
		 //Getting result set values.
		 Object[] objArr = result.getResult();
		 if(objArr!=null){ 	
			for(int i=0;i<objArr.length;i++)
			{
			 inArray = (Object[])objArr[i];
			 	for(int j=0;j<inArray.length;j++)
				 {
					 System.out.println(inArray[j]+" inArray.length " +inArray.length +"  voTableMarker.getValues() "+voTableMarker.getValues().length);
					 voTableMarker.getValues()[j]=inArray[j];					 
				 }
			 	voTableMarker.addRow();
			}
		 }
		if(voTableMarker.getRowCount() > 0) {
			 voTableMarker.writeTable(output);
     	} 
		voTableMarker.writeEndVOTable(output,comCriteriaTO.getStatus());		 
	
		}catch(Exception pe) {
        	pe.printStackTrace();
        	try {
				output.write(pe.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				try {
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        	return;
        }		
		
	}

	private  VOTableMaker createVOTableMaker(CommonCriteriaTO comCriteriaTO) {
		HashMap<String,CommonTO> hmbColumnList=comCriteriaTO.getHmbColumnList();
		 System.out.println(ConfigurationProfiler.getInstance().getProperty("sql.columnnames"));
		 String[] columnNames=ConfigurationProfiler.getInstance().getProperty("sql.columnnames").split("::");
		 System.out.println(" : columnnames String  : "+columnNames);
		 String[] columnDesc=ConfigurationProfiler.getInstance().getProperty("sql.columndesc").split("::");
		 System.out.println(" : columndesc String  : "+columnDesc);
		 String[] columnUcd=ConfigurationProfiler.getInstance().getProperty("sql.columnucd").split("::");
		 System.out.println(" : columndesc String  : "+columnUcd); 
		ColumnInfo [] defValues = new ColumnInfo[columnNames.length];
		for(int inColCount=0;inColCount<columnNames.length;inColCount++){
			System.out.println(columnNames[inColCount]+"   "+columnDesc[inColCount]+"  "+columnUcd[inColCount]);
			CommonTO commonTO=hmbColumnList.get(columnNames[inColCount]);	
			if(commonTO!=null && commonTO.getColumnType().equals("INTEGER")){
				//Need to change class to INTEGER.
				defValues[inColCount] = new ColumnInfo(columnNames[inColCount],String.class,columnDesc[inColCount]);
			}else{
				defValues[inColCount] = new ColumnInfo(columnNames[inColCount],String.class,columnDesc[inColCount]);
			}			
	        defValues[inColCount].setUCD(columnUcd[inColCount]);
		}
		return new VOTableMaker(defValues);
	}
	
}