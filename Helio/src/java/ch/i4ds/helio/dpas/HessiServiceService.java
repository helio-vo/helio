/**
 * HessiServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ch.i4ds.helio.dpas;

public interface HessiServiceService extends javax.xml.rpc.Service {
    public java.lang.String getHessiServicePortAddress();

    public ch.i4ds.helio.dpas.HessiService getHessiServicePort() throws javax.xml.rpc.ServiceException;

    public ch.i4ds.helio.dpas.HessiService getHessiServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
