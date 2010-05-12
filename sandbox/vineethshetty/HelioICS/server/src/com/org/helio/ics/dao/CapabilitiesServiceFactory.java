/* #ident	"%W%" */
package com.org.helio.ics.dao;


import com.org.helio.ics.dao.impl.CapabilitiesServiceImpl;
import com.org.helio.ics.dao.interfaces.CapabilitiesServiceDao;



public class CapabilitiesServiceFactory {

	private static CapabilitiesServiceFactory instance = null;
	
	private CapabilitiesServiceFactory(){
		//Constructor
	}

	public static CapabilitiesServiceFactory getInstance(){
		if(instance==null){
			instance = new CapabilitiesServiceFactory();
		}
		return instance;
	}

	public CapabilitiesServiceDao getIcsDetailsDAO(){
		return new CapabilitiesServiceImpl();
	}
	
}
