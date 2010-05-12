/* #ident	"%W%" */
package com.org.helio.common.dao.interfaces;

import java.util.HashMap;
import java.util.List;

import com.org.helio.common.dao.exception.DetailsNotFoundException;
import com.org.helio.common.dao.exception.InstrumentsDetailsNotSavedException;
import com.org.helio.common.transfer.CommonTO;
import com.org.helio.common.transfer.InstrumentOperationPeriodTO;
import com.org.helio.common.transfer.InstrumentsTO;
import com.org.helio.common.transfer.criteriaTO.InstrumentCriteriaTO;
 
public interface InstrumentOperationPeriodDao {
	public void saveInstrumentOperationPeriodDetails(InstrumentOperationPeriodTO insTO) throws InstrumentsDetailsNotSavedException;
	public InstrumentOperationPeriodTO editInstrumentOperationPeriodDetails(int insID) throws DetailsNotFoundException;
	public void updateInstrumentOperationPeriodDetails(InstrumentOperationPeriodTO insTO) throws InstrumentsDetailsNotSavedException;
	public List<CommonTO> getInstrumentNames(String insId);
	public List<CommonTO> getInstrumentDescription(String insId);
}
