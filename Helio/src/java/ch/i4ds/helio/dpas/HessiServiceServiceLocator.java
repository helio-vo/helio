/**
 * HessiServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ch.i4ds.helio.dpas;

public class HessiServiceServiceLocator extends org.apache.axis.client.Service implements ch.i4ds.helio.dpas.HessiServiceService {

    public HessiServiceServiceLocator() {
    }


    public HessiServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public HessiServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for HessiServicePort
    private java.lang.String HessiServicePort_address = "http://localhost:8080/core/services/hessi";

    public java.lang.String getHessiServicePortAddress() {
        return HessiServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String HessiServicePortWSDDServiceName = "HessiServicePort";

    public java.lang.String getHessiServicePortWSDDServiceName() {
        return HessiServicePortWSDDServiceName;
    }

    public void setHessiServicePortWSDDServiceName(java.lang.String name) {
        HessiServicePortWSDDServiceName = name;
    }

    public ch.i4ds.helio.dpas.HessiService getHessiServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(HessiServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getHessiServicePort(endpoint);
    }

    public ch.i4ds.helio.dpas.HessiService getHessiServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ch.i4ds.helio.dpas.HessiServicePortBindingStub _stub = new ch.i4ds.helio.dpas.HessiServicePortBindingStub(portAddress, this);
            _stub.setPortName(getHessiServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setHessiServicePortEndpointAddress(java.lang.String address) {
        HessiServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ch.i4ds.helio.dpas.HessiService.class.isAssignableFrom(serviceEndpointInterface)) {
                ch.i4ds.helio.dpas.HessiServicePortBindingStub _stub = new ch.i4ds.helio.dpas.HessiServicePortBindingStub(new java.net.URL(HessiServicePort_address), this);
                _stub.setPortName(getHessiServicePortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("HessiServicePort".equals(inputPortName)) {
            return getHessiServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://dpas.helio.i4ds.ch/", "HessiServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://dpas.helio.i4ds.ch/", "HessiServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("HessiServicePort".equals(portName)) {
            setHessiServicePortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
