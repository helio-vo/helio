package ch.i4ds.helio.dpas;

public class HessiServiceProxy implements ch.i4ds.helio.dpas.HessiService {
  private String _endpoint = null;
  private ch.i4ds.helio.dpas.HessiService hessiService = null;
  
  public HessiServiceProxy() {
    _initHessiServiceProxy();
  }
  
  public HessiServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initHessiServiceProxy();
  }
  
  private void _initHessiServiceProxy() {
    try {
      hessiService = (new ch.i4ds.helio.dpas.HessiServiceServiceLocator()).getHessiServicePort();
      if (hessiService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)hessiService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)hessiService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (hessiService != null)
      ((javax.xml.rpc.Stub)hessiService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ch.i4ds.helio.dpas.HessiService getHessiService() {
    if (hessiService == null)
      _initHessiServiceProxy();
    return hessiService;
  }
  
 
  
  public java.lang.String[] getAhoi() throws java.rmi.RemoteException{
    if (hessiService == null)
      _initHessiServiceProxy();
    return hessiService.getAhoi();
  }
  
  
}