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
		 
		 //getting list name.
		 String[] listName=comCriteriaTO.getListName().split("::");
		 HashMap<String,String> params  = new HashMap<String,String>();
		 
		 params.put("kwstartdate", comCriteriaTO.getStartDateTime());
		 params.put("kwenddate", comCriteriaTO.getEndDateTime());
		 params.put("kwinstrument", comCriteriaTO.getInstruments());
		 params.put("kwlistname", comCriteriaTO.getListName());
				 
		 comCriteriaTO.setParamData(params);
		 
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
		 
		 comCriteriaTO.setQuery(query);
		 
		 ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
		 shortNameDao.getSNQueryResult(comCriteriaTO);
		  				 
		}catch(Exception pe) {
        	pe.printStackTrace();
        	logger.fatal("   : Exception in CommonDaoImpl:generateVOTableDetails : ", pe);
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
					logger.fatal("   : Exception in CommonDaoImpl:generateVOTableDetails : ", e);
				}
			}
        	return;
        }		
		
	}

	
	
}