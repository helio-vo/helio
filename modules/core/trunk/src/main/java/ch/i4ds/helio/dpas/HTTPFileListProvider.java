package ch.i4ds.helio.dpas;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit.*;

/**
 * A generic class to query instruments that supply a list of files in HTML format (called
 * "directory page"). Actual implementations of data providers should inherit from this
 * class and implement the abstract methods.
 * 
 * @author Simon Felix (de@iru.ch)
 */
public abstract class HTTPFileListProvider implements DataProvider
{
  private int cacheDuration=7;
  
  public void setCacheDuration(int _days)
  {
    cacheDuration=_days;
  }
  
  class CacheEntry
  {
    long requestSent;
    List<ResultItem> results;
  }
  
  HashMap<Long,CacheEntry> cache=new HashMap<Long,CacheEntry>();
  
  
  /**
   * Request a directory page for every <i>n</i>.
   * 
   * Usually an archive is organized with one directory page per day/month/year.
   * Return Calendar.DAY_OF_MONTH, Calendar.HOUR, Calendar.YEAR, ... to configure the
   * HTTPFileListProvider accordingly.
   * 
   * @return A field from {@link java.util.Calendar}
   */
  public abstract int getFileListPer();
  
  /**
   * Creates the URL of a directory page of the specified date.
   * 
   * @param _start This date/time should be contained in the page.
   * @return An URL pointing to the directory page
   */
  public abstract String getFileListPath(Calendar _start);
  
  /**
   * Creates a ResultItem from a single link. This method will
   * be called numerous times per directory page, once for every link.
   * 
   * The method should check if the provided URL is valid. In the case
   * of invalid URLs the method should return NULL. If the result lies
   * outside the given time period it should return NULL.
   * 
   * @param _path URL of a link from the directory page
   * @param _dateFrom Beginning of the searched time period
   * @param _dateTo End of the searched time period
   * @return A result or NULL if no result can be created
   */
  public abstract ResultItem getData(String _path,Calendar _dateFrom,Calendar _dateTo);
  
  /**
   * Queries an instrument. This method gets all directory pages of the supplied
   * time period and parses the links.
   * 
   * @param _dateFrom Beginning of the time period to search
   * @param _dateTo End of the time period to search
   * @param _maxResults Will not return many more results than this
   * @return A list of the results
   */
  public List<ResultItem> query(final Calendar _dateFrom,final Calendar _dateTo,int _maxResults) throws Exception
  {
    final LinkedList<ResultItem> results=new LinkedList<ResultItem>();
    
    switch(getFileListPer())
    {
      case Calendar.YEAR:
        _dateFrom.set(Calendar.MONTH,0);
      case Calendar.MONTH:
        _dateFrom.set(Calendar.DAY_OF_MONTH,1);
      case Calendar.DAY_OF_MONTH:
      case Calendar.DAY_OF_WEEK:
      case Calendar.DAY_OF_YEAR:
        _dateFrom.set(Calendar.HOUR,0);
      case Calendar.HOUR:
        _dateFrom.set(Calendar.MINUTE,0);
      case Calendar.MINUTE:
        _dateFrom.set(Calendar.SECOND,0);
      case Calendar.SECOND:
        _dateFrom.set(Calendar.MILLISECOND,0);
      case Calendar.MILLISECOND:
        break;
      case Calendar.WEEK_OF_MONTH:
      case Calendar.WEEK_OF_YEAR:
        throw new RuntimeException("Directory pages per week are not supported");
    }
    
    for(final Calendar currentDay=(Calendar)_dateFrom.clone();currentDay.before(_dateTo);currentDay.add(getFileListPer(),1))
    {
      CacheEntry ce=cache.get(currentDay.getTimeInMillis());
      if(ce!=null && System.currentTimeMillis()-ce.requestSent<1000l*60*60*24*cacheDuration)
      {
        results.addAll(ce.results);
      }
      else
      {
        final LinkedList<ResultItem> resultsOfThisDirectoryPage=new LinkedList<ResultItem>();
        
        try
        {
          //read the directory index site
          final URLConnection c=new URL(getFileListPath(currentDay)).openConnection();
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
                      String filepath=null;
                      try
                      {
                        filepath=new URL(c.getURL(),(String)_a.getAttribute(currentAttrib)).toExternalForm();
                      }
                      catch(MalformedURLException e)
                      {
                        e.printStackTrace();
                      }
                      
                      //check if the referenced file has a valid file name
                      ResultItem result=getData(filepath,_dateFrom,_dateTo);
                      
                      //it does --> we found a result
                      if(result!=null)
                        resultsOfThisDirectoryPage.add(result);
                    }
                  }
                }
              }
            },true);
          }
          
          //close connection
          in.close();
        }
        catch(FileNotFoundException _fnfe)
        {
        }
        
        //add all the results of this index page
        results.addAll(resultsOfThisDirectoryPage);
        
        //and cache the page for future use
        ce=new CacheEntry();
        ce.requestSent=System.currentTimeMillis();
        ce.results=resultsOfThisDirectoryPage;
        cache.put(currentDay.getTimeInMillis(),ce);
      }
      
      if(results.size()>_maxResults)
        break;
    }
    return results;
  }
}
