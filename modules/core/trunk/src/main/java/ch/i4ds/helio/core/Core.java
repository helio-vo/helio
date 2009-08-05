package ch.i4ds.helio.core;
import java.io.*;
import java.util.List;
import java.util.concurrent.*;

import javax.jws.WebService;

import net.sf.taverna.platform.spring.RavenAwareClassPathXmlApplicationContext;
import net.sf.taverna.t2.facade.*;
import net.sf.taverna.t2.facade.impl.WorkflowInstanceFacadeImpl;
import net.sf.taverna.t2.invocation.*;
import net.sf.taverna.t2.provenance.reporter.ProvenanceReporter;
import net.sf.taverna.t2.reference.ReferenceService;
import net.sf.taverna.t2.workflowmodel.*;
import net.sf.taverna.t2.workflowmodel.serialization.xml.*;

import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;


@WebService(serviceName="GetGurk")
public class Core
{
  
  public String execWF(String _input) throws Exception
  {
    ApplicationContext context = new RavenAwareClassPathXmlApplicationContext("taverna-config.xml");
    ((AbstractApplicationContext)context).registerShutdownHook();
    
    final ReferenceService referenceService = (ReferenceService) context.getBean("t2reference.service.referenceService");
    
    XMLDeserializer deserializer = new XMLDeserializerImpl();
    InputStream inStream = new FileInputStream("../applications/core/WEB-INF/classes/workflows/example/tv2-test.t2flow");
    SAXBuilder builder = new SAXBuilder();
    Element el= builder.build(inStream).detachRootElement();
    final Dataflow df=deserializer.deserializeDataflow(el);
    
    final InvocationContext ic=new InvocationContext()
    {
      public <T> List<? extends T> getEntities(Class<T> entityType)
      {
        return null;
      }
      
      public ReferenceService getReferenceService()
      {
        return referenceService;
      }
      
      public ProvenanceReporter getProvenanceReporter()
      {
        return null;
      }
    };
    
    WorkflowInstanceFacade wif=new WorkflowInstanceFacadeImpl(df,ic,"a");
    
    wif.fire();
    
    final LinkedBlockingQueue<String> result=new LinkedBlockingQueue<String>();
    
    wif.addResultListener(new ResultListener()
    {
      public void resultTokenProduced(WorkflowDataToken token,String portName)
      {
        Object s=referenceService.renderIdentifier(token.getData(),Object.class,ic);
        try
        {
          result.put((String)s);
        }
        catch(InterruptedException _ie)
        {
          
        }
      }
    });
    
    return result.take();
  }
}
