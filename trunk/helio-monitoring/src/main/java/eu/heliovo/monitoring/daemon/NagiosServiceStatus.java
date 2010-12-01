package eu.heliovo.monitoring.daemon;

/**
 * Available service status given by Nagios. Do not change the ordering, because the ordinals are used!
 * 
 * @author Kevin Seidler
 * 
 */
public enum NagiosServiceStatus {
	OK, WARNING, CRITICAL, UNKNOWN
}
