/* #ident	"%W%" */
package com.org.helio.ics.dao.impl;

import com.org.helio.ics.dao.CapabilitiesServiceFactory;
import com.org.helio.ics.dao.exception.CapabilitiesServiceDetailsNotFoundException;
import com.org.helio.ics.dao.interfaces.CapabilitiesServiceDao;



public class CapabilitiesServiceImpl implements CapabilitiesServiceDao {

	
	public CapabilitiesServiceImpl() {
		
		
	}

	public void getDetails() throws CapabilitiesServiceDetailsNotFoundException {
		try{
			CapabilitiesServiceDao comDao = CapabilitiesServiceFactory.getInstance().getIcsDetailsDAO();
		}catch(Exception ex)
		{			
			throw new CapabilitiesServiceDetailsNotFoundException("Exception ",ex);
		}
	
      }
}