
package eu.heliovo.workflow.clients.hec;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2-hudson-752-
 * Generated source version: 2.1
 * 
 */
@WebService(name = "HEC", targetNamespace = "http://service.hec/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface HEC {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "sql", targetNamespace = "http://service.hec/", className = "eu.heliovo.workflow.clients.hec.Sql")
    @ResponseWrapper(localName = "sqlResponse", targetNamespace = "http://service.hec/", className = "eu.heliovo.workflow.clients.hec.SqlResponse")
    public String sql(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

}
