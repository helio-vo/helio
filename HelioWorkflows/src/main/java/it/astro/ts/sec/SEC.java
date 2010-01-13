/**
 * SECwsdl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.astro.ts.sec;

public interface SEC extends javax.xml.rpc.Service {
    public java.lang.String getSECwsdlPortAddress();

    public it.astro.ts.sec.SECPortType getSECwsdlPort() throws javax.xml.rpc.ServiceException;

    public it.astro.ts.sec.SECPortType getSECwsdlPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
