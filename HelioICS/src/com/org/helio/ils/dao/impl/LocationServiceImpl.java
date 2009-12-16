/* #ident	"%W%" */
package com.org.helio.ils.dao.impl;

import com.org.helio.ics.dao.CapabilitiesServiceFactory;
import com.org.helio.ics.dao.exception.CapabilitiesServiceDetailsNotFoundException;
import com.org.helio.ics.dao.interfaces.CapabilitiesServiceDao;
import com.org.helio.ils.dao.LocationServiceFactory;
import com.org.helio.ils.dao.exception.LocationServiceDetailsNotFoundException;
import com.org.helio.ils.dao.interfaces.LocationServiceDao;



public class LocationServiceImpl implements LocationServiceDao {

	
	public LocationServiceImpl() {
		
		
	}

	public void getDetails() throws LocationServiceDetailsNotFoundException {
		try{
			LocationServiceDao comDao = LocationServiceFactory.getInstance().getIlsDetailsDAO();
		}catch(Exception ex)
		{			
			throw new LocationServiceDetailsNotFoundException("Exception ",ex);
		}
	
      }
}