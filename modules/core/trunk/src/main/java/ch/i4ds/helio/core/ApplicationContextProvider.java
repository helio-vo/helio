package ch.i4ds.helio.core;

import net.sf.taverna.platform.spring.RavenAwareClassPathXmlApplicationContext;

import org.springframework.beans.BeansException;
import org.springframework.context.*;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * Use this class to get the ApplicationContext in which the application is running or
 * in which Taverna workflows will be ran.
 * 
 * @author simon.felix
 */
public class ApplicationContextProvider implements ApplicationContextAware
{
  private static ApplicationContext appContextSpring=null;
  private static ApplicationContext appContextTaverna=null;
  
  public ApplicationContextProvider()
  {
    appContextTaverna=new RavenAwareClassPathXmlApplicationContext("taverna-config.xml");
    ((AbstractApplicationContext)appContextTaverna).registerShutdownHook();
  }
  
  public void setApplicationContext(ApplicationContext _appContext) throws BeansException
  {
    appContextSpring=_appContext;
  }
  
  public static ApplicationContext getSpringApplicationContext()
  {
    return appContextSpring;
  }
  
  public static ApplicationContext getTavernaApplicationContext()
  {
    return appContextTaverna;
  }
}
