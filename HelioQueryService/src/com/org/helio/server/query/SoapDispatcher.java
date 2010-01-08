package com.org.helio.server.query;

import javax.xml.stream.*;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.StringWriter;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.codehaus.xfire.util.STAXUtils; 
import org.codehaus.xfire.MessageContext;
import com.org.helio.common.transfer.criteriaTO.CommonCriteriaTO;
import com.org.helio.server.util.QueryThreadAnalizer;

/**
 * Class: SoapDispatcher
 * Description: The dispatcher handles all soap requests and responses for a Query.  Called via the
 * SoapServlet. SoapRequests (Bodies) are placed into a DOM and by analyzing the uri 
 * determine the correct query service for the correct contract.  Responses are Stream based (NOT DOM) into an 
 * XMLStreamReader with the help of PipedStreams.
 *
 */
public class SoapDispatcher {

  Hashtable interfaceMappings = null;
  protected final  Logger logger = Logger.getLogger(this.getClass());
  
  /**
   * Method: Constructor
   * Description: Setup any initiations, mainly hashtable of uri to query 
   * interface versions.
   */
  public SoapDispatcher() {
	 
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
		 logger.info("  : Starting Webservice Call :  ");
		 //get the soap request.
	     XMLStreamReader reader = context.getInMessage().getXMLStreamReader();

	     //form a DOM of the request.
		 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	     DocumentBuilder builder = dbf.newDocumentBuilder();
	     Document inputDoc = STAXUtils.read(builder,reader,true);
	     //all the soap requests in the body will have a namespaceuri that      
	     String inputURI = inputDoc.getDocumentElement().getNamespaceURI();
	    
	     XMLStreamReader responseReader = null;	        	 	    	
	     String interfaceName = inputDoc.getDocumentElement().getLocalName().intern();
	    	 //since this service will be used a lot, supposedly .intern() can be quicker
   	    	 //each method should return a XMLStreamReader that is streamed back to the client.
	    	 if(interfaceName == "Query".intern()) {
	    		 CommonCriteriaTO comCriteriaTO=new CommonCriteriaTO(); 
	    		 PipedReader pr = new PipedReader();
	    		 PipedWriter pw = new PipedWriter(pr);	    		   		   		  		   	 
	    		 comCriteriaTO.setPrintWriter(pw);
	    		 //Indicator to define VOTABLE for Webservice request
	    		 comCriteriaTO.setStatus("WebService");
	    		 String time = inputDoc.getDocumentElement().getElementsByTagNameNS("*","TIME").item(0).getFirstChild().getNodeValue();
	    		 String[] dateTime= time.replace("T", " ").split("/");			
				 logger.info(" : startDateTime : "+dateTime[0]+" : startEndTime : "+dateTime[1]);			
				 comCriteriaTO.setStartDateTime(dateTime[0]);
				 comCriteriaTO.setEndDateTime(dateTime[1]);															 
				 new QueryThreadAnalizer(comCriteriaTO).start();				
				 logger.info(" : Done VOTABLE : ");												
				 responseReader = STAXUtils.createXMLStreamReader(pr);									
	    	 }	  
	 	 logger.info(" : returning response reader soap output : ");
	 	 return responseReader;
	 }catch(Exception e) {
		 logger.fatal("   : Exception in SoapDispatcher:invoke : ", e);
	 }
	 return null;
  }
}