/* #ident	"%W%" */
package com.org.helio.common.dao.interfaces;

import java.util.HashMap;

import com.org.helio.common.dao.exception.DataNotFoundException;
import com.org.helio.common.dao.exception.ShortNameQueryException;
import com.org.helio.common.transfer.CommonResultTO;
import com.org.helio.common.transfer.criteriaTO.CommonCriteriaTO;



public interface ShortNameQueryDao {
	//public String getShortNameQuery(String sKey)throws  DataNotFoundException;
	//public CommonResultTO getSNQueryResult(String sSql, HashMap<String,String> hmArgs) throws ShortNameQueryException;
	//public CommonResultTO getSNQueryResult(String sSql,HashMap<String,String> hmArgs,int startRow,int noOfRecords) throws ShortNameQueryException;
	
	public void getSNQueryResult(CommonCriteriaTO comCriteriaTO) throws ShortNameQueryException;
	public void getSNQueryResult(CommonCriteriaTO comCriteriaTO, int startRow, int noOfRecords) throws ShortNameQueryException;
}
