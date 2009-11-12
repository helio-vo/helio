package ch.i4ds.helio.core;

import java.io.IOException;

import org.apache.xbean.spring.context.FileSystemXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import ch.i4ds.helio.ApplicationContextProvider;
import junit.framework.TestCase;

public class FrontendFacadeTest extends TestCase
{
  private FrontendFacade ff;
  
  public FrontendFacadeTest()
  {
    ApplicationContext ac=new FileSystemXmlApplicationContext("src/webapp/WEB-INF/applicationContext.xml");
    ff=(FrontendFacade)ac.getBean("FrontendFacade");
  }
  
  public void testInstrumentList() throws Exception
  {
    assertTrue(ff.getInstruments().length>0);
    for(String instrumentName:ff.getInstruments())
    {
      assertNotNull(instrumentName);
      assertTrue(!instrumentName.equals(""));
    }
  }
  
  public void testQueryService() throws Exception
  {
    assertEquals(62,ff.query1("hessi","2007-03-05 03:00:00","2007-03-07 23:45:00",0).length);
    assertEquals(117,ff.query1("phoenix2","2007-03-05 03:00:00","2007-03-07 23:45:00",0).length);
    assertEquals(17,ff.query1("hessi-ec","2007-03-05 03:00:00","2007-03-07 23:45:00",0).length);
    assertEquals(31,ff.query1("sm-seit","2006-03-01 03:00:00","2006-03-27 23:45:00",0).length,1);
  }
  
  public void testInitialWorkflow() throws Exception
  {
    ff.runInitialWorkflow("2007-03-05 03:00:00","2007-03-06 23:45:00","","");
  }
}
