/* #ident	"%W%" */
package com.org.helio.ils.dao;


import com.org.helio.ics.dao.impl.CapabilitiesServiceImpl;
import com.org.helio.ics.dao.interfaces.CapabilitiesServiceDao;
import com.org.helio.ils.dao.impl.LocationServiceImpl;
import com.org.helio.ils.dao.interfaces.LocationServiceDao;



public class LocationServiceFactory {

	private static LocationServiceFactory instance = null;
	
	private LocationServiceFactory(){
		//Constructor
	}

	public static LocationServiceFactory getInstance(){
		if(instance==null){
			instance = new LocationServiceFactory();
		}
		return instance;
	}

	public LocationServiceDao getIlsDetailsDAO(){
		return new LocationServiceImpl();
	}
	
}
