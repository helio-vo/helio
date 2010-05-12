/* #ident	"%W%" */
package com.org.helio.common.dao.interfaces;

import java.util.HashMap;

import com.org.helio.common.dao.exception.DetailsNotFoundException;
import com.org.helio.common.transfer.criteriaTO.InstrumentCriteriaTO;
import com.org.helio.common.transfer.criteriaTO.InstrumentOperationPeriodCriteriaTO;
import com.org.helio.common.transfer.criteriaTO.ObservatoryCriteriaTO;
 
public interface CommonDao {
	public InstrumentCriteriaTO getInstrumentDetails(InstrumentCriteriaTO insCriteriaTO) throws DetailsNotFoundException ;
	public InstrumentOperationPeriodCriteriaTO getInstrumentOperationPeriodDetails(InstrumentOperationPeriodCriteriaTO insOpsPeriodCriteriaTO) throws DetailsNotFoundException ;
	public ObservatoryCriteriaTO getObservatoryDetails(ObservatoryCriteriaTO obsCriteriaTO) throws DetailsNotFoundException ;
}
