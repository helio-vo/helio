package ch.i4ds.helio.dpas;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.activation.DataHandler;
import javax.jws.*;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.*;
import javax.swing.text.html.HTMLEditorKit.*;
import org.egso.common.context.*;
import org.egso.common.services.provider.ResponseQueryProvider;
import org.egso.provider.query.QueryEngine;

@WebService
public class HessiService
{
  @WebMethod
  public String[] getAhoi()
  {
    QueryEngine engine=new QueryEngine();
    EGSOContext ctx=EGSOContextFactory.newInstance(EGSOContext.ROLE_PROVIDER,"test","1.0").createContext(EGSOContext.CONTEXT_QUERY,"test");
    String QUERY = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><query><select><field name=\"filename\"/><field name=\"instrument\"/><field name=\"start-date\"/><field name=\"end-date\"/><field name=\"wavelength\"/></select><data same-level=\"OR\" lower-level=\"AND\"><param name=\"date\"><interval><start>2002-07-14 00:00:00</start><end>2002-07-14 23:59:59</end></interval></param></data></query>";
    engine.query(ctx.toXML(),QUERY,new ResponseQueryProvider()
    {
      public String sendResponse(String context,DataHandler results) throws Exception
      {
        System.out.println((String)results.getContent());
        return null;
      }
    });
    
    return new String[]{"ahoi","matrosen"};
  }
  
  
  /**
   * A data class to represent a single result of the hesssi service
   * 
   * @author Simon Felix (de@iru.ch)
   */
  public static class MeasurementURLs
  {
    public Calendar measurementStart;
    public String urlFITS;
    public String urlCorrectedRate;
    public String urlFrontRate;
    public String urlPartRate;
    public String urlRate;
    public String urlRearRate;
  }
  
  @WebMethod
  public MeasurementURLs[] getListOfFiles(String _dateFrom,String _dateTo) throws IOException
  {
    if(_dateFrom.length()!="2001.01.01 00:00:00".length())
      throw new RuntimeException("Invalid time range (dateFrom)");
    if(_dateTo.length()!="2001.01.01 00:00:00".length())
      throw new RuntimeException("Invalid time range (dateTo)");
    
    final Calendar from=Calendar.getInstance();
    final Calendar to=Calendar.getInstance();
    from.set(
          Integer.parseInt(_dateFrom.substring(0,4)),
          Integer.parseInt(_dateFrom.substring(5,7))-1,
          Integer.parseInt(_dateFrom.substring(8,10)),
          Integer.parseInt(_dateFrom.substring(11,13)),
          Integer.parseInt(_dateFrom.substring(14,16)),
          Integer.parseInt(_dateFrom.substring(17,19))
        );
    to.set(
        Integer.parseInt(_dateTo.substring(0,4)),
        Integer.parseInt(_dateTo.substring(5,7))-1,
        Integer.parseInt(_dateTo.substring(8,10)),
        Integer.parseInt(_dateTo.substring(11,13)),
        Integer.parseInt(_dateTo.substring(14,16)),
        Integer.parseInt(_dateTo.substring(17,19))
      );
    
    if(from.after(to))
      throw new RuntimeException("Invalid time range (from > to)");
    
    if(to.getTimeInMillis()-from.getTimeInMillis()>1000l*60*60*24*365*5)
      throw new RuntimeException("Invalid time range (>5 years)");
    
    final LinkedList<MeasurementURLs> results=new LinkedList<MeasurementURLs>();
    for(final Calendar currentDay=(Calendar)from.clone();currentDay.before(to);currentDay.add(Calendar.DAY_OF_MONTH,1))
    {
      try
      {
        //read the directory index site
        final URLConnection c=new URL("http://www.hedc.ethz.ch/data/"
            +String.format("%04d/%02d/%02d",
                currentDay.get(Calendar.YEAR),
                currentDay.get(Calendar.MONTH)+1, //january==0 in java
                currentDay.get(Calendar.DAY_OF_MONTH))+"/").openConnection();
        
        BufferedReader in=new BufferedReader(new InputStreamReader(c.getInputStream()));
        
        //parse the site and collect all links. this has to be synchronized
        //because the swing html-parser is not thread safe
        synchronized(Parser.class)
        {
          Parser p=HTMLParserFactory.createParser();
          p.parse(in,new ParserCallback(){
            public void handleStartTag(HTML.Tag _t, MutableAttributeSet  _a,int _pos)
            {
              //did we find an <a> tag?
              if(_t==HTML.Tag.A)
              {
                //look for the href attribute
                Enumeration<?> attribs=_a.getAttributeNames();
                while(attribs.hasMoreElements())
                {
                  Object currentAttrib=attribs.nextElement();
                  if("href".equals(currentAttrib.toString()))
                  {
                    //we found <a href="...">, add this to the list of links
                    String filename=(String)_a.getAttribute(currentAttrib);
                    
                    //check if the referenced file has a valid file name
                    if(filename.toLowerCase().endsWith(".fits"))
                      if(filename.length()=="hsi_20000619_191200_000.fits".length())
                      {
                        //parse the time of the file. we don't have to parse the
                        //date, because we found this file already in a date-specific
                        //directory
                        Calendar fileTime=(Calendar)currentDay.clone();
                        fileTime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(filename.substring(13,15)));
                        fileTime.set(Calendar.MINUTE,Integer.parseInt(filename.substring(15,17)));
                        fileTime.set(Calendar.SECOND,Integer.parseInt(filename.substring(17,19)));
                        
                        //does the file date lie within the requested time range?
                        if(!fileTime.after(to) && !fileTime.before(from))
                        {
                          MeasurementURLs m=new MeasurementURLs();
                          m.measurementStart=fileTime;
                          m.urlFITS=c.getURL()+filename;
                          
                          String quickViewURLStart=String.format("http://www.hedc.ethz.ch/data/metadata/%04d/%02d/%02d/hsi_%04d%02d%02d_%02d%02d%02d_",
                              fileTime.get(Calendar.YEAR),
                              fileTime.get(Calendar.MONTH)+1, //january==0 in java
                              fileTime.get(Calendar.DAY_OF_MONTH),
                              fileTime.get(Calendar.YEAR),
                              fileTime.get(Calendar.MONTH)+1, //january==0 in java
                              fileTime.get(Calendar.DAY_OF_MONTH),
                              fileTime.get(Calendar.HOUR_OF_DAY),
                              fileTime.get(Calendar.MINUTE),
                              fileTime.get(Calendar.SECOND)
                            );
                          
                          m.urlCorrectedRate=quickViewURLStart+"corrected_rate.png";
                          m.urlFrontRate=quickViewURLStart+"front_rate.png";
                          m.urlPartRate=quickViewURLStart+"part_rate.png";
                          m.urlRate=quickViewURLStart+"rate.png";
                          m.urlRearRate=quickViewURLStart+"rear_rate.png";
                          
                          results.add(m);
                        }
                      }
                  }
                }
              }
            }
          },true);
        }
        
        in.close();
      }
      catch(IOException _ioe)
      {
        //couldn't find index page --> ignore
      }
      catch(NumberFormatException _nfe)
      {
        //couldn't parse filename
      }
    }
    
    return results.toArray(new MeasurementURLs[0]);
  }
}
