
package eu.heliovo.sms;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.1-b01-
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "SMSService", targetNamespace = "http://sms.heliovo.eu/", wsdlLocation = "file:/C:/Program%20Files/Apache/Tomcat%206.0/webapps/helio-sms-0.0.1-SNAPSHOT/WEB-INF/wsdl/SMSService.wsdl")
public class SMSService
    extends Service
{

    private final static URL SMSSERVICE_WSDL_LOCATION;
    private final static WebServiceException SMSSERVICE_EXCEPTION;
    private final static QName SMSSERVICE_QNAME = new QName("http://sms.heliovo.eu/", "SMSService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/Program%20Files/Apache/Tomcat%206.0/webapps/helio-sms-0.0.1-SNAPSHOT/WEB-INF/wsdl/SMSService.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        SMSSERVICE_WSDL_LOCATION = url;
        SMSSERVICE_EXCEPTION = e;
    }

    public SMSService() {
        super(__getWsdlLocation(), SMSSERVICE_QNAME);
    }

    public SMSService(WebServiceFeature... features) {
        super(__getWsdlLocation(), SMSSERVICE_QNAME, features);
    }

    public SMSService(URL wsdlLocation) {
        super(wsdlLocation, SMSSERVICE_QNAME);
    }

    public SMSService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SMSSERVICE_QNAME, features);
    }

    public SMSService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SMSService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns SMS
     */
    @WebEndpoint(name = "SMSPort")
    public SMS getSMSPort() {
        return super.getPort(new QName("http://sms.heliovo.eu/", "SMSPort"), SMS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SMS
     */
    @WebEndpoint(name = "SMSPort")
    public SMS getSMSPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://sms.heliovo.eu/", "SMSPort"), SMS.class, features);
    }

    private static URL __getWsdlLocation() {
        if (SMSSERVICE_EXCEPTION!= null) {
            throw SMSSERVICE_EXCEPTION;
        }
        return SMSSERVICE_WSDL_LOCATION;
    }

}
