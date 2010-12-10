package eu.heliovo.monitoring.component;

import static eu.heliovo.monitoring.model.ServiceFactory.newServiceStatusDetails;

import java.util.*;

import org.apache.xmlbeans.XmlException;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Component;

import com.eviware.soapui.impl.wsdl.*;
import com.eviware.soapui.impl.wsdl.submit.transports.http.WsdlResponse;
import com.eviware.soapui.model.testsuite.AssertionError;

import eu.heliovo.monitoring.logging.*;
import eu.heliovo.monitoring.model.*;
import eu.heliovo.monitoring.util.WsdlValidationUtils;
/**
 * Just calls one method of every service to see that it is working.
 * 
 * @author Kevin Seidler
 * 
 */
@Component
public final class MethodCallComponent extends AbstractComponent {

	private final ComponentHelper componentHelper;
	private final LoggingFactory loggingFactory;
	private final String logFilesUrl; // TODO should be moved somewhere in the logging classes
	// private final ExecutorService executor;

	private static final String LOG_FILE_SUFFIX = "_method-call_";
	private static final boolean TEST_FOR_SOAP_FAULT = false;

	@Autowired
	public MethodCallComponent(ComponentHelper componentHelper, LoggingFactory loggingFactory,
			@Value("${monitoringService.logUrl}") String logFilesUrl /* , ExecutorService executor */) {

		super(" -method call-");

		this.componentHelper = componentHelper;
		this.loggingFactory = loggingFactory;
		this.logFilesUrl = logFilesUrl;
		// this.executor = executor;
	}

	// TODO cache WSDL documents to avoid the import on every run
	// see http://www.soapui.org/architecture/integration.html
	// see http://www.soapui.org/userguide/functional/response-assertions.html, download source of soapUI &
	// search for files containing "assertion"
	@Override
	public void refreshCache() {

		final List<ServiceStatusDetails> newCache = new ArrayList<ServiceStatusDetails>();

		for (Service service : super.getServices()) {

			String serviceName = service.getName() + super.getServiceNameSuffix();
			String serviceUrlAsString = service.getUrl().toString();
			String logFileWriterName = service.getName() + LOG_FILE_SUFFIX;
			LogFileWriter logFileWriter = loggingFactory.newLogFileWriter(logFileWriterName);

			try {

				// WsdlInterface wsdlInterface = importWsdl(logFileWriter, serviceUrlAsString);
				WsdlInterface wsdlInterface = componentHelper.importWsdl(logFileWriter, serviceUrlAsString);
				WsdlOperation operation = selectOperation(wsdlInterface);
				WsdlRequest request = componentHelper.createRequest(wsdlInterface, logFileWriter, operation);
				WsdlResponse response = componentHelper.submitRequest(request, logFileWriter);
				componentHelper.processResponse(response, serviceName, service, logFileWriter);
				ServiceStatusDetails serviceStatus = buildServiceStatus(response, serviceName, service, logFileWriter);

				newCache.add(serviceStatus);
				wsdlInterface.getProject().release();

			} catch (Exception e) {
				componentHelper.handleException(e, logFileWriter, serviceName, service, newCache);
			}
			logFileWriter.close();
		}
		super.setCache(newCache);
	}

	// private WsdlInterface importWsdl(LogFileWriter logFileWriter, final String wsdlUrl) throws XmlException,
	// IOException, SoapUIException, InterruptedException, ExecutionException {
	// return new ImportWsdlCommand(logFileWriter, wsdlUrl, executor).execute();
	// }

	private WsdlOperation selectOperation(final WsdlInterface wsdlInterface) {
		return wsdlInterface.getOperationAt(0);
	}

	private ServiceStatusDetails buildServiceStatus(final WsdlResponse response, final String serviceName,
			final Service service, final LogFileWriter logFileWriter) throws XmlException {

		// build message
		final StringBuffer stringBuffer = new StringBuffer("Service is working");
		ServiceStatus status = ServiceStatus.OK;

		// response validation
		final AssertionError[] responseAssertionErrors = WsdlValidationUtils.validateResponse(response);
		LoggingHelper.logResponseValidation(responseAssertionErrors, logFileWriter);

		if (responseAssertionErrors != null && responseAssertionErrors.length > 0) {

			stringBuffer.append(", but the repsonse is not valid");
			status = ServiceStatus.CRITICAL;

		} else if (TEST_FOR_SOAP_FAULT) {

			final WsdlOperation operation = response.getRequest().getOperation();
			if (WsdlValidationUtils.isSoapFault(response.getContentAsString(), operation)) {
				stringBuffer.append(", but the response is a SOAP fault");
				logFileWriter.write("The response is a SOAP fault!");
				status = ServiceStatus.WARNING;
			}
		}

		final long responseTime = response.getTimeTaken();
		stringBuffer.append(", response time = " + responseTime + " ms");
		stringBuffer.append(LoggingHelper.getLogFileText(logFileWriter, logFilesUrl));
		final String message = stringBuffer.toString();

		return newServiceStatusDetails(serviceName, service.getUrl(), status, responseTime, message);
	}
}