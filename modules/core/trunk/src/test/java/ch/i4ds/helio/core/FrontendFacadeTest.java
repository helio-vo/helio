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
    assertEquals(ff.query1("hessi","2007-03-05 03:00:00","2007-03-07 23:45:00",0).length,62);
    //assertEquals(ff.query1("phoenix2","2007-03-05 03:00:00","2007-03-07 23:45:00",0).length,1);
    /*assertEquals(ff.query1("hessi-ec ","2007-03-05 03:00:00","2007-03-07 23:45:00",0).length,1);
    assertEquals(ff.query1("sm-seit","2007-03-05 03:00:00","2007-03-07 23:45:00",0).length,1);
    
    assertEquals(ff.query1("hessi","2007-03-05 03:00:00","2007-03-07 23:45:00",9).length,9);
    assertEquals(ff.query1("phoenix2","2007-03-05 03:00:00","2007-03-07 23:45:00",9).length,9);
    assertEquals(ff.query1("hessi-ec ","2007-03-05 03:00:00","2007-03-07 23:45:00",9).length,9);
    assertEquals(ff.query1("sm-seit","2007-03-05 03:00:00","2007-03-07 23:45:00",9).length,9);*/
  }
  
  public void testInitialWorkflow() throws Exception
  {
    //ff.runInitialWorkflow("2007-03-05 03:00:00","2007-03-07 23:45:00","","");
  }
}
