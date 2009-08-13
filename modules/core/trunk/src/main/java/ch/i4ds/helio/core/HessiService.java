package ch.i4ds.helio.core;

import java.io.IOException;
import java.net.*;
import java.text.DateFormat;
import java.util.*;

import javax.jws.*;

@WebService
public class HessiService
{
  @WebMethod
  public String[] getListOfFiles(String _dateFrom,String _dateTo) throws IOException
  {
    if(_dateFrom.length()!="2001.01.01 00:00:00".length())
      throw new RuntimeException("Invalid time range (dateFrom)");
    if(_dateTo.length()!="2001.01.01 00:00:00".length())
      throw new RuntimeException("Invalid time range (dateTo)");
    
    Calendar from=Calendar.getInstance();
    Calendar to=Calendar.getInstance();
    from.set(
          Integer.parseInt(_dateFrom.substring(0,4)),
          Integer.parseInt(_dateFrom.substring(5,7)),
          Integer.parseInt(_dateFrom.substring(8,10)),
          Integer.parseInt(_dateFrom.substring(11,13)),
          Integer.parseInt(_dateFrom.substring(14,16)),
          Integer.parseInt(_dateFrom.substring(17,19))
        );
    to.set(
        Integer.parseInt(_dateFrom.substring(0,4)),
        Integer.parseInt(_dateFrom.substring(5,7)),
        Integer.parseInt(_dateFrom.substring(8,10)),
        Integer.parseInt(_dateFrom.substring(11,13)),
        Integer.parseInt(_dateFrom.substring(14,16)),
        Integer.parseInt(_dateFrom.substring(17,19))
      );
    
    if(!from.before(to))
      throw new RuntimeException("Invalid time range(from > to)");
    
    LinkedList<String> results=new LinkedList<String>();
    for(Calendar aDay=from;aDay.before(to);aDay.roll(Calendar.DATE,1))
    {
      URLConnection c=new URL("http://www.hedc.ethz.ch/data/2008/06/08/").openConnection();
      c.setAllowUserInteraction(false);
      c.connect();
      String s=(String)c.getContent(new Class[]{String.class});
      System.out.println(s);
    }
    
    return results.toArray(new String[0]);
  }
}
