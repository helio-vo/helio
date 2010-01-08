package com.org.helio.server.servlets;

import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.service.binding.MessageBindingProvider;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.transport.http.XFireServlet;
import com.org.helio.server.query.SoapDispatcher;
import javax.servlet.ServletException;

public class SoapServlet extends XFireServlet
{
	
    /*
	 * 
     */
private static final long serialVersionUID = 1L;

public void init() throws ServletException
  {
    super.init();
   
    //make an xfire factory class and bind it to a message style for us to do the Soap ourselves
    ObjectServiceFactory factory = new ObjectServiceFactory(new MessageBindingProvider());
    factory.setStyle("message");
    
    //now create the linkup with the SoapDispather to the namespace and endpoint being "HelioService"
    Service queryServicev1_0 = factory.create(SoapDispatcher.class,"HelioService","http://helio.org/xml/QueryService/v1.0",null);
    
    //we register the service with the controller that handles soap requests
    getController().getServiceRegistry().register(queryServicev1_0);
     
  }
}