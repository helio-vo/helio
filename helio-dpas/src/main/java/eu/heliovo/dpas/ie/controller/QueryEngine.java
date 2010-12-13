package eu.heliovo.dpas.ie.controller;

import java.io.Writer;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

import eu.heliovo.dpas.ie.common.DebugUtilities;
import eu.heliovo.dpas.ie.common.DpasUtilities;
import eu.heliovo.dpas.ie.components.DpasOutputFormatter;
import eu.heliovo.dpas.ie.dataProviders.DPASDataProvider;
import eu.heliovo.dpas.ie.internalData.DPASQueryArgument;
import eu.heliovo.dpas.ie.internalData.DPASRequest;
import eu.heliovo.dpas.ie.internalData.DPASResultItem;
import eu.heliovo.dpas.ie.providers.DataProviderManager;
import eu.heliovo.dpas.ie.providers.DataProviderManagerException;

public class QueryEngine 
{
	private DataProviderManager dpManager = null;
	private DpasUtilities 		dpasUtils = new DpasUtilities();
	private DpasOutputFormatter dpasFormatter = new DpasOutputFormatter();
	private	DebugUtilities		debugUtils	=	new DebugUtilities();

	public QueryEngine(DataProviderManager dpManager) {
		super();
		this.dpManager = dpManager;
	}

	public Map<String, DPASResultItem> execute(DPASQueryArgument q) 
	{
		String currentInstrument 	= null;
		String currentStartTime 	= null;
		String currentStopTime 		= null;
		String currentDataType 		= null;
		String currentDataLevel 	= null;
		/*
		 * Wether the data should be partially or globally sorted
		 */
		boolean partialSorting = q.isPartialSorting();
		/*
		 * The current data providers
		 */
		DPASDataProvider currentDP = null;
		/*
		 * The results
		 */
		Map<String, DPASResultItem> allResults = new HashMap<String, DPASResultItem>();
		Map<String, DPASResultItem> partialResults = new HashMap<String, DPASResultItem>();
		/*
		 * XML Document
		 */
		Document d = dpasFormatter.createNewDocument();

		for (int n = 0; n < q.getNumberOfInstrumentsQueries(); n++) {
			currentInstrument = q.getCurrentInstrument(n);
			currentStartTime = q.getCurrentStartTime(n);
			currentStopTime = q.getCurrentStopTime(n);
			/*
			 * TODO : Add here the management of types and levels Add them to
			 * the query of the data source.
			 */
			debugUtils.printLog(this.getClass().getName(), "Querying for " + currentInstrument + " from "
					+ currentStartTime + " to " + currentStopTime);
			/*
			 * Find here the data provider suitable for the query
			 */
			/*
			 * TODO : Add here the management of multiple data sources.
			 */
			if (dpManager.isPresent(currentInstrument)) 
			{
				try 
				{
					currentDP = (DPASDataProvider) dpManager.getBest(currentInstrument);
					debugUtils.printLog(this.getClass().getName(), "Best provider for " + currentInstrument
							+ " is " + currentDP);

					if (partialSorting)
					{
						allResults = PerformCurrentQuery(currentInstrument,
								currentStartTime, currentStopTime, currentDP);
						
						debugUtils.printLog(this.getClass().getName(), "* Query for " + 
								currentInstrument + 
								" from "+
								currentStartTime +
								" to "+
								currentStopTime+
								" ===> " +
								allResults.size() +
								" number of results");
					}
					else 
					{
						partialResults.clear();
						
						partialResults = PerformCurrentQuery(currentInstrument,
								currentStartTime, currentStopTime, currentDP);
						
						debugUtils.printLog(this.getClass().getName(), "* Query for " + 
								currentInstrument + 
								" from "+
								currentStartTime +
								" to "+
								currentStopTime+
								" ===> " +
								partialResults.size() +
								" number of results");

						allResults.putAll(partialResults);
					}

				} catch (DataProviderManagerException e) {
					debugUtils.printLog(this.getClass().getName(), "Looking for " + currentInstrument
							+ " raised the following exception ! ");
					e.printStackTrace();
				}
			} else {
				debugUtils.printLog(this.getClass().getName(), currentInstrument + " is NOT present ");
			}
		}
		return allResults;
	}

