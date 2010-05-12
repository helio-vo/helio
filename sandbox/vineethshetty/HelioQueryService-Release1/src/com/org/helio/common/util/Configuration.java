
package com.org.helio.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;


public class Configuration {
	
    public static ResourceBundle rsbundle=  ResourceBundle.getBundle( "helio" );
  	private final String CSVDIR = "csvpath";
	
	
	public static Configuration getInstance()
	{
		
		
		 rsbundle=null;        
         rsbundle = ResourceBundle.getBundle( "helio" );          
         
        return new Configuration(); 
	}
	
	public  ResourceBundle getResourceBundle(){
		
		return rsbundle;
	}
	
	

	public static void main(String args[])
	{
		System.out.println(Configuration.getInstance().getResourceBundle().getString("FILENAME"));		
	}
	
		
}

