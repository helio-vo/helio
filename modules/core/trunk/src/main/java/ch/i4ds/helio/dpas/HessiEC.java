package ch.i4ds.helio.dpas;

import java.io.*;
import java.net.*;
import java.util.*;

public class HessiEC implements DataProvider
{
  private long lastUpdate;
  private long updatePeriod=1000l*60*60*24;
  private LinkedList<ResultItem> events=new LinkedList<ResultItem>();
  
  public void setUpdatePeriod(long _milliseconds)
  {
    updatePeriod=_milliseconds;
  }
  
  private void parseFlareList() throws Exception
  {
    final URLConnection c=new URL("http://www.hedc.ethz.ch/data/dbase/hessi_flare_list.txt").openConnection();
    BufferedReader in=new BufferedReader(new InputStreamReader(c.getInputStream()));
    
    events.clear();
    
    String line;
    while((line=in.readLine())!=null)
    {
      if(line.length()==141)
      {
        String[] items=line.split("\\s+");
        if(items.length>12)
        {
          ResultItem ri=new ResultItem();
          ri.flareNr=Integer.parseInt(items[0]);
          ri.measurementStart=parseDate(items[1],items[2]);
          ri.measurementEnd=parseDate(items[1],items[4]);
          ri.measurementPeak=parseDate(items[1],items[3]);
          ri.peakCS=Integer.parseInt(items[6]);
          ri.totalCounts=Integer.parseInt(items[7]);
          ri.energyKeVFrom=Integer.parseInt(items[8].split("\\-")[0]);
          ri.energyKeVTo=Integer.parseInt(items[8].split("\\-")[1]);
          ri.xPos=Integer.parseInt(items[9]);
          ri.yPos=Integer.parseInt(items[10]);
          ri.radial=Integer.parseInt(items[11]);
          ri.AR=Integer.parseInt(items[12]);
          events.add(ri);
        }
      }
    }
    
    in.close();
    
    //using linear search later on, therefore sorting is not neccessary
    /*Collections.sort(events,new Comparator<ResultItem>(){
      public int compare(ResultItem _a,ResultItem _b)
      {
        return _a.measurementStart.compareTo(_b.measurementStart);
      }});*/
    
    lastUpdate=System.currentTimeMillis();
  }
  
  private Calendar parseDate(String _day,String _time)
  {
    String[] dateParts=_day.split("\\-");
    
    Calendar c=Calendar.getInstance();
    c.set(
          Integer.parseInt(dateParts[2]),
          0,
          Integer.parseInt(dateParts[0]),
          Integer.parseInt(_time.substring(0,2)),
          Integer.parseInt(_time.substring(3,5)),
          Integer.parseInt(_time.substring(6,8))
        );
    c.set(Calendar.MILLISECOND,0);
    c.setTimeZone(TimeZone.getTimeZone("GMT"));
    
    if(dateParts[1].equals("Jan"))
      c.set(Calendar.MONTH,Calendar.JANUARY);
    if(dateParts[1].equals("Feb"))
      c.set(Calendar.MONTH,Calendar.FEBRUARY);
    if(dateParts[1].equals("Mar"))
      c.set(Calendar.MONTH,Calendar.MARCH);
    if(dateParts[1].equals("Apr"))
      c.set(Calendar.MONTH,Calendar.APRIL);
    if(dateParts[1].equals("May"))
      c.set(Calendar.MONTH,Calendar.MAY);
    if(dateParts[1].equals("Jun"))
      c.set(Calendar.MONTH,Calendar.JUNE);
    if(dateParts[1].equals("Jul"))
      c.set(Calendar.MONTH,Calendar.JULY);
    if(dateParts[1].equals("Aug"))
      c.set(Calendar.MONTH,Calendar.AUGUST);
    if(dateParts[1].equals("Sep"))
      c.set(Calendar.MONTH,Calendar.SEPTEMBER);
    if(dateParts[1].equals("Oct"))
      c.set(Calendar.MONTH,Calendar.OCTOBER);
    if(dateParts[1].equals("Nov"))
      c.set(Calendar.MONTH,Calendar.NOVEMBER);
    if(dateParts[1].equals("Dec"))
      c.set(Calendar.MONTH,Calendar.DECEMBER);
    
    return c;
  }
  
  private boolean flareListNeedsUpdate()
  {
    return System.currentTimeMillis()>lastUpdate+updatePeriod;
  }
  
  public List<ResultItem> query(Calendar dateFrom,Calendar dateTo,int maxResults) throws Exception
  {
    //download the flare list if it is out of date
    if(flareListNeedsUpdate())
      parseFlareList();
    
    //naïve linear search trough the complete list, should be fast enough
    LinkedList<ResultItem> results=new LinkedList<ResultItem>();
    for(ResultItem ri:events)
      if(ri.measurementEnd.after(dateFrom) && ri.measurementStart.before(dateTo))
      {
        results.add(ri);
        if(results.size()>maxResults)
          break;
      }
    
    return results;
  }
  
}
