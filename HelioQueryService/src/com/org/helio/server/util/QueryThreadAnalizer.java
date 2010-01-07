package com.org.helio.server.util;

import java.io.IOException;

import com.org.helio.common.dao.CommonDaoFactory;
import com.org.helio.common.dao.exception.DetailsNotFoundException;
import com.org.helio.common.dao.interfaces.CommonDao;
import com.org.helio.common.transfer.criteriaTO.CommonCriteriaTO;

public class QueryThreadAnalizer extends Thread{

	private CommonCriteriaTO comCriteriaTO;
	public QueryThreadAnalizer(CommonCriteriaTO comCriTO){
		comCriteriaTO=comCriTO;
	}
	 public void run () {
		 CommonDao commonNameDao= CommonDaoFactory.getInstance().getCommonDAO();			
			try {
				
					//comCriteriaTO.getPrintWriter().write("<helio:queryResponse xmlns:helio=\"http://helio.org/xml/QueryService/v1.0\">");
				
				commonNameDao.generateVOTableDetails(comCriteriaTO);
				//comCriteriaTO.getPrintWriter().write("</helio:queryResponse>");
			
			} catch (DetailsNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
}
