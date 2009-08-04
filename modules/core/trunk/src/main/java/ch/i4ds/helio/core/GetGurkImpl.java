package ch.i4ds.helio.core;
import javax.jws.*;


@WebService(serviceName="GetGurk")
public class GetGurkImpl
{
  public void init()
  {
  }
  
  @WebMethod
  public String getGurk()
  {
    return "gurk";
  }
}
