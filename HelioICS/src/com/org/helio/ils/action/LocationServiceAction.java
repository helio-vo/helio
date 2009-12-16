package com.org.helio.ils.action;

import org.hibernate.Session;

import com.opensymphony.xwork2.ActionSupport;
import com.org.helio.common.action.CommonAction;
import com.org.helio.common.util.HibernateSessionFactory;

public class LocationServiceAction  extends CommonAction
{	

    public String display(){    	
    	 System.out.println(" +++++++++ Starting hibernate call ++++++");
    	 Session  session =HibernateSessionFactory.getSession();
    	 System.out.println(" +++++++ End of hibernate call+++++++++");
    	return "SUCCESS";
    }
    
   

	
	
}
