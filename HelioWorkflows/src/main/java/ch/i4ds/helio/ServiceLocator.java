package ch.i4ds.helio;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import javax.jws.*;
import javax.xml.bind.*;
import com.sun.xml.ws.developer.Stateful;
import ws.clients.FrontendServices.*;
import ws.clients.HEC.HECService;

/**
 * This class is used for service discovery
 * 
 * @author Simon Felix (de@iru.ch)
 */
public class ServiceLocator
{
  private Map<String,Map<String,String>> serviceSets=new LinkedHashMap<String,Map<String,String>>();
  
  public void setServiceSets(Map<String,Map<String,String>> _serviceSets)
  {
    serviceSets=_serviceSets;
  }
  
  public String getServiceLocation(String _service_set,String _service) throws java.lang.Exception
  {
    return serviceSets.get(_service_set).get(_service);
  }
}