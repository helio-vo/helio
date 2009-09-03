package org.egso.provider.query;

import java.util.Random;

import org.apache.log4j.Logger;
import org.egso.common.context.EGSOContext;
import org.egso.common.context.EGSOContextFactory;
import org.egso.common.services.provider.ResponseFileQueryProvider;
import org.egso.common.services.provider.ResponseQueryProvider;
import org.egso.provider.ProviderConfiguration;
import org.egso.provider.datamanagement.datapresentation.DataPresentationManager;
import org.egso.provider.service.CoSECService;
import org.egso.provider.service.ServiceLoader;



public class QueryEngine {


	private DataPresentationManager dataPresentationManager = null;
	private QueryValidator queryValidator = null;
	private ServiceLoader serviceLoader = null;
	private CoSECService cosecService = null;
	private Logger logger = null;
	private EGSOContextFactory contextFactory = null;
	private static final int MAXIMAL_RESULTS_DISPLAYED = 100;
	private Random random = null;



	public QueryEngine() {
		logger = Logger.getLogger(this.getClass().getName());
		logger.info("Query Engine Initialization");
		dataPresentationManager = new DataPresentationManager();
		queryValidator = new QueryValidator();
		serviceLoader = new ServiceLoader();
		cosecService = new CoSECService();
		contextFactory = EGSOContextFactory.newInstance(EGSOContext.ROLE_PROVIDER, (String) ProviderConfiguration.getInstance().getProperty("core.rolename"), (String) ProviderConfiguration.getInstance().getProperty("core.roleversion"));
		random = new Random();
	}

	public String query(String context, String query, ResponseQueryProvider notifier) {
		String id = "" + Math.abs(random.nextInt());
		QueryExecutor executor = new QueryExecutor(id, contextFactory.createContext(context), query, notifier, queryValidator, dataPresentationManager);
		executor.start();
		return (id);
	}

	public String fetchFiles(String context, String query, ResponseFileQueryProvider notifier) {
		String id = "" + Math.abs(random.nextInt());
		FileExecutor executor = new FileExecutor(id, contextFactory.createContext(context), query, notifier, queryValidator, dataPresentationManager);
		executor.start();
		return (id);
	}

	public String cosecPlotGoesXrays(String startDate, String endDate) {
		if (cosecService == null) {
			return("CoSEC Service [Plot Goes Xrays] is not available.");
		}
		return(cosecService.cosecPlotGoesXrays(startDate, endDate));
	}

	public String cosecPlotGoesProtons(String startDate, String endDate) {
		if (cosecService == null) {
			return("CoSEC Service [Plot Goes Protons] is not available.");
		}
		return(cosecService.cosecPlotGoesProtons(startDate, endDate));
	}


}

