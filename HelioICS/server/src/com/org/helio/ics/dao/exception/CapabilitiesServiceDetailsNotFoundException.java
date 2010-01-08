/* #ident	"%W%" */
package com.org.helio.ics.dao.exception;

public class CapabilitiesServiceDetailsNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	String error="";
	public CapabilitiesServiceDetailsNotFoundException()
	{
		super();             
		error = "unknown";
	}

	public CapabilitiesServiceDetailsNotFoundException(String err)
	{
		super(err);   
		error = err;  
	}
	public CapabilitiesServiceDetailsNotFoundException(String err,Throwable tr)
	{
		super(err,tr);   
		error = err;  
	}	

	public String getError()
	{
		return error;
	}
}