	private Map<String, DPASResultItem> PerformCurrentQuery(
			String currentInstrument, String currentStartTime,
			String currentStopTime, DPASDataProvider currentDataProvider) 
			{
		debugUtils.printLog(this.getClass().getName(), "Executing real query on : " + currentInstrument
				+ " from " + currentStartTime + " to " + currentStopTime);

		List<DPASResultItem> 		tmpResults 		= null;
		Map<String, DPASResultItem> finalResults 	= new HashMap<String, DPASResultItem>();
		Calendar 					sortingDate 	= null;

		try 
		{
			tmpResults = currentDataProvider.query(
					currentInstrument,
					dpasUtils.HELIOTimeToCalendar(currentStartTime), 
					dpasUtils.HELIOTimeToCalendar(currentStopTime), 2);			
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		if(tmpResults != null)
			for (int k = 0; k < tmpResults.size(); k++) 
			{
				/*
				 * Modify the format of the date in the result
				 */
				tmpResults.get(k).instrument	=	currentInstrument;
				sortingDate = (Calendar) tmpResults.get(k).getColumn(0);
				finalResults.put(dpasUtils.calendarToHELIOTime(sortingDate),
						tmpResults.get(k));
			}
			
		return finalResults;
	}

	public DPASRequest execute(Writer printWriter,DPASRequest request,boolean votable) throws Exception 
	{
		String currentInstrument 	= null;
		String currentStartTime 	= null;
		String currentStopTime 		= null;
		String currentDataType 		= null;
		String currentDataLevel 	= null;
		/*
		 * Wether the data should be partially or globally sorted
		 */
		boolean partialSorting 		= false;
		/*
		 * The current data providers
		 */
		DPASDataProvider currentDP = null;
		/*
		 * The results
		 */
		Map<String, DPASResultItem> allResults = new HashMap<String, DPASResultItem>();
		Map<String, DPASResultItem> partialResults = new HashMap<String, DPASResultItem>();
		/*
		 * XML Document
		 */
		Document d = dpasFormatter.createNewDocument();

		DPASQueryArgument	q	=	request.getRequestArgument();
		partialSorting 		= q.isPartialSorting();
		
		for (int n = 0; n < q.getNumberOfInstrumentsQueries(); n++) 
		{
			currentInstrument = q.getCurrentInstrument(n);
			currentStartTime = q.getCurrentStartTime(n);
			currentStopTime = q.getCurrentStopTime(n);
			/*
			 * TODO : Add here the management of types and levels Add them to
			 * the query of the data source.
			 */
			/*
			 * Find here the data provider suitable for the query
			 */
			/*
			 * TODO : Add here the management of multiple data sources.
			 */
			if (dpManager.isPresent(currentInstrument)) 
			{
				try 
				{
					currentDP = (DPASDataProvider) dpManager.getBest(currentInstrument);
					debugUtils.printLog(this.getClass().getName(), "Best provider for " + currentInstrument
							+ " is " + currentDP);

					if (partialSorting)
					{
						allResults = PerformCurrentQuery(currentInstrument,
								currentStartTime, currentStopTime, currentDP);
						
						debugUtils.printLog(this.getClass().getName(), "* Query for " + 
								currentInstrument + 
								" from "+
								currentStartTime +
								" to "+
								currentStopTime+
								" ===> " +
								allResults.size() +
								" number of results");
					}
					else 
					{
						partialResults.clear();
						
						partialResults = PerformCurrentQuery(currentInstrument,
								currentStartTime, currentStopTime, currentDP);
						
						debugUtils.printLog(this.getClass().getName(), "* Query for " + 
								currentInstrument + 
								" from "+
								currentStartTime +
								" to "+
								currentStopTime+
								" ===> " +
								partialResults.size() +
								" number of results");

						allResults.putAll(partialResults);
					}

					if (partialSorting){
						if(votable){
							dpasFormatter.createVOTable(printWriter,allResults);
						}else{
							d = dpasFormatter.addSortedRealToDocument(d,
								allResults);
						}
					}
				

				} catch (DataProviderManagerException e) {
					debugUtils.printLog(this.getClass().getName(), "Looking for " + currentInstrument
							+ " raised the following exception ! ");
					e.printStackTrace();
				}
			} else {
				debugUtils.printLog(this.getClass().getName(), currentInstrument + " is NOT present ");
			}
		}
		if (!partialSorting){
			if(votable){
			dpasFormatter.createVOTable(printWriter,allResults);
			}else{
			d = dpasFormatter.addSortedRealToDocument(d,
					allResults);
			}
		}
		request.setXmlOutput(d);
		
		return request;	
	}
}