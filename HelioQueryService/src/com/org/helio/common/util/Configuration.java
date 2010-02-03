
package com.org.helio.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


public class Configuration {
	
	private Properties prop=new Properties();	
	protected final  Logger logger = Logger.getLogger(this.getClass());
	
	private Configuration()
	{
		try
		{							 
			readPropertiesFile();
			
		}catch(Exception ex)
		{
			logger.fatal(" :  Exception occured in Configuration : While loading property file ", ex);
		}
		
	}
		


	public static Configuration getInstance() {
		return ConfigurationHolder.instance;
	}

	private static class ConfigurationHolder {
		private static Configuration instance = new Configuration();
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
    
	  private static final String PROP_FILE="helio.properties";  
	   public void readPropertiesFile(){  
	        try{  
	        	ClassLoader cl = Configuration.class.getClassLoader();
	        	InputStream fin = cl.getResourceAsStream(PROP_FILE);
	            prop.load(fin); 
	            fin.close();  
	            /* code to use values read from the file*/  
	       }catch(Exception e){  
	          logger.fatal("Failed to read from " + PROP_FILE + " file.", e);
	      }  
	    }
	
	
	
	
}

