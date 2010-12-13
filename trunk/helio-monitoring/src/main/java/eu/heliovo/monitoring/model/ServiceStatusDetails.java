package eu.heliovo.monitoring.model;

import java.io.Serializable;
import java.net.URL;

/**
 * Service status details are made up by a name, a URL, the actual status (e.g. OK or CRITICAL), the response time if OK
 * and a message.
 * 
 * @author Kevin Seidler
 * 
 */
public interface ServiceStatusDetails extends Serializable {

	ServiceStatus getStatus();

	long getResponseTimeInMillis();

	String getName();

	URL getUrl();

	String getMessage();

}