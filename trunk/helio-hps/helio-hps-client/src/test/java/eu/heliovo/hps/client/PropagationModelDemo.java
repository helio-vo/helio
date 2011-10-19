package eu.heliovo.hps.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import javax.xml.ws.BindingProvider;

import eu.heliovo.hps.server.AbstractApplicationDescription;
import eu.heliovo.hps.server.HPSService;
import eu.heliovo.hps.server.HPSServiceService;
import eu.heliovo.shared.common.utilities.LogUtilities;
import eu.heliovo.shared.hps.ApplicationExecutionStatus;

public class PropagationModelDemo 
{
	/*
	 * Various utilities
	 */
	LogUtilities		logUtilities	=	new LogUtilities();
	/*
	 * The Service Stubs
	 */
	HPSServiceService	hpsSS			=	new HPSServiceService();
//	String				serviceAddress	=	"http://localhost:8080/helio-hps-server/hpsService";
	String  			serviceAddress	=	"http://cagnode58.cs.tcd.ie:8080/helio-hps-server/hpsService";

	public static void main(String[] args) 
	{
		PropagationModelDemo	pmDemo	=	new PropagationModelDemo();
//		pmDemo.runPropagationModel("CME", "2010-01-01T00:00", "0", "45", "300", "0");
//		pmDemo.runPropagationModel("2010-01-01T00:00", "0", "45", "300");
		pmDemo.runPropagationModel();
	}

	private void runPropagationModel() 
		{
			HPSService	hpsService	=	hpsSS.getHPSServicePort();
			((BindingProvider)hpsService).getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
					serviceAddress);
	
			Boolean							fastExecution	=	true;
			int								numJobs			=	1;
			AbstractApplicationDescription	app				= 	null;
			String 							key 			= 	null;
			String							exeId			=	null;
			String							exeStatus		=	null;
			String							exeOutput		=	null;
			InputStreamReader 				reader 			=	new InputStreamReader(System.in);
			BufferedReader 					in 				= 	new BufferedReader(reader);
	
			try 
			{
				/*
				 * Retrieve the application list and selecting the appropriate propagation model. 
				 */
				Vector<AbstractApplicationDescription> res = new Vector<AbstractApplicationDescription>();
				res.addAll(hpsService.getPresentApplications());

				for(int n = 0; n < res.size(); n++)
				{
					System.out.println("[" + 
							n
							+ "] ==> : " + 
							res.get(n).getName());
				}
				/*
				 * Select the propagation model you want to use. 
				 */
				System.out.print(" * Enter corresponding value : ");
				key = in.readLine();
				app	=	res.get(Integer.valueOf(key).intValue());		
				System.out.println(" * You have selected : " + app.getName());
				/*
				 * Select parameters for the propagation model 
				 */
				for(int currParam = 0; currParam < app.getParameters().size(); currParam++)
				{
					System.out.print(" * Enter value for " + 
							app.getParameters().get(currParam).getName() + " [" +
							app.getParameters().get(currParam).getType() + "] : ");
					key = in.readLine();
					app.getParameters().get(currParam).setValue(key);
				}	
				/*
				 * Submitting one instance of the application to the fast computation resource 
				 */
				exeId = hpsService.executeApplication(app, fastExecution , numJobs);
				logUtilities.printShortLogEntry(app.getName() + " is being executed with execution ID " + exeId);
				/*
				 * Wait until the application is completed
				 */
				exeStatus = hpsService.getStatusOfExecution(exeId);
				logUtilities.printShortLogEntry(exeId + " --> " + exeStatus);
				while(!exeStatus.equals(ApplicationExecutionStatus.Completed))
				{
					exeStatus = hpsService.getStatusOfExecution(exeId);
					logUtilities.printShortLogEntry(exeId + " --> " + exeStatus);
					Thread.sleep(1000);
				}
				exeOutput	=	"http://cagnode58.cs.tcd.ie/output_dir/"+exeId;
				logUtilities.printShortLogEntry("Results of the propagation model are available in " + exeOutput);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			logUtilities.printShortLogEntry("--------------------------------------------------");
			logUtilities.printShortLogEntry("... done");		
		}

	private void runPropagationModel(
			String	pmType,
			String 	time,
			String 	angle,
			String 	width,
			String 	speed,
			String 	speedError) 
	{
		HPSService	hpsService	=	hpsSS.getHPSServicePort();
		((BindingProvider)hpsService).getRequestContext().put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				serviceAddress);

		Boolean							fastExecution	=	true;
		int								numJobs			=	1;
		AbstractApplicationDescription	app				= 	null;
		String 							key 			= 	null;
		String							exeId			=	null;
		String							exeStatus		=	null;
		String							exeOutput		=	null;
		InputStreamReader 				reader 			=	new InputStreamReader(System.in);
		BufferedReader 					in 				= 	new BufferedReader(reader);

		try 
		{
			/*
			 * Retrieve the application list and select the propagation model. 
			 */
			Vector<AbstractApplicationDescription> res = new Vector<AbstractApplicationDescription>();
			res.addAll(hpsService.getPresentApplications());
			app	=	res.get(0);		
			/*
			 * Define parameters 
			 */
			/*
			 * Date 
			 */
			app.getParameters().get(0).setValue(time);
			/*
			 * Longitude 
			 */
			app.getParameters().get(1).setValue(angle);
			/*
			 * Width 
			 */
			app.getParameters().get(2).setValue(width);			
			/*
			 * Speed 
			 */
			app.getParameters().get(3).setValue(speed);

			exeId = hpsService.executeApplication(app, fastExecution , numJobs);
			logUtilities.printShortLogEntry(app.getName() + " is being executed with execution ID " + exeId);
			/*
			 * Wait until the application is completed
			 */
			
			exeStatus = hpsService.getStatusOfExecution(exeId);
			logUtilities.printShortLogEntry(exeId + " --> " + exeStatus);
			while(!exeStatus.equals(ApplicationExecutionStatus.Completed))
			{
				exeStatus = hpsService.getStatusOfExecution(exeId);
				logUtilities.printShortLogEntry(exeId + " --> " + exeStatus);
				Thread.sleep(1000);
			}
//			/*
//			 * If the application is completed, retrieve the output and
//			 * remove from the list of running applications
//			 */
//			if (exeStatus.equals(ApplicationExecutionStatus.Completed)) 
//			{
//				logUtilities.printShortLogEntry(exeId + " --> " + hpsService.getOutputOfExecution(exeId));
//			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		logUtilities.printShortLogEntry("--------------------------------------------------");
		logUtilities.printShortLogEntry("... done");		
	}
}
