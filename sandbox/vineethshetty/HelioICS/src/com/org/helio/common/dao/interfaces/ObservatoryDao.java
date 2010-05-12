/* #ident	"%W%" */
package com.org.helio.common.dao.interfaces;

import java.util.HashMap;

import com.org.helio.common.dao.exception.DetailsNotFoundException;
import com.org.helio.common.dao.exception.InstrumentsDetailsNotSavedException;
import com.org.helio.common.dao.exception.ObservatoryDetailsNotSavedException;
import com.org.helio.common.transfer.InstrumentsTO;
import com.org.helio.common.transfer.ObservatoryTO;
import com.org.helio.common.transfer.criteriaTO.InstrumentCriteriaTO;
 
public interface ObservatoryDao {
	public void saveObservatoryDetails(ObservatoryTO obsTO) throws ObservatoryDetailsNotSavedException;
	public ObservatoryTO editObservatoryDetails(int obsID) throws DetailsNotFoundException;
	public void updateObservatoryDetails(ObservatoryTO obsTO) throws ObservatoryDetailsNotSavedException;
}
