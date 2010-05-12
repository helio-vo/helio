/* #ident	"%W%" */
package com.org.helio.common.dao.interfaces;

import java.util.HashMap;

import com.org.helio.common.dao.exception.DetailsNotFoundException;
import com.org.helio.common.dao.exception.InstrumentsDetailsNotSavedException;
import com.org.helio.common.transfer.InstrumentsTO;
import com.org.helio.common.transfer.criteriaTO.InstrumentCriteriaTO;
 
public interface InstrumentDao {
	public void saveInstrumentDetails(InstrumentsTO insTO) throws InstrumentsDetailsNotSavedException;
	public InstrumentsTO editInstrumentDetails(int insID) throws DetailsNotFoundException;
	public void updateInstrumentDetails(InstrumentsTO insTO) throws InstrumentsDetailsNotSavedException;
}
