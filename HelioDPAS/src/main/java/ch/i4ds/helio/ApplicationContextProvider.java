package ch.i4ds.helio;

import org.springframework.beans.BeansException;
import org.springframework.context.*;


/**
 * Use this class to get the ApplicationContext in which the application is running or
 * in which Taverna workflows will be run.
 * 
 * @author Simon Felix
 */
public class ApplicationContextProvider implements ApplicationContextAware
{
  private static ApplicationContext appContextSpring=null;
  
  public void setApplicationContext(ApplicationContext _appContext) throws BeansException
  {
    appContextSpring=_appContext;
  }
  
  public static ApplicationContext getSpringApplicationContext()
  {
    return appContextSpring;
  }
}
