package ch.i4ds.helio.core;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;

import javax.jws.*;
import net.sf.taverna.t2.facade.*;
import net.sf.taverna.t2.facade.impl.WorkflowInstanceFacadeImpl;
import net.sf.taverna.t2.invocation.*;
import net.sf.taverna.t2.provenance.reporter.ProvenanceReporter;
import net.sf.taverna.t2.reference.*;
import net.sf.taverna.t2.workflowmodel.*;
import net.sf.taverna.t2.workflowmodel.serialization.DeserializationException;
import net.sf.taverna.t2.workflowmodel.serialization.xml.*;

import org.jdom.*;
import org.jdom.input.SAXBuilder;


@WebService
public class TavernaExecutor
{
  private Map<String,Object> executeWorkflow(InputStream _workflowDefinition,Map<String,Object> _input) throws InvalidDataflowException,InterruptedException,IOException,JDOMException,DeserializationException
  {
    final ReferenceService referenceService=(ReferenceService)ApplicationContextProvider.getTavernaApplicationContext().getBean("t2reference.service.referenceService");
    
    //deserialize the xml workflow-definition
    Element el=new SAXBuilder().build(_workflowDefinition).detachRootElement();
    
    final Dataflow df;
    try
    {
      //build a dataflow out of the xml definition
      df=new XMLDeserializerImpl().deserializeDataflow(el);
    }
    catch(EditException _ee)
    {
      //this will never happen as we don't edit the workflow programmatically
      throw new RuntimeException(_ee);
    }
    
    //create a simple invocation context
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
    
    //create the "taverna engine"
    WorkflowInstanceFacade wif=new WorkflowInstanceFacadeImpl(df,ic,"");
    
    //if any input parameters were provided, add them to the workflow
    if(_input!=null)
      for(Entry<String,Object> e:_input.entrySet())
      {
        T2Reference dataReference=ic.getReferenceService().register(e.getValue(),1,true,ic);
        WorkflowDataToken dataToken=new WorkflowDataToken("",new int[]{},dataReference,ic);
        try
        {
          wif.pushData(dataToken,e.getKey());
        }
        catch(TokenOrderException _toe)
        {
          throw new RuntimeException(_toe);
        }
      }
    
    //start workflow
    wif.fire();
    
    //this thread needs to wait until all [n] results are collected
    final Semaphore workflowIsDone=new Semaphore(1-wif.getDataflow().getOutputPorts().size());
    
    //create a map to collect the results
    final Map<String,Object> results=Collections.synchronizedMap(new LinkedHashMap<String,Object>());
    final LinkedList<Throwable> exceptions=new LinkedList<Throwable>();
    
    //the result listener will be called once for every output. we collect
    //the rendered data in the results-map 
    wif.addResultListener(new ResultListener()
    {
      public void resultTokenProduced(WorkflowDataToken token,String portName)
      {
        Object s=referenceService.renderIdentifier(token.getData(),Object.class,ic);
        results.put(portName,s);
        workflowIsDone.release();
      }
    });
    
    //the failure listener will be called when the workflow fails
    wif.addFailureListener(new FailureListener()
    {
      public void workflowFailed(String message,Throwable t)
      {
        exceptions.add(t);
        
        //this'll work, even if it's not thread-safe. in the worst case
        //we release one permit too much
        workflowIsDone.release(1-workflowIsDone.availablePermits());
      }
    });
    
    //wait until workflow is done or an exception has occured
    workflowIsDone.acquire();
    
    //did an exception occur? if yes --> re-throw it...
    if(!exceptions.isEmpty())
      throw new RuntimeException(exceptions.get(0));
    
    //...otherwise return collected results
    return results;
  }
  
  @SuppressWarnings("unchecked")
  @WebMethod
  public String execWF(String _input) throws Exception
  {
    InputStream inStream = new FileInputStream("../applications/core/WEB-INF/classes/workflows/example/dna.t2flow");
    //return (String)executeWorkflow(inStream,null).get("prophetOutput");
    
    Map<String,Object> results=executeWorkflow(inStream,null);
    
    //png
    byte[] img=((ArrayList<byte[]>)results.get("outputPlot")).get(0);
    
    return (String)results.get("prophetOutput");
  }
}
