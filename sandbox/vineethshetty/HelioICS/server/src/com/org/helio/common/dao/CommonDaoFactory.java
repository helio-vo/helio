/* #ident	"%W%" */
package com.org.helio.common.dao;

import com.org.helio.common.dao.impl.CommonDaoImpl;
import com.org.helio.common.dao.impl.InstrumentDaoImpl;
import com.org.helio.common.dao.impl.InstrumentOperationPeriodDaoImpl;
import com.org.helio.common.dao.impl.ObservatoryDaoImpl;
import com.org.helio.common.dao.impl.ShortNameQueryDaoImpl;
import com.org.helio.common.dao.interfaces.CommonDao;
import com.org.helio.common.dao.interfaces.InstrumentDao; 
import com.org.helio.common.dao.interfaces.InstrumentOperationPeriodDao;
import com.org.helio.common.dao.interfaces.ObservatoryDao;
import com.org.helio.common.dao.interfaces.ShortNameQueryDao;



public class CommonDaoFactory {

	private static CommonDaoFactory instance = null;
	
	private CommonDaoFactory(){
		//Constructor
	}

	public static CommonDaoFactory getInstance(){
		if(instance==null){
			instance = new CommonDaoFactory();
		}
		return instance;
	}

	public CommonDao getCommonDAO(){
		return new CommonDaoImpl();
	}
	public ShortNameQueryDao getShortNameQueryDao(){
		return new ShortNameQueryDaoImpl();
	}
	public InstrumentDao getInstrumentDao(){
		return new InstrumentDaoImpl();
	}
	public InstrumentOperationPeriodDao getInstrumentOperationPeriodDao(){
		return new InstrumentOperationPeriodDaoImpl();
	}
	public ObservatoryDao getObservatoryDao(){
		return new ObservatoryDaoImpl();
	}
	
}
