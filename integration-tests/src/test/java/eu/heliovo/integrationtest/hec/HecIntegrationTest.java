package eu.heliovo.integrationtest.hec;

import java.util.Collection;

import org.junit.runner.RunWith;

import eu.heliovo.integrationtest.AbstractIntegrationTest;
import eu.heliovo.integrationtest.util.DataReaderUtil;
import eu.heliovo.integrationtest.util.LabelledParameterized;
import eu.heliovo.integrationtest.util.LabelledParameterized.Parameters;
import eu.heliovo.registryclient.HelioServiceName;

@RunWith(LabelledParameterized.class)
public class HecIntegrationTest extends AbstractIntegrationTest {
    private static final HelioServiceName SERVICE_NAME = HelioServiceName.HEC;
    private static final String TEST_DATA_FILE = "/hec/hec_testdata.txt";

    /**
     * Create the integration test
     * @param startTime the start time list
     * @param endTime the end time list
     * @param from the list of from catalogs
     * @param where the where clause
     */
    public HecIntegrationTest(String[] startTime, String[] endTime, String[] from, String where, String expectedResultFile) {
        super(SERVICE_NAME, startTime, endTime, from, where, expectedResultFile);
    }
    
    @Parameters public static Collection<Object[]> testData() {
        DataReaderUtil reader = new DataReaderUtil(DataReaderUtil.class.getResourceAsStream(TEST_DATA_FILE));
        return reader.getTestData();
    }    
}
