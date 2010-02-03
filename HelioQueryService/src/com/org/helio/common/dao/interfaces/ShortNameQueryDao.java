/* #ident	"%W%" */
package com.org.helio.common.dao.interfaces;

import java.sql.Connection;
import java.util.HashMap;

import com.org.helio.common.dao.exception.DataNotFoundException;
import com.org.helio.common.dao.exception.DetailsNotFoundException;
import com.org.helio.common.dao.exception.ShortNameQueryException;
import com.org.helio.common.transfer.CommonResultTO;
import com.org.helio.common.transfer.CommonTO;
import com.org.helio.common.transfer.criteriaTO.CommonCriteriaTO;



public interface ShortNameQueryDao {
	//public String getShortNameQuery(String sKey)throws  DataNotFoundException;
	//public CommonResultTO getSNQueryResult(String sSql, HashMap<String,String> hmArgs) throws ShortNameQueryException;
	//public CommonResultTO getSNQueryResult(String sSql,HashMap<String,String> hmArgs,int startRow,int noOfRecords) throws ShortNameQueryException;
	
	public void getSNQueryResult(CommonCriteriaTO comCriteriaTO) throws ShortNameQueryException;
	public void getSNQueryResult(CommonCriteriaTO comCriteriaTO, int startRow, int noOfRecords) throws ShortNameQueryException;
	public void generateVOTableDetails(CommonCriteriaTO comCriteriaTO) throws DetailsNotFoundException,Exception;
	public HashMap<String,String> getDatabaseTableNames(Connection con) throws Exception;
	public CommonTO[] getTableColumnNames(Connection con,String tableName) throws Exception;
	
}
