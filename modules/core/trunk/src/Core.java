import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Core
{
  public static void main(String[] _args)
  {
    ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring-config.xml"});
    ((AbstractApplicationContext)context).registerShutdownHook();
  }
}
