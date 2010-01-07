package com.org.helio.server.query;

import javax.xml.stream.*;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilderFactory;

import org.astrogrid.util.DomHelper;

import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.StringWriter;
import java.util.Hashtable;
import org.codehaus.xfire.util.STAXUtils;
import org.codehaus.xfire.MessageContext;

import com.org.helio.common.dao.CommonDaoFactory;
import com.org.helio.common.dao.exception.DetailsNotFoundException;
import com.org.helio.common.dao.interfaces.CommonDao;
import com.org.helio.common.transfer.criteriaTO.CommonCriteriaTO;

/**
 * Class: SoapDispatcher
 * Description: The dispatcher handles all soap requests and responses for a Query.  Called via the
 * SoapServlet. SoapRequests (Bodies) are placed into a DOM and by analyzing the uri 
 * determine the correct query service for the correct contract.  Responses are Stream based (NOT DOM) into an 
 * XMLStreamReader with the help of PipedStreams.
 *
 */
public class SoapDispatcherString {

  Hashtable interfaceMappings = null;
  
  /**
   * Method: Constructor
   * Description: Setup any initiations, mainly hashtable of uri to query 
   * interface versions.
   */
  public SoapDispatcherString() {
	  interfaceMappings = new Hashtable();
	  //Small hashtable for determing the query interface.	
	  }

  /**
   * Method: invoke
   * Description: Called by SoapServlet (XFire) for all soap requests and 
   * responses.
   * @param context - MessageContext that is ued to extract the soap request.
   * @return XMLStreamReader - response XMLStreamReader that contains the 
   * soap response populated by InputStream (PipedInputStream)
   */
  public XMLStreamReader invoke(MessageContext context) {
	 try {
		 //get the soap request.
	     XMLStreamReader reader = context.getInMessage().getXMLStreamReader();

	     //form a DOM of the request.
		 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	     DocumentBuilder builder = dbf.newDocumentBuilder();
	     Document inputDoc = STAXUtils.read(builder,reader,true);
	     //all the soap requests in the body will have a namespaceuri that      
	     String inputURI = inputDoc.getDocumentElement().getNamespaceURI();   
	     XMLStreamReader responseReader = null;
	     //if(query != null) {	    	 	    	 
	    	 String interfaceName = inputDoc.getDocumentElement().getLocalName().intern();
	    	 //since this service will be used a lot, supposedly .intern() can be quicker
   	    	 //each method should return a XMLStreamReader that is streamed back to the client.
	    	 if(interfaceName == "Query".intern()) {
	    		 final CommonCriteriaTO comCriteriaTO=new CommonCriteriaTO(); 
	    		 PipedReader pr = new PipedReader();
	    		 PipedWriter pw = new PipedWriter(pr);
	    		 StringWriter sw = new StringWriter();
	    		 //pr.connect(pw);
	    		// pw.connect(pr);

	    		 System.out.println( DomHelper.DocumentToString(inputDoc));
	    		 comCriteriaTO.setPrintWriter(sw);
	    		 String time = inputDoc.getDocumentElement().getElementsByTagNameNS("*","TIME").item(0).getFirstChild().getNodeValue();
	    		 String[] dateTime= time.replace("'T'", " ").split("/");			
				 System.out.println(" startDateTime : "+dateTime[0]+" startEndTime : "+dateTime[1]);			
				 comCriteriaTO.setStartDateTime(dateTime[0]);
				 comCriteriaTO.setEndDateTime(dateTime[1]);											
				 final CommonDao commonNameDao= CommonDaoFactory.getInstance().getCommonDAO();
				
				commonNameDao.generateVOTableDetails(comCriteriaTO);
						
				 System.out.println("+++++++++++++++++++++ Done VOTABLE ++++++++++++++++++++++++++");
				//Thread.yield();
				
				synchronized (this) {
					//responseReader = STAXUtils.createXMLStreamReader(pr);
					//responseReader = STAXUtils.createXMLStreamReader(new java.io.StringReader("<heliot:hello xmlns:heliot=\"http://helio.org/test\">This is a test</heliot:hello>"));
					System.out.println(" String Buffer "+sw.getBuffer().toString());
					responseReader = STAXUtils.createXMLStreamReader(new java.io.StringReader(sw.getBuffer().toString()));
				}		
				 				 
	    	 }
	     //}
	 	 System.out.println("returning responsereader");
	 	 return responseReader;
	 }catch(Exception e) {
		 e.printStackTrace();
	 }
	 return null;
  }
}