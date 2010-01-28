/* #ident	"%W%" */
package com.org.helio.common.dao.interfaces;

import java.util.HashMap;

import com.org.helio.common.dao.exception.DetailsNotFoundException;
import com.org.helio.common.transfer.criteriaTO.CommonCriteriaTO;

 
public interface CommonDao {
	public void generateVOTableDetails(CommonCriteriaTO comCriteriaTO) throws DetailsNotFoundException;
}
