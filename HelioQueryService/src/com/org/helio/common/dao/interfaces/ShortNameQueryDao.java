/* #ident	"%W%" */
package com.org.helio.common.dao.interfaces;

import java.util.HashMap;

import com.org.helio.common.dao.exception.DataNotFoundException;
import com.org.helio.common.dao.exception.ShortNameQueryException;
import com.org.helio.common.transfer.CommonResultTO;



public interface ShortNameQueryDao {
	//public String getShortNameQuery(String sKey)throws  DataNotFoundException;
	public CommonResultTO getSNQueryResult(String sSql, HashMap<String,String> hmArgs) throws ShortNameQueryException;
	public CommonResultTO getSNQueryResult(String sSql,HashMap<String,String> hmArgs,int startRow,int noOfRecords) throws ShortNameQueryException;
}
