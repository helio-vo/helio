
package eu.heliovo.workflow.clients.dpas;

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
 * JAX-WS RI 2.2-hudson-752-
 * Generated source version: 2.1
 * 
 */
@WebService(name = "QueryService", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface QueryService {


    /**
     * 
     * @param stopTimes
     * @param startTimes
     * @param instruments
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "queryReturn", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
    @RequestWrapper(localName = "query", targetNamespace = "http://controller.dpas.helio.i4ds.ie", className = "eu.heliovo.workflow.clients.dpas.Query")
    @ResponseWrapper(localName = "queryResponse", targetNamespace = "http://controller.dpas.helio.i4ds.ie", className = "eu.heliovo.workflow.clients.dpas.QueryResponse")
    public String query(
        @WebParam(name = "instruments", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
        List<String> instruments,
        @WebParam(name = "startTimes", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
        List<String> startTimes,
        @WebParam(name = "stopTimes", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
        List<String> stopTimes);

    /**
     * 
     * @param startTime
     * @param instrument
     * @param stopTime
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "simpleDummyQueryReturn", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
    @RequestWrapper(localName = "simpleDummyQuery", targetNamespace = "http://controller.dpas.helio.i4ds.ie", className = "eu.heliovo.workflow.clients.dpas.SimpleDummyQuery")
    @ResponseWrapper(localName = "simpleDummyQueryResponse", targetNamespace = "http://controller.dpas.helio.i4ds.ie", className = "eu.heliovo.workflow.clients.dpas.SimpleDummyQueryResponse")
    public String simpleDummyQuery(
        @WebParam(name = "instrument", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
        String instrument,
        @WebParam(name = "startTime", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
        String startTime,
        @WebParam(name = "stopTime", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
        String stopTime);

    /**
     * 
     * @param startTime
     * @param instrument
     * @param stopTime
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "simpleQueryReturn", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
    @RequestWrapper(localName = "simpleQuery", targetNamespace = "http://controller.dpas.helio.i4ds.ie", className = "eu.heliovo.workflow.clients.dpas.SimpleQuery")
    @ResponseWrapper(localName = "simpleQueryResponse", targetNamespace = "http://controller.dpas.helio.i4ds.ie", className = "eu.heliovo.workflow.clients.dpas.SimpleQueryResponse")
    public String simpleQuery(
        @WebParam(name = "instrument", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
        String instrument,
        @WebParam(name = "startTime", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
        String startTime,
        @WebParam(name = "stopTime", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
        String stopTime);

    /**
     * 
     * @param stopTimes
     * @param startTimes
     * @param instruments
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "dummyQueryReturn", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
    @RequestWrapper(localName = "dummyQuery", targetNamespace = "http://controller.dpas.helio.i4ds.ie", className = "eu.heliovo.workflow.clients.dpas.DummyQuery")
    @ResponseWrapper(localName = "dummyQueryResponse", targetNamespace = "http://controller.dpas.helio.i4ds.ie", className = "eu.heliovo.workflow.clients.dpas.DummyQueryResponse")
    public String dummyQuery(
        @WebParam(name = "instruments", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
        List<String> instruments,
        @WebParam(name = "startTimes", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
        List<String> startTimes,
        @WebParam(name = "stopTimes", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
        List<String> stopTimes);

    /**
     * 
     * @param stopTimes
     * @param partialSorting
     * @param startTimes
     * @param instruments
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "dummySortedQueryReturn", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
    @RequestWrapper(localName = "dummySortedQuery", targetNamespace = "http://controller.dpas.helio.i4ds.ie", className = "eu.heliovo.workflow.clients.dpas.DummySortedQuery")
    @ResponseWrapper(localName = "dummySortedQueryResponse", targetNamespace = "http://controller.dpas.helio.i4ds.ie", className = "eu.heliovo.workflow.clients.dpas.DummySortedQueryResponse")
    public String dummySortedQuery(
        @WebParam(name = "instruments", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
        List<String> instruments,
        @WebParam(name = "startTimes", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
        List<String> startTimes,
        @WebParam(name = "stopTimes", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
        List<String> stopTimes,
        @WebParam(name = "partialSorting", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
        boolean partialSorting);

    /**
     * 
     * @param stopTimes
     * @param partialSorting
     * @param startTimes
     * @param instruments
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "sortedQueryReturn", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
    @RequestWrapper(localName = "sortedQuery", targetNamespace = "http://controller.dpas.helio.i4ds.ie", className = "eu.heliovo.workflow.clients.dpas.SortedQuery")
    @ResponseWrapper(localName = "sortedQueryResponse", targetNamespace = "http://controller.dpas.helio.i4ds.ie", className = "eu.heliovo.workflow.clients.dpas.SortedQueryResponse")
    public String sortedQuery(
        @WebParam(name = "instruments", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
        List<String> instruments,
        @WebParam(name = "startTimes", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
        List<String> startTimes,
        @WebParam(name = "stopTimes", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
        List<String> stopTimes,
        @WebParam(name = "partialSorting", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
        boolean partialSorting);

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "getInstrumentsReturn", targetNamespace = "http://controller.dpas.helio.i4ds.ie")
    @RequestWrapper(localName = "getInstruments", targetNamespace = "http://controller.dpas.helio.i4ds.ie", className = "eu.heliovo.workflow.clients.dpas.GetInstruments")
    @ResponseWrapper(localName = "getInstrumentsResponse", targetNamespace = "http://controller.dpas.helio.i4ds.ie", className = "eu.heliovo.workflow.clients.dpas.GetInstrumentsResponse")
    public String getInstruments();

}
