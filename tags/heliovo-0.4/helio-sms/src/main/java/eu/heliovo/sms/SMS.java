
package eu.heliovo.sms;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "SMS", targetNamespace = "http://sms.heliovo.eu/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SMS {


    /**
     * 
     * @param phenomenon
     * @return
     *     returns java.util.List<java.lang.String>
     * @throws SmsFault_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getEquivalents", targetNamespace = "http://sms.heliovo.eu/", className = "eu.heliovo.sms.GetEquivalents")
    @ResponseWrapper(localName = "getEquivalentsResponse", targetNamespace = "http://sms.heliovo.eu/", className = "eu.heliovo.sms.GetEquivalentsResponse")
    public List<String> getEquivalents(
        @WebParam(name = "phenomenon", targetNamespace = "")
        String phenomenon)
        throws SmsFault_Exception
    ;

    /**
     * 
     * @param phenomenon
     * @return
     *     returns java.util.List<java.lang.String>
     * @throws SmsFault_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getRelated", targetNamespace = "http://sms.heliovo.eu/", className = "eu.heliovo.sms.GetRelated")
    @ResponseWrapper(localName = "getRelatedResponse", targetNamespace = "http://sms.heliovo.eu/", className = "eu.heliovo.sms.GetRelatedResponse")
    public List<String> getRelated(
        @WebParam(name = "phenomenon", targetNamespace = "")
        String phenomenon)
        throws SmsFault_Exception
    ;

    /**
     * 
     * @param phenomenon
     * @return
     *     returns java.util.List<java.lang.String>
     * @throws SmsFault_Exception
     */
    @WebMethod
    @WebResult(name = "hecListNames", targetNamespace = "")
    @RequestWrapper(localName = "getHECListNames", targetNamespace = "http://sms.heliovo.eu/", className = "eu.heliovo.sms.GetHECListNames")
    @ResponseWrapper(localName = "getHECListNamesResponse", targetNamespace = "http://sms.heliovo.eu/", className = "eu.heliovo.sms.GetHECListNamesResponse")
    public List<String> getHECListNames(
        @WebParam(name = "phenomenon", targetNamespace = "")
        String phenomenon)
        throws SmsFault_Exception
    ;

    /**
     * 
     * @return
     *     returns java.util.List<java.lang.String>
     * @throws SmsFault_Exception
     */
    @WebMethod
    @WebResult(name = "knowPhenomena", targetNamespace = "")
    @RequestWrapper(localName = "getKnownPhenomena", targetNamespace = "http://sms.heliovo.eu/", className = "eu.heliovo.sms.GetKnownPhenomena")
    @ResponseWrapper(localName = "getKnownPhenomenaResponse", targetNamespace = "http://sms.heliovo.eu/", className = "eu.heliovo.sms.GetKnownPhenomenaResponse")
    public List<String> getKnownPhenomena()
        throws SmsFault_Exception
    ;

}
