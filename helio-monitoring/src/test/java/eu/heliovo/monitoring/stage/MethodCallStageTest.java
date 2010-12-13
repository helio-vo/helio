package eu.heliovo.monitoring.stage;

import static eu.heliovo.monitoring.logging.LoggingTestUtils.getLoggingFactory;
import static eu.heliovo.monitoring.test.util.TestUtils.getComponentHelper;
import static eu.heliovo.monitoring.test.util.TestUtils.logFilesUrl;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import eu.heliovo.monitoring.model.*;
import eu.heliovo.monitoring.stage.MethodCallStage;
import eu.heliovo.monitoring.test.util.TestServices;

public class MethodCallStageTest extends Assert {

	@Test
	public void testMethodCallComponent() throws Exception {

		// TODO test individual methods of MethodCallComponent

		MethodCallStage component = new MethodCallStage(getComponentHelper(), getLoggingFactory(), logFilesUrl);
		component.setServices(TestServices.LIST);
		component.updateStatus();

		final List<ServiceStatusDetails> serviceStatus = component.getServicesStatus();

		assertNotNull(serviceStatus);
		assertTrue(serviceStatus.size() == TestServices.LIST.size());

		boolean testedFakeService = false;
		boolean testedNoWsdlService = false;

		String fakeOfflineServiceName = "FakeOfflineService" + MethodCallStage.SERVICE_NAME_SUFFIX;
		String noWsdlOfflineServiceName = "NoWsdlOfflineService" + MethodCallStage.SERVICE_NAME_SUFFIX;

		for (final ServiceStatusDetails actualServiceStatusDetails : serviceStatus) {
			if (actualServiceStatusDetails.getName().equals(fakeOfflineServiceName)) {
				testedFakeService = true;
				assertTrue(actualServiceStatusDetails.getStatus().equals(ServiceStatus.CRITICAL));
			}
			if (actualServiceStatusDetails.getName().equals(noWsdlOfflineServiceName)) {
				testedNoWsdlService = true;
				assertTrue(actualServiceStatusDetails.getStatus().equals(ServiceStatus.CRITICAL));
			}
		}
		assertTrue(testedFakeService);
		assertTrue(testedNoWsdlService);

		System.out.println("=== Services to be tested ===");
		for (final Service service : TestServices.LIST) {
			System.out.println(service.getName() + " " + service.getUrl());
		}

		System.out.println("=== testing results");
		for (final ServiceStatusDetails serviceStatusDetails : component.getServicesStatus()) {
			System.out.println(serviceStatusDetails.toString());
		}
	}
}