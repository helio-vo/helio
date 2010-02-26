/* #ident	"%W%" */
package com.org.helio.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.org.helio.common.util.InstanceHolders;


public class StartupServlet extends HttpServlet {
 
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {

		try{
			
			ClassLoader loader = this.getClass().getClassLoader();
			// check id test.txt available.
			String sProfileFilePath=loader.getResource("test.txt").getFile();
			if(sProfileFilePath!=null && !sProfileFilePath.equals("")){
				InstanceHolders.getInstance().setProperty("hsqldb.database.path",sProfileFilePath.replaceAll("/test.txt", ""));
			}
			
			System.out.println("++++++++++ Servleter started +++++++++++++");
			
		}
		catch(Exception ex)
		{
			System.out.println("Exception: "+ex);
			ex.printStackTrace();
		}
	}
}
