/* #ident	"%W%" */
package com.org.helio.common.util;

import java.util.HashMap;



public class InstanceHolders {
	private HashMap<String,Process> hmRunningProcess=new HashMap<String,Process>();
	private static InstanceHolders oInstanceHolders =null;
	private InstanceHolders ()
	{
	}
	public static InstanceHolders  getInstance()
	{
		if(oInstanceHolders ==null)
			oInstanceHolders = new InstanceHolders();

		return oInstanceHolders; 
	}

	public synchronized  void addProcess(String sUser,Process oProcess) throws Exception
	{
		if(hmRunningProcess.containsKey(sUser))
		{
			throw new Exception("Could not update database connection details. ");
		}
		hmRunningProcess.put(sUser, oProcess);
	}
	
	public synchronized  void removeProcess(String sUser)
	{
		if(!hmRunningProcess.containsKey(sUser))
		{
			return;
		}
		hmRunningProcess.remove(sUser);
	}
	
	public synchronized  Process getProcess(String sUser)
	{
		if(!hmRunningProcess.containsKey(sUser))
		{
			return null;
		}
		return hmRunningProcess.get(sUser);
	}
	
	public synchronized  boolean isProcessRunning(String sUser)
	{
		if(!hmRunningProcess.containsKey(sUser))
		{
			return false;
		}
		return true;
	}
		
}
