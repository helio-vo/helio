/* #ident	"%W%" */
package com.org.helio.ils.dao.exception;

public class LocationServiceDetailsNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	String error="";
	public LocationServiceDetailsNotFoundException()
	{
		super();             
		error = "unknown";
	}

	public LocationServiceDetailsNotFoundException(String err)
	{
		super(err);   
		error = err;  
	}
	public LocationServiceDetailsNotFoundException(String err,Throwable tr)
	{
		super(err,tr);   
		error = err;  
	}	

	public String getError()
	{
		return error;
	}
}
