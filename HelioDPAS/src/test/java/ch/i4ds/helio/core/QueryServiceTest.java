package ch.i4ds.helio.core;

import org.apache.xbean.spring.context.FileSystemXmlApplicationContext;
import org.springframework.context.ApplicationContext;

import ch.i4ds.helio.dpas.ResultItem;
import junit.framework.TestCase;

public class QueryServiceTest extends TestCase
{
  private FrontendFacade ff;
  
  public QueryServiceTest()
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
  
  public void testPhoenix2() throws Exception
  {
    assertEquals(117,ff.query1("phoenix2","2007-03-05 03:00:00","2007-03-07 23:45:00",0).length);
    
    for(ResultItem ri:ff.query1("phoenix2","2007-03-05 03:00:00","2007-03-05 23:45:00",0))
    {
      if(ri.urlIntensityFITSGZ.substring(7).contains("//"))
        assertTrue("Intensity FITSGZ contains double slashes ("+ri.urlIntensityFITSGZ+")",false);
      if(ri.urlPhaseFITSGZ.substring(7).contains("//"))
        assertTrue("Phase FITSGZ contains double slashes ("+ri.urlPhaseFITSGZ+")",false);
    }
  }
  
  public void testQueryService() throws Exception
  {
    assertEquals(62,ff.query1("hessi","2007-03-05 03:00:00","2007-03-07 23:45:00",0).length);
    assertEquals(17,ff.query1("hessi-ec","2007-03-05 03:00:00","2007-03-07 23:45:00",0).length);
    assertEquals(31,ff.query1("sm-seit","2006-03-01 03:00:00","2006-03-27 23:45:00",0).length,1);
  }
  
  public void testInitialWorkflow() throws Exception
  {
    if(ff.runInitialWorkflow("2007-03-05 03:00:00","2007-03-06 12:00:00","","").length()<100)
      assertTrue("Initial workflow failed 1",false);
    
    if(ff.runInitialWorkflow("2004-11-14 12:00:00","2004-11-20 12:00:00","","").length()<100)
      assertTrue("Initial workflow failed 2",false);
  }
}
