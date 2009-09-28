package ch.i4ds.helio.core;

import java.util.*;
import javax.jws.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import org.apache.tools.ant.filters.StringInputStream;
import org.apache.tools.ant.util.DateUtils;
import org.w3c.dom.*;
import ch.i4ds.helio.dpas.HessiService;

/**
 * Facade for the web sites. This class contains various convenience functions
 * which the web sites will call to get their information. The functions load
 * the appropriate Taverna workflow, execute it and then return the results in
 * a convenient fashion.
 * 
 * @author Simon Felix (de@iru.ch)
 */
@WebService
public class FrontendFacade
{
  private TavernaExecutor taverna;
  
  @WebMethod(exclude=true)
  public void setTaverna(TavernaExecutor _taverna)
  {
    taverna=_taverna;
  }
  
  /*
   * 
   * SELECT * FROM goes_xray_flare WHERE  xray_class>'C6' AND time_start>='2009-08-23 00:00:00' AND time_start<='2009-09-23 23:59:59' ORDER BY time_start LIMIT 1500;
   * 
   * http://soleil.i4ds.ch:9090/HELIO-core/services/frontend?wsdl
   * 
   */
  
  /**
   * Gets a list of files from HESSI over a specified time period.
   * 
   * @param _dateFrom A string in the following format YYYY-MM-DD HH:MM:SS, specifying the beginning of the time period
   * @param _dateTo A string in the following format YYYY-MM-DD HH:MM:SS, specifying the end of the time period
   * @return A list of URLs in the time period pointing to quickview and FITS files
   */
  @SuppressWarnings("unchecked")
  @WebMethod
  public HessiService.HessiURLs[] getHessiEvents(String _dateFrom,String _dateTo) throws Exception
  {
    //prepare the inputs of the workflow
    Map<String,Object> inputs=new LinkedHashMap<String,Object>();
    inputs.put("dateFrom",_dateFrom);
    inputs.put("dateTo",_dateTo);
    
    //load the workflow and execute it
    Map<String,Object> wf_results=taverna.executeWorkflow(getClass().getResourceAsStream("workflows/hessi-get-filelist.t2flow"),inputs);
    
    //get the output of the workflow
    List<String> stringlist=(List<String>)wf_results.get("list");
    
    //convert the output of the workflow from XML to pojo's
    HessiService.HessiURLs[] results=new HessiService.HessiURLs[stringlist.size()];
    
    DocumentBuilderFactory domFactory=DocumentBuilderFactory.newInstance();
    domFactory.setNamespaceAware(true);
    
    XPath xpath=XPathFactory.newInstance().newXPath();
    XPathExpression selectMeasurementStart=xpath.compile("/return/measurementStart");
    XPathExpression selectUrlFITS=xpath.compile("/return/urlFITS");
    XPathExpression selectUrlCorrectedRate=xpath.compile("/return/urlCorrectedRate");
    XPathExpression selectUrlFrontRate=xpath.compile("/return/urlFrontRate");
    XPathExpression selectUrlPartRate=xpath.compile("/return/urlPartRate");
    XPathExpression selectUrlRate=xpath.compile("/return/urlRate");
    XPathExpression selectUrlRearRate=xpath.compile("/return/urlRearRate");
    
    //go trough all results and convert every single one
    for(int i=0;i<stringlist.size();i++)
    {
      Document doc=domFactory.newDocumentBuilder().parse(new StringInputStream(stringlist.get(i)));
      
      results[i]=new HessiService.HessiURLs();
      
      Calendar c=Calendar.getInstance();
      c.setTime(DateUtils.parseIso8601DateTime((String)selectMeasurementStart.evaluate(doc,XPathConstants.STRING)));
      results[i].measurementStart=c;
      
      results[i].urlFITS=(String)selectUrlFITS.evaluate(doc,XPathConstants.STRING);
      results[i].urlCorrectedRate=(String)selectUrlCorrectedRate.evaluate(doc,XPathConstants.STRING);
      results[i].urlFrontRate=(String)selectUrlFrontRate.evaluate(doc,XPathConstants.STRING);
      results[i].urlPartRate=(String)selectUrlPartRate.evaluate(doc,XPathConstants.STRING);
      results[i].urlRate=(String)selectUrlRate.evaluate(doc,XPathConstants.STRING);
      results[i].urlRearRate=(String)selectUrlRearRate.evaluate(doc,XPathConstants.STRING);
    }
    
    return results;
  }
}
