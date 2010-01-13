package it.astro.ts.sec;

public class SECPortTypeProxy implements it.astro.ts.sec.SECPortType {
  private String _endpoint = null;
  private it.astro.ts.sec.SECPortType sECwsdlPortType = null;
  
  public SECPortTypeProxy() {
    _initSECwsdlPortTypeProxy();
  }
  
  public SECPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initSECwsdlPortTypeProxy();
  }
  
  private void _initSECwsdlPortTypeProxy() {
    try {
      sECwsdlPortType = (new it.astro.ts.sec.SECLocator()).getSECwsdlPort();
      if (sECwsdlPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)sECwsdlPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)sECwsdlPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (sECwsdlPortType != null)
      ((javax.xml.rpc.Stub)sECwsdlPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.astro.ts.sec.SECPortType getSECwsdlPortType() {
    if (sECwsdlPortType == null)
      _initSECwsdlPortTypeProxy();
    return sECwsdlPortType;
  }
  
  public java.lang.String sql(java.lang.String name) throws java.rmi.RemoteException{
    if (sECwsdlPortType == null)
      _initSECwsdlPortTypeProxy();
    return sECwsdlPortType.sql(name);
  }
  
  public java.lang.String describeCatalogue(java.lang.String name) throws java.rmi.RemoteException{
    if (sECwsdlPortType == null)
      _initSECwsdlPortTypeProxy();
    return sECwsdlPortType.describeCatalogue(name);
  }
  
  public java.lang.String describeTable(java.lang.String name) throws java.rmi.RemoteException{
    if (sECwsdlPortType == null)
      _initSECwsdlPortTypeProxy();
    return sECwsdlPortType.describeTable(name);
  }
  
  public java.lang.String countRows(java.lang.String name) throws java.rmi.RemoteException{
    if (sECwsdlPortType == null)
      _initSECwsdlPortTypeProxy();
    return sECwsdlPortType.countRows(name);
  }
  
  
}