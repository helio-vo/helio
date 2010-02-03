/* #ident	"%W%" */
package com.org.helio.common.dao;

import com.org.helio.common.dao.impl.CommonDaoImpl;
import com.org.helio.common.dao.impl.ShortNameQueryDaoImpl;
import com.org.helio.common.dao.interfaces.CommonDao;
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
	
	
}
