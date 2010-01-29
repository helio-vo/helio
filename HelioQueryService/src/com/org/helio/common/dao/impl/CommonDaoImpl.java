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
		
		try{
				
		 ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
		 shortNameDao.getSNQueryResult(comCriteriaTO);
		  				 
		}catch(Exception pe) {
        	pe.printStackTrace();
        	logger.fatal("   : Exception in CommonDaoImpl:generateVOTableDetails : ", pe);
        	
        	return;
        }		
		
	}

	
	
}