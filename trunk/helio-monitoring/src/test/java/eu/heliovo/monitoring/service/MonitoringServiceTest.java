package eu.heliovo.monitoring.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import eu.heliovo.monitoring.component.MethodCallComponent;
import eu.heliovo.monitoring.component.PingComponent;
import eu.heliovo.monitoring.component.TestingComponent;
import eu.heliovo.monitoring.model.ServiceStatus;

/**
 * Tests the MonitoringService.
 * 
 * @author Kevin Seidler
 * 
 */
public class MonitoringServiceTest extends Assert {

	private final PingComponent pingComponent = new PingComponent();
	private final MethodCallComponent methodCallComponent = new MethodCallComponent("mainlog",
			"http://localhost:8080/helio-monitoring/logs");
	private final TestingComponent testingComponent = new TestingComponent("mainlog",
			"http://localhost:8080/helio-monitoring/logs");
	private final MonitoringServiceImpl monitoringService = new MonitoringServiceImpl(pingComponent,
			methodCallComponent, testingComponent);

	@Test
	public void testService() throws Exception {

		monitoringService.afterPropertiesSet(); // called automatically by spring
		pingComponent.refreshCache(); // also done automatically in runtime
		testingComponent.refreshCache(); // also done automatically in runtime

		// TODO evenutally call MethodCallComponent here

		final List<ServiceStatus> result = monitoringService.getPingStatus();
		assertFalse(result.isEmpty());
		for (final ServiceStatus serviceStatus : result) {
			System.out.println("service: " + serviceStatus.getId() + " status: " + serviceStatus.getState().toString()
					+ " response time: " + serviceStatus.getResponseTime() + " ms");
		}

		// TODO do it as well for method call and testing
	}
}
