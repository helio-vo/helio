// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.2_01, build R40)
// Generated source version: 1.1.2

package org.egso.comms.log.wsdl;

public interface Logging_PortType extends java.rmi.Remote {
    public void appendEvents(org.egso.comms.log.types.EventList eventList) throws 
         java.rmi.RemoteException;
    public org.egso.comms.log.types.Log createLog(org.egso.comms.log.types.Log log) throws 
         java.rmi.RemoteException;
}
