package com.org.helio.server.util;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.org.helio.common.dao.CommonDaoFactory;
import com.org.helio.common.dao.exception.DetailsNotFoundException;
import com.org.helio.common.dao.interfaces.CommonDao;
import com.org.helio.common.transfer.criteriaTO.CommonCriteriaTO;

public class QueryThreadAnalizer extends Thread{

	protected final  Logger logger = Logger.getLogger(this.getClass());
	private CommonCriteriaTO comCriteriaTO;
	public QueryThreadAnalizer(CommonCriteriaTO comCriTO){
		comCriteriaTO=comCriTO;
	}
	public void run () {
		 CommonDao commonNameDao= CommonDaoFactory.getInstance().getCommonDAO();			
			try {				
				commonNameDao.generateVOTableDetails(comCriteriaTO);				
			} catch (DetailsNotFoundException e) {			
				 logger.fatal("   : Exception in QueryThreadAnalizer:run : ", e);
			}
	 }
}
