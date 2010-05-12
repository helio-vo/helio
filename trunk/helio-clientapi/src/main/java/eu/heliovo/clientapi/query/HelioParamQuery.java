package eu.heliovo.clientapi.query;

import java.util.Map;

import eu.heliovo.clientapi.result.HelioJob;
import eu.heliovo.clientapi.result.HelioResultSet;

/**
 * Methods to execute parameterized queries on the Helio system. 
 * @author marco soldati at fhnw ch
 *
 */
public interface HelioParamQuery {

	/**
	 * Execute a parameterized query. A set of well defined parameters is used to issue a specific query
	 * on the helio system.
	 * @param params
	 * @return
	 */
	public HelioJob querySync(Map<String, Object> params);

	/**
	 * Execute a parameterized query. A set of well defined parameters is used to issue a specific query
	 * on the helio system.
	 * @param params
	 * @return a future object that provides control over the status of asynchronous queries. 
	 */
	public HelioJob queryASync(Map<String, Object> params);
	
	
	/**
	 * Get the description of the allowed parameters in a specific context.
	 * The context is  
	 * @param context the context in which the  
	 * @return
	 */
	public HelioParameter[] getParameterDescription(Map<String, Object> context);
	
}
