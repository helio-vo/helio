package ch.i4ds.helio.workflows;

import org.apache.xbean.spring.context.FileSystemXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import ch.i4ds.helio.workflows.*;
import junit.framework.TestCase;

public class WorkflowsTest extends TestCase
{
  private Workflows wf;
  
  public WorkflowsTest()
  {
    ApplicationContext ac=new FileSystemXmlApplicationContext("src/webapp/WEB-INF/applicationContext.xml");
    wf=(Workflows)ac.getBean("Workflows");
  }
  
  public void testInstrumentList() throws Exception
  {
    System.out.println(
    wf.runInitialWorkflow("2005-01-01 00:00:00","2005-03-01 00:00:00","","","fhnw"));
    /*assertTrue(ff.getInstruments().length>0);
    for(String instrumentName:ff.getInstruments())
    {
      assertNotNull(instrumentName);
      assertTrue(!instrumentName.equals(""));
    }*/
  }
}
