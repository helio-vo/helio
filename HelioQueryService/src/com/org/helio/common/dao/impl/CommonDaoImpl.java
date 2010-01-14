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

import org.apache.log4j.Logger;

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

	protected final  Logger logger = Logger.getLogger(this.getClass());
	
	public void generateVOTableDetails(CommonCriteriaTO comCriteriaTO) throws DetailsNotFoundException {
		Object[] inArray = null;	
		BufferedWriter output = new BufferedWriter( comCriteriaTO.getPrintWriter() );
		try{			
		 HashMap<String,String> params  = new HashMap<String,String>();
		 params.put("kwstartdate", comCriteriaTO.getStartDateTime());
		 params.put("kwenddate", comCriteriaTO.getEndDateTime());
		 params.put("kwinstrument", comCriteriaTO.getInstruments());
		 params.put("kwlistname", comCriteriaTO.getListName());
		 //Query with time
		 String query=ConfigurationProfiler.getInstance().getProperty("sql.query");
		 //Appending Instrument clause.
		 String queryInstContraint=ConfigurationProfiler.getInstance().getProperty("sql.query.instr.constraint");
		 if(queryInstContraint!=null && !queryInstContraint.equals(""))
			 query=query+" "+queryInstContraint; 
		 //Appending ListName clause.
		 String queryListNameContraint=ConfigurationProfiler.getInstance().getProperty("sql.query.listname.constraint");
		 if(queryListNameContraint!=null && !queryListNameContraint.equals(""))
			 query=query+" "+queryListNameContraint; 
		 //Appending Order By clause.
		 String queryOrderByContraint=ConfigurationProfiler.getInstance().getProperty("sql.query.orderby.constraint");
		 if(queryOrderByContraint!=null && !queryOrderByContraint.equals(""))
			 query=query+" "+queryOrderByContraint;
		 logger.info(" : Query String FROM PROPERTY FILE : "+query);		
		 ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
		 CommonResultTO result=shortNameDao.getSNQueryResult(query,params);
		 HashMap<String,CommonTO> hmbColumnList=result.getColumnNameList();		
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
					 logger.info(inArray[j]+" inArray.length " +inArray.length +"  voTableMarker.getValues() "+voTableMarker.getValues().length);
					 voTableMarker.getValues()[j]=inArray[j];					 
				 }
			 	voTableMarker.addRow();
			}
		 }
		if(voTableMarker.getRowCount() > 0) {
			 voTableMarker.writeTable(output);
     	} 
		//Writing end of VOTable.
		voTableMarker.writeEndVOTable(output,comCriteriaTO.getStatus());		 
	
		}catch(Exception pe) {
        	pe.printStackTrace();
        	try {
				output.write(pe.getMessage());
			} catch (IOException e) {
				logger.fatal("   : Exception in CommonDaoImpl:generateVOTableDetails : ", e);
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