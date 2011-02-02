package eu.heliovo.monitoring.stage;

import static eu.heliovo.monitoring.logging.LoggingTestUtils.getLoggingFactory;
import static eu.heliovo.monitoring.test.util.TestUtils.getStageHelper;
import static eu.heliovo.monitoring.test.util.TestUtils.logFilesUrl;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.ExecutorService;

import org.junit.Test;

import eu.heliovo.monitoring.model.*;
import eu.heliovo.monitoring.test.util.*;

public final class TestingStageTest {

	private final ExecutorService executor = TestUtils.getExecutor();

	public TestingStageTest() throws Exception {
	}

	@Test
	public void testTestStage() throws Exception {

		final TestingStage stage = new TestingStage(getStageHelper(), getLoggingFactory(), logFilesUrl, executor);
		stage.updateServices(TestServices.LIST);
		stage.updateStatus();

		final List<ServiceStatusDetails> serviceStatus = stage.getServicesStatus();

		assertNotNull(serviceStatus);
		assertTrue(serviceStatus.size() == TestServices.LIST.size());

		boolean testedFakeService = false;
		boolean testedNoWsdlService = false;

		String fakeOfflineServiceName = "FakeOfflineService" + TestingStage.SERVICE_NAME_SUFFIX;
		String noWsdlOfflineServiceName = "NoWsdlOfflineService" + TestingStage.SERVICE_NAME_SUFFIX;

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
		for (final ServiceStatusDetails serviceStatusDetails : stage.getServicesStatus()) {
			System.out.println(serviceStatusDetails.toString());
		}
	}

}
