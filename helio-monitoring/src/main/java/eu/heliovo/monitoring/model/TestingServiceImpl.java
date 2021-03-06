package eu.heliovo.monitoring.model;

import java.net.URL;
import java.util.*;

import eu.heliovo.monitoring.util.StringUtils;

/**
 * Please see {@link TestingService}.
 * 
 * @author Kevin Seidler
 * 
 */
public final class TestingServiceImpl implements TestingService {

	private final String identifier;
	private final String name;
	private final URL url;
	private final List<OperationTest> operationTests;

	protected TestingServiceImpl(String identifier, String name, URL url, List<OperationTest> operationTests) {
		this.identifier = identifier;
		this.name = name;
		this.url = url;
		this.operationTests = Collections.unmodifiableList(operationTests);
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getCanonicalName() {
		return StringUtils.getCanonicalString(this.name);
	}

	@Override
	public URL getUrl() {
		return url;
	}

	@Override
	public List<OperationTest> getOperationTests() {
		return operationTests;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((url == null) ? 0 : url.toString().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TestingServiceImpl other = (TestingServiceImpl) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (url == null) {
			if (other.url != null) {
				return false;
			}
		} else if (!url.toString().equals(other.url.toString())) {
			return false;
		}
		return true;
	}
}