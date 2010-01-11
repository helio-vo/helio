/* #ident	"%W%" */
package com.org.helio.common.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

public class ConfigurationProfiler {

	private Properties prop=new Properties();	
	private String sProfileFilePath ;
	protected final  Logger logger = Logger.getLogger(this.getClass());
	
	private ConfigurationProfiler()
	{
		try
		{
			
			logger.info(" Env file path "+System.getenv("file.path"));
			if(sProfileFilePath==null || sProfileFilePath.equals(""))
				sProfileFilePath=System.getenv("file.path");
			loadPropertyValues();			
			TimerTask task = new FileWatcher(new File(sProfileFilePath)) {
			      protected void onChange( File file ) {
			      	try {			      		
			      		loadPropertyValues();
				    } catch (Exception e) {
			      		e.printStackTrace();
			      	}
			      }
				};

			    Timer timer = new Timer();
			    // check every 10 min 
			    timer.schedule(task , new Date(), 1000 * 60); // refresh after 600000 miliseconds  = 10 mins

			
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	
	private void loadPropertyValues()
	{
		/*
		 * New property variable is loaded to safeguard from loassing the properties values in case of error while refresh.
		 * */
		try{
			System.out.println("++++++++++++++++++++ Property Loaded +++++++++++++++++++++");
			Properties newProp= new Properties();
			newProp.load(new  FileInputStream( new File(sProfileFilePath)));
			prop=newProp;
			
			//System.out.println(ConfigurationProfiler.getInstance().getProperty("FILENAME"));
			
			
		}catch(Exception ex)
		{			
			ex.printStackTrace();
		}
	}

	public static ConfigurationProfiler getInstance() {
		return ConfigurationProfilerHolder.instance;
	}

	private static class ConfigurationProfilerHolder {
		private static ConfigurationProfiler instance = new ConfigurationProfiler();
	}
	
	public String getProperty(String sKey)
	{
		try{
			return prop.getProperty(sKey).toString();
		}catch(Exception ex)
		{
			return "";
		}
	}
	
	public Enumeration getAllKeys(){
		return prop.propertyNames();
	}
	
	public void setProperty(String sKey,String value) throws IOException 
    { 
         prop.setProperty(sKey, value); 
    } 

	
}
