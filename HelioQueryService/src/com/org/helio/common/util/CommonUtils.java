/* #ident	"%W%" */
package com.org.helio.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;


public class CommonUtils {
	private static final String ARGSUFFIX = ":]";

	private static final String ARGPREFIX = "[:";
	
	public static String replaceParams(String sText , HashMap<String,String> hmArgs)
	{
		StringBuffer sTextQuery = new StringBuffer(sText);
		if (hmArgs != null) {
			int argBeginIndex;
			while ((argBeginIndex = sTextQuery.indexOf(ARGPREFIX)) != -1) {
				int argEndIndex = sTextQuery.indexOf(ARGSUFFIX);
				String argKey = sTextQuery.substring(argBeginIndex + ARGPREFIX.length(), argEndIndex);
				String argValue = (String) hmArgs.get(argKey);
				if (argValue == null) {
					//sTextQuery = sTextQuery.delete(argBeginIndex, argEndIndex + sTextQuery.length());
					sTextQuery = sTextQuery.replace(argBeginIndex, argEndIndex + ARGSUFFIX.length(), "");
				} else {
					sTextQuery = sTextQuery.replace(argBeginIndex, argEndIndex + ARGSUFFIX.length(), argValue);
				}
			}
		}
		return sTextQuery.toString();
	}
	
	  public static String getPropertyFilePath() throws NamingException{
			InitialContext initCtx = new InitialContext();			
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			String  propertyBean = (String ) envCtx.lookup("property/context");
			return propertyBean;
	  }	
	
}
