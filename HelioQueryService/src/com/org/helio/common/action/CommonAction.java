package com.org.helio.common.action;

import org.hibernate.Session;
import com.opensymphony.xwork2.ActionSupport;
import com.org.helio.common.util.HibernateSessionFactory;
 
public class CommonAction  extends ActionSupport
{	
	
    private static final long serialVersionUID = 1L;

	public String display(){ 
    
    	return "SUCCESS";
    }
       
}
