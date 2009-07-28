import java.io.*;
import java.util.List;

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


public class Core
{
  public static void main(String[] _args) throws Exception
  {
    ApplicationContext context = new RavenAwareClassPathXmlApplicationContext("spring-config.xml");
    ((AbstractApplicationContext)context).registerShutdownHook();
    
    final ReferenceService referenceService = (ReferenceService) context.getBean("t2reference.service.referenceService");

    
    //TavernaBaseProfile profile=new TavernaBaseProfile(context);
    
    XMLDeserializer deserializer = new XMLDeserializerImpl();
    InputStream inStream = new FileInputStream("workflows/example/tv2-test.t2flow");
    SAXBuilder builder = new SAXBuilder();
    Element el= builder.build(inStream).detachRootElement();
    final Dataflow df=deserializer.deserializeDataflow(el);
    
    for(InputPort ip:df.getInputPorts())
      System.out.println("In: "+ip.getName());
    
    
    for(OutputPort op:df.getOutputPorts())
      System.out.println("Out: "+op.getName());
    
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
    
    wif.addResultListener(new ResultListener()
    {
      public void resultTokenProduced(WorkflowDataToken token,String portName)
      {
        Object s=referenceService.renderIdentifier(token.getData(),Object.class,ic);
        System.out.println(portName+"="+s);
      }
    });
  }
}
