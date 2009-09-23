package ch.i4ds.helio.core;

import java.util.*;
import javax.jws.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import org.apache.tools.ant.filters.StringInputStream;
import org.apache.tools.ant.util.DateUtils;
import org.w3c.dom.*;
import ch.i4ds.helio.dpas.HessiService;

@WebService
public class FrontendFacade
{
  private TavernaExecutor taverna;
  
  @WebMethod(exclude=true)
  public void setTaverna(TavernaExecutor _taverna)
  {
    taverna=_taverna;
  }
  
  @WebMethod
  public HessiService.MeasurementURLs[] getHessiEvents(String _dateFrom,String _dateTo) throws Exception
  {
    Map<String,Object> inputs=new LinkedHashMap<String,Object>();
    
    inputs.put("dateFrom",_dateFrom);
    inputs.put("dateTo",_dateTo);
    
    Map<String,Object> wf_results=taverna.executeWorkflow(getClass().getResourceAsStream("workflows/hessi-get-filelist.t2flow"),inputs);
    
    List<String> stringlist=(List<String>)wf_results.get("a");
    HessiService.MeasurementURLs[] results=new HessiService.MeasurementURLs[stringlist.size()];
    
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
    
    
    for(int i=0;i<stringlist.size();i++)
    {
      Document doc=domFactory.newDocumentBuilder().parse(new StringInputStream(stringlist.get(i)));
      
      
      
      results[i]=new HessiService.MeasurementURLs();
      //results[i].measurementStart=(String)selectMeasurementStart.evaluate(doc,XPathConstants.STRING);
      
      Calendar c=Calendar.getInstance();
      c.setTime(DateUtils.parseIso8601Date((String)selectMeasurementStart.evaluate(doc,XPathConstants.STRING)));
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
