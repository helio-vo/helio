package eu.heliovo.queryservice.client;

import java.rmi.RemoteException;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.SOAPBodyElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import eu.heliovo.queryservice.common.util.DomHelper;

public class QueryWebServiceClient {

	public static void main(String args[]) throws RemoteException, ServiceException, Exception
	{		
		
		Document doc = null; 
		DocumentBuilder registryBuilder = null;
		registryBuilder =DocumentBuilderFactory.newInstance().newDocumentBuilder();
		doc = registryBuilder.newDocument();
		//change this to helio:TimeSearch
		//the getSoapBodyNamespaceURI() you can hard code it should be the same namespace you used to register in the SoapServlet.
		Element root = doc.createElementNS("http://helio-vo.eu/xml/QueryService/v0.1", "helio:Query");			
		//This configuration for TIME.		  	
		Element xqueryElemTime = doc.createElementNS("http://helio-vo.eu/xml/QueryService/v0.1","helio:TIME");			
		//xquery should be 'time' so 2009-10-09T00:00:00/2009-10-09T01:00:00
		xqueryElemTime.appendChild(doc.createTextNode("1990-10-20T20:30:56/2009-10-20T20:30:56"));	
		//This configuration for INSTRUMENT.
		Element xqueryElemIntrument = doc.createElementNS("http://helio-vo.eu/xml/QueryService/v0.1","helio:INSTRUMENT");			
		//xquery should be 'Instrument' 
		xqueryElemIntrument.appendChild(doc.createTextNode("HXT"));
		//This configuration for LISTNAME.
		Element xqueryElemListName = doc.createElementNS("http://helio-vo.eu/xml/QueryService/v0.1","helio:LISTNAME");			
		//xquery should be LISTNAME
		xqueryElemListName.appendChild(doc.createTextNode("INSTRUMENTS,OBSERVATORY"));
		//ok put all these into Document.
		root.appendChild(xqueryElemTime); //Time Element.
		root.appendChild(xqueryElemIntrument); // Instrument Element.
		root.appendChild(xqueryElemListName); // List Name Element.
		doc.appendChild(root);
		//Calling the service.
		callService(doc,"Query","Query");
		
	}
	
	protected static Document callService(Document soapBody, String name, String soapActionURI) throws RemoteException , ServiceException, Exception {
	       Vector result = null;      
	       Document resultDoc = DomHelper.newDocument();
	       Document wsDoc = null;
	       NodeList vResources = null;
	       //get a call object
	       Call call = getCall();
	           
	           //When trying to call a Web Service with this client deployed on Microsoft .Net
	           //the SoapActionURI was important to Microsoft as a requirement though it should not be.
	           //I think this might have been fixed some years ago, but leaving it here for now.
	           call.setSOAPActionURI(soapActionURI);	           
	           //create the soap body to be placed in the
	           //outgoing soap message.
	           SOAPBodyElement sbeRequest = new SOAPBodyElement(soapBody.getDocumentElement());	           
	           //go ahead and set the name and namespace on the soap body
	           //not sure if this is that important since I am constructing the soap body xml myself.
	           //I believe it should not be that important but if my memory serves me correct
	           //Axis seemed to throw a NullPointerException if these two methods were not set.
	           sbeRequest.setName(name);
	           sbeRequest.setNamespaceURI("http://helio-vo.eu/xml/QueryService/v0.1");	           
	           //call the web service, on axis-message style it
	           //comes back as a vector of soabodyelements.
	           result = (Vector)call.invoke(new Object[] { sbeRequest });
	           SOAPBodyElement sbe = null;
	           if (result.size() > 0) {
	              sbe = (SOAPBodyElement)result.get(0);
	              wsDoc = sbe.getAsDocument();	  	              
	              System.out.println("THE RESULTDOC FROM SERVICE = " +DomHelper.getStringFromDoc(wsDoc) );
	              if(wsDoc.getDocumentElement() == null) {
	                 
	                  throw new Exception("Error Nothing returned in the Message from the Server, should always be something.");
	              }          
	              resultDoc = wsDoc;
	              return resultDoc;
	           }
	         
	           throw new Exception("ERROR RESULT FROM WEB SERVICE WAS LITERALLY NOTHING IN THE MESSAGE SHOULD NOT HAPPEN.");
	           //return null;
	   }
	   	   
	    /**
	    * Method to establish a Service and a Call to the server side web service.
	    * @return Call object which has the necessary properties set for an Axis message style.
	    * @throws Exception
	    * @todo there's code similar to this in eac of the delegate classes. could it be moved into a common baseclass / helper class.	   
	    */
	   private static Call getCall() throws ServiceException {	       	     
	      Call _call = null;
	      Service service = new Service();
	      _call = (Call)service.createCall();
	      
	      //this is finally your endpoint to do
	      //"http://localhost:8080/HelioQuery/services/HelioService"
	      _call.setTargetEndpointAddress("http://localhost:8080/HelioQueryService/services/HelioService");
	      _call.setSOAPActionURI("");
	      //_call.setOperationStyle(org.apache.axis.enum.Style.MESSAGE);
	      //_call.setOperationUse(org.apache.axis.enum.Use.LITERAL);
	      _call.setEncodingStyle(null);
	      return _call;
	   }

}
