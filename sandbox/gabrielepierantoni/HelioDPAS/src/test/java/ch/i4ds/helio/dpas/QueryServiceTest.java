package ch.i4ds.helio.dpas;

import org.apache.xbean.spring.context.FileSystemXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import ch.i4ds.helio.dpas.*;
import junit.framework.TestCase;

public class QueryServiceTest extends TestCase
{
  private QueryService qs;
  
  public QueryServiceTest()
  {
    ApplicationContext ac=new FileSystemXmlApplicationContext("src/webapp/WEB-INF/applicationContext.xml");
    qs=(QueryService)ac.getBean("QueryService");
  }
  
  public void testQueryService() throws Exception
  {
    /*assertTrue(ff.getInstruments().length>0);
    for(String instrumentName:ff.getInstruments())
    {
      assertNotNull(instrumentName);
      assertTrue(!instrumentName.equals(""));
    }*/
  }
}