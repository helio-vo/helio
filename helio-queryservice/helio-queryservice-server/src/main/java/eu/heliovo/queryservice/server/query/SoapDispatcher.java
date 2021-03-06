package eu.heliovo.queryservice.server.query;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Provider;
import javax.xml.ws.ServiceMode; 
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.handler.MessageContext;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.apache.log4j.Logger; 
import org.w3c.dom.Element;
import eu.heliovo.queryservice.common.dao.CommonDaoFactory;
import eu.heliovo.queryservice.common.dao.interfaces.CommonDao;
import eu.heliovo.queryservice.common.transfer.FileResultTO;
import eu.heliovo.queryservice.common.transfer.criteriaTO.CommonCriteriaTO;
import eu.heliovo.queryservice.common.util.CommonUtils;
import eu.heliovo.queryservice.common.util.FileUtils;
import eu.heliovo.queryservice.common.util.HsqlDbUtils;
import eu.heliovo.queryservice.common.util.InstanceHolders;
import eu.heliovo.queryservice.common.util.LongRunningQueryIdHolders;
import eu.heliovo.queryservice.common.util.RunService;
import eu.heliovo.queryservice.server.util.QueryThreadAnalizer;
import eu.heliovo.queryservice.server.util.ServiceInfo;

/**
 * Class: SoapDispatcher
 * Description: The dispatcher handles all soap requests and responses for a Query.  Called via the
 * SoapServlet. SoapRequests (Bodies) are placed into a DOM and by analyzing the uri 
 * determine the correct query service for the correct contract.  Responses are Stream based (NOT DOM) into an 
 * XMLStreamReader with the help of PipedStreams.
 *
 */
@WebServiceProvider(targetNamespace="",
	      serviceName="",
	      portName="")
	      
@ServiceMode(value=javax.xml.ws.Service.Mode.PAYLOAD)

public class SoapDispatcher implements Provider<Source> {
	@javax.annotation.Resource(type=Object.class)
	 protected WebServiceContext wsContext; 
	
  protected final  Logger logger = Logger.getLogger(this.getClass());

  /**
   * Method: Constructor
   * Description: Setup any initiations, mainly hashtable of uri to query 
   * interface versions.
   */
  public SoapDispatcher() {
	 
  }

  /**
   * Method: invoke
   * Description:For all soap requests and 
   * responses.Using Metro.
   * @param request - Source that is used to extract the soap request.
   * @return Source - response StreamSource that contains the 
   * soap response populated by InputStream (PipedInputStream) 
   */
  
	@Override
	public Source invoke(Source request) {
		// TODO Auto-generated method stub
		PipedReader pr=null;
		PipedWriter pw=null;
		CommonCriteriaTO comCriteriaTO=null;
		StreamSource responseReader = null;	
		File file=null;
		BufferedWriter bw =null;
		String saveFilePath=null;
		String saveTo =null;
		//Creating UUID and generating unique ID.
		UUID uuid = UUID.randomUUID();
		String randomUUIDString = uuid.toString();
		FileResultTO fileTO=new FileResultTO();
		try {
			
			 Element inputDoc=toDocument(request);
			 MessageContext mc = wsContext.getMessageContext();
			 HttpServletRequest req = (HttpServletRequest)mc.get(MessageContext.SERVLET_REQUEST);
			 String namespaceURI = inputDoc.getNamespaceURI();
		     String interfaceName = inputDoc.getLocalName().intern();
		     logger.info("interfacename: " + interfaceName + " Namespaceuri = " + namespaceURI);
		     //since this service will be used a lot, supposedly .intern() can be quicker
	   	     //each method should return a XMLStreamReader that is streamed back to the client.
		     comCriteriaTO=new CommonCriteriaTO(); 
			 pr = new PipedReader();
			 pw = new PipedWriter(pr);	 
			 if(req.getContextPath()!=null){
				 comCriteriaTO.setContextPath(req.getContextPath().substring(req.getContextPath().indexOf("-")+1,req.getContextPath().length()));
			 }
			 
			 if(namespaceURI != null && (namespaceURI.equals("http://helio-vo.eu/xml/QueryService/v1.0b") ||
					 namespaceURI.equals("http://helio-vo.eu/xml/LongQueryService/v1.0b"))) {
				 comCriteriaTO.setVotable1_2(true);				 
			 }
			 
			 if(namespaceURI != null && namespaceURI.trim().length() > 0) {
				 comCriteriaTO.setNamespaceURI(namespaceURI);
			 }
			 		
			 // This is common for Time. interface.
	    	 //Setting for START TIME parameter.			 
			 if(inputDoc.getElementsByTagNameNS("*","STARTTIME").getLength()>0 && inputDoc.getElementsByTagNameNS("*","STARTTIME").item(0).getFirstChild()!=null){
				 NodeList nodeList=inputDoc.getElementsByTagNameNS("*","STARTTIME");
    			 String[] startTime=new String[nodeList.getLength()];
    			 //List Name
		    	 for(int i=0;i<nodeList.getLength();i++){
		    		 startTime[i]=nodeList.item(i).getFirstChild().getNodeValue();
		    	 }

	    		 comCriteriaTO.setStartDateTimeList(startTime);
			 }
			 
    		 //Setting for TIME parameter.
    		 if(inputDoc.getElementsByTagNameNS("*","ENDTIME").getLength()>0 && inputDoc.getElementsByTagNameNS("*","ENDTIME").item(0).getFirstChild()!=null){
    			 NodeList nodeList=inputDoc.getElementsByTagNameNS("*","ENDTIME");
    			 String[] endTime=new String[nodeList.getLength()];
    			 //List Name
		    	 for(int i=0;i<nodeList.getLength();i++){
		    		 endTime[i]=nodeList.item(i).getFirstChild().getNodeValue();
		    	 }
				 comCriteriaTO.setEndDateTimeList(endTime);	
    		 }
    		 
    		 
    		 
	    	 //Setting for ListName parameter.
    		 if(inputDoc.getElementsByTagNameNS("*","FROM").getLength()>0){
    			 String sListName="";
    			 //Node list
    			 NodeList nodeList=inputDoc.getElementsByTagNameNS("*","FROM");
    			 String[] listName=new String[nodeList.getLength()];
    			 //List Name
		    	 for(int i=0;i<nodeList.getLength();i++){
		    		 String value=nodeList.item(i).getFirstChild().getNodeValue();
		    		 listName[i]=value;
		    		 sListName=sListName+value+",";
		    	 }
		    	 comCriteriaTO.setListTableName(listName);
		    	 if(sListName!=null && !sListName.trim().equals(""))
		    		 comCriteriaTO.setListName(sListName.substring(0, sListName.length()-1));
    		 }	
    		 //Setting value for independent query.
    		 if(comCriteriaTO.getStartDateTimeList()!=null)
    			 comCriteriaTO.setAllStartDate(CommonUtils.arrayToString(comCriteriaTO.getStartDateTimeList(), ","));
    		 if(comCriteriaTO.getEndDateTimeList()!=null)
    			 comCriteriaTO.setAllEndDate(CommonUtils.arrayToString(comCriteriaTO.getEndDateTimeList(), ","));
 		     comCriteriaTO.setContextUrl(CommonUtils.getUrl(req));
	    	 
    		//Setting for Start Row parameter.
			 if(inputDoc.getElementsByTagNameNS("*","STARTINDEX").getLength()>0 && inputDoc.getElementsByTagNameNS("*","STARTINDEX").item(0).getFirstChild()!=null){
				 String startRow = inputDoc.getElementsByTagNameNS("*","STARTINDEX").item(0).getFirstChild().getNodeValue();
				 comCriteriaTO.setStartRow(startRow);
			 }
			 
			//Setting for No Of Rows parameter.
			 if(inputDoc.getElementsByTagNameNS("*","MAXRECORDS").getLength()>0 && inputDoc.getElementsByTagNameNS("*","MAXRECORDS").item(0).getFirstChild()!=null){
				 String noOfRows = inputDoc.getElementsByTagNameNS("*","MAXRECORDS").item(0).getFirstChild().getNodeValue();
				 comCriteriaTO.setNoOfRows(noOfRows);
			 }
			 
			 /*
			 if(inputDoc.getElementsByTagNameNS("*","POS").getLength()>0 && inputDoc.getElementsByTagNameNS("*","POS").item(0).getFirstChild()!=null &&
				inputDoc.getElementsByTagNameNS("*","SIZE").getLength()>0 && inputDoc.getElementsByTagNameNS("*","SIZE").item(0).getFirstChild()!=null){
				 NodeList nodeList=inputDoc.getElementsByTagNameNS("*","POS");
				 String posTmp = nodeList.item(0).getFirstChild().getNodeValue();
				 String pos = posTmp;
				 String coordinate; // = defaultcoordinate
				 if(posTmp.indexOf(";") != -1) {
					 pos = posTmp.split(";")[0];
					 coordinate = posTmp.split(";")[1];
				 }
				 nodeList=inputDoc.getElementsByTagNameNS("*","SIZE");
				 String sizeTmp = nodeList.item(0).getFirstChild().getNodeValue();
				 String size = sizeTmp;
				 String shape; // = defaultShape
				 if(sizeTmp.indexOf(";")) {
					 size = sizeTmp.split(";")[0];
					 shape = sizeTmp.split(";")[1];
				 }
				 
				 String region;
				 if(inputDoc.getElementsByTagNameNS("*","REGION").getLength()>0 && inputDoc.getElementsByTagNameNS("*","REGION").item(0).getFirstChild()!=null){
					 NodeList nodeList=inputDoc.getElementsByTagNameNS("*","REGION");
					 region = nodeList.item(0).getFirstChild().getNodeValue();
					 
				 }
			 }
			 */
			 
			//Setting for Join query status.
			 if(inputDoc.getElementsByTagNameNS("*","JOIN").getLength()>0 && inputDoc.getElementsByTagNameNS("*","JOIN").item(0).getFirstChild()!=null){
				 String join = inputDoc.getElementsByTagNameNS("*","JOIN").item(0).getFirstChild().getNodeValue();
				 if(join!=null && !join.trim().equals(""))
					 comCriteriaTO.setJoin(join);
			 }
	    	 
			 //Full query interface
		     if(interfaceName != "TimeQuery".intern())  {
		    	 //Setting for Instrument parameter.
				 if(inputDoc.getElementsByTagNameNS("*","INSTRUMENT").getLength()>0 && inputDoc.getElementsByTagNameNS("*","INSTRUMENT").item(0).getFirstChild()!=null){
					 String instruments = inputDoc.getElementsByTagNameNS("*","INSTRUMENT").item(0).getFirstChild().getNodeValue();
					 comCriteriaTO.setInstruments(instruments);
				 }
				//Setting for WHERE parameter.
				 if(inputDoc.getElementsByTagNameNS("*","WHERE").getLength()>0 && inputDoc.getElementsByTagNameNS("*","WHERE").item(0).getFirstChild()!=null){
					 String whereClause = inputDoc.getElementsByTagNameNS("*","WHERE").item(0).getFirstChild().getNodeValue();
					 comCriteriaTO.setWhereClause(whereClause);
				 }
				
	    	 } 
		     
		     if(interfaceName == "SQLSelect".intern()){
		    	 comCriteriaTO.setSqlQuery(true);
		    	 
		    	 if(inputDoc.getElementsByTagNameNS("*","WHAT").getLength()>0){
	    			 //Node list
	    			 NodeList nodeList=inputDoc.getElementsByTagNameNS("*","WHAT");
	    			 String[] whats=new String[nodeList.getLength()];
	    			 logger.info("WHAT size " + nodeList.getLength());
	    			 //List Name
			    	 for(int i=0;i<nodeList.getLength();i++){
			    		 String value=nodeList.item(i).getFirstChild().getNodeValue();
			    		 whats[i]=value;
			    	 }
			    	 comCriteriaTO.setSelections(whats);
	    		 }
		    	 
		    	 if(inputDoc.getElementsByTagNameNS("*","SELECT").getLength()>0){
	    			 //Node list
	    			 NodeList nodeList=inputDoc.getElementsByTagNameNS("*","SELECT");
	    			 String[] whats=new String[nodeList.getLength()];
	    			 logger.info("SELECT size " + nodeList.getLength());
	    			 //List Name
			    	 for(int i=0;i<nodeList.getLength();i++){
			    		 String value=nodeList.item(i).getFirstChild().getNodeValue();
			    		 whats[i]=value;
			    	 }
			    	 comCriteriaTO.setSelections(whats);
	    		 }
		    	 

		    	 if(inputDoc.getElementsByTagNameNS("*","LIMIT").getLength()>0 && inputDoc.getElementsByTagNameNS("*","LIMIT").item(0).getFirstChild()!=null){
					 String noOfRows = inputDoc.getElementsByTagNameNS("*","LIMIT").item(0).getFirstChild().getNodeValue();
					 comCriteriaTO.setNoOfRows(noOfRows);
				 }
		    	 
		    	 if(inputDoc.getElementsByTagNameNS("*","OFFSET").getLength()>0 && inputDoc.getElementsByTagNameNS("*","OFFSET").item(0).getFirstChild()!=null){
					 String startRow = inputDoc.getElementsByTagNameNS("*","OFFSET").item(0).getFirstChild().getNodeValue();
					 comCriteriaTO.setStartRow(startRow);
				 }
		    	 
		    	 if(inputDoc.getElementsByTagNameNS("*","ORDER_BY").getLength()>0 && inputDoc.getElementsByTagNameNS("*","ORDER_BY").item(0).getFirstChild()!=null){
					 String orderBy = inputDoc.getElementsByTagNameNS("*","ORDER_BY").item(0).getFirstChild().getNodeValue();
					 comCriteriaTO.setOrderBy(orderBy);
				 }
		     }
		     
		     if(interfaceName == "CoordinateQuery".intern()) {
	    		 //Setting for POS( RA & DEC) parameter.
				 if(inputDoc.getElementsByTagNameNS("*","POS").getLength()>0 && inputDoc.getElementsByTagNameNS("*","POS").item(0).getFirstChild()!=null){
					 String pos = inputDoc.getElementsByTagNameNS("*","POS").item(0).getFirstChild().getNodeValue();
					 if(pos!=null && !pos.equals("")){
						 String[] posArr=pos.split(";");
						 if(posArr.length>1){
							 comCriteriaTO.setPosRef(posArr[1]);
						 }
						 String[] arrPos=posArr[0].split(",");
						 if(arrPos.length>0)
							 comCriteriaTO.setPosRa(arrPos[0]);
						 if(arrPos.length>1)
							 comCriteriaTO.setPosDec(arrPos[1]);
					 }
				 }
				 if(inputDoc.getElementsByTagNameNS("*","REGION").getLength()>0 && inputDoc.getElementsByTagNameNS("*","REGION").item(0).getFirstChild()!=null){
					 String sRegion = inputDoc.getElementsByTagNameNS("*","REGION").item(0).getFirstChild().getNodeValue();
					 //Getting parse region.
					 Map<String,String> map=CommonUtils.parseRegionParameter(sRegion);
					 //Region.
					 comCriteriaTO.setsRegion(map.get("region"));
					 //Region values.
					 comCriteriaTO.setsRegionValues(map.get("regionvalues"));
				 }
				//Setting for SIZE parameter.
				 if(inputDoc.getElementsByTagNameNS("*","SIZE").getLength()>0 && inputDoc.getElementsByTagNameNS("*","SIZE").item(0).getFirstChild()!=null){
					 String size = inputDoc.getElementsByTagNameNS("*","SIZE").item(0).getFirstChild().getNodeValue();
					 comCriteriaTO.setSize(size);
				 }
				 //Long running query interface.
	    	 }
		     
			 if(interfaceName == "getTableNames".intern()){
				 new ServiceInfo(pw).start();
				 
			 } else if(interfaceName == "getTableFields".intern()){
				 if(inputDoc.getElementsByTagNameNS("*","table_name").getLength()>0 && inputDoc.getElementsByTagNameNS("*","table_name").item(0).getFirstChild()!=null){
					 String table_name = inputDoc.getElementsByTagNameNS("*","table_name").item(0).getFirstChild().getNodeValue();
					 new ServiceInfo(table_name, pw).start();
				 }
				 
				 
			 } else if(interfaceName == "LongTimeQuery".intern() || interfaceName == "LongQuery".intern()){
						 
	    		 //Setting for No Of Rows parameter.
				 if(inputDoc.getElementsByTagNameNS("*","SAVETO").getLength()>0 && inputDoc.getElementsByTagNameNS("*","SAVETO").item(0).getFirstChild()!=null){
					 saveTo = inputDoc.getElementsByTagNameNS("*","SAVETO").item(0).getFirstChild().getNodeValue();
				 } 
				 // Save To file.
				 if(saveTo==null || saveTo==""){
				    saveTo= InstanceHolders.getInstance().getProperty("hsqldb.database.path")+"/files";
				    File f = new File(saveTo);
				    //Checking if directry present; if not create one.
				    if(!f.exists())
				    	f.mkdir();		    
				 }
				 logger.info(" : save to file location :  "+saveTo);
				 //passing save to value to common TO.	
				 comCriteriaTO.setSaveto(saveTo);
				 comCriteriaTO.setLongRunningQueryStatus("id");
				 //
				 fileTO.setRandomUUIDString(randomUUIDString);

				 String xmlString=CommonUtils.createXmlForWebService(fileTO);
				 System.out.println(" : XML String : "+xmlString);
				 
				 //Setting piped reader 
				 comCriteriaTO.setLongRunningPrintWriter(pw);
				 //Set data to print writer.
				 comCriteriaTO.setDataXml(xmlString);
				 //Thread created to load data into response.
				 CommonDao commonNameDao= CommonDaoFactory.getInstance().getCommonDAO();
				 commonNameDao.generatelongRunningQueryXML(comCriteriaTO);	
				 logger.info(" : Done VOTABLE : ");	
				 if(saveTo!=null && saveTo.contains("ftp")){
				    FileUtils.saveFileToFtp(saveTo,"votable_"+randomUUIDString+".xml");	    			    	
				 }else{
				 //Save the file to local system.
				    saveFilePath=saveTo+"/votable_"+randomUUIDString+".xml";
				    file = new File(saveFilePath);
				    FileOutputStream fo = new FileOutputStream(file);
				    FileDescriptor fd = fo.getFD();
					bw = new BufferedWriter(new OutputStreamWriter(fo));
					comCriteriaTO.setPrintWriter(bw);
					comCriteriaTO.setLongFD(fd);
				 }
				
				 //Running the service in back round
				 RunService oRunReport= new RunService(comCriteriaTO,randomUUIDString);
				 Thread th = new Thread(oRunReport);
				 th.start();
				 //Long running query status of completion.
		 }else if(interfaceName == "GetStatus".intern()){
			 String sID =null;
			 if(inputDoc.getElementsByTagNameNS("*","ID").getLength()>0 && inputDoc.getElementsByTagNameNS("*","ID").item(0).getFirstChild()!=null){
	    		 sID = inputDoc.getElementsByTagNameNS("*","ID").item(0).getFirstChild().getNodeValue();
			 }
			 //
			 comCriteriaTO.setLongRunningQueryStatus("status");
			 String sStatus=LongRunningQueryIdHolders.getInstance().getProperty(sID);
				if(sStatus==null || sStatus.trim().equals(""))
				  sStatus=HsqlDbUtils.getInstance().getStatusFromHsqlDB(sID);
				//Setting file TO
				fileTO.setRandomUUIDString(sID);
				fileTO.setStatus(sStatus);
				
				String xmlString=CommonUtils.createXmlForWebService(fileTO);
				System.out.println(" : XML String : "+xmlString);	
				//Setting piped reader 
				 comCriteriaTO.setLongRunningPrintWriter(pw);
				//Set data to print writer.
				comCriteriaTO.setDataXml(xmlString);
				//Thread created to load data into response.
				CommonDao commonNameDao= CommonDaoFactory.getInstance().getCommonDAO();
				commonNameDao.generatelongRunningQueryXML(comCriteriaTO);	
				//Long running query file location path result.
		 }else if(interfaceName == "GetResult".intern()){
			 String sID =null;
			 if(inputDoc.getElementsByTagNameNS("*","ID").getLength()>0 && inputDoc.getElementsByTagNameNS("*","ID").item(0).getFirstChild()!=null){
	    		 sID = inputDoc.getElementsByTagNameNS("*","ID").item(0).getFirstChild().getNodeValue();
			 }
			 //
			 comCriteriaTO.setLongRunningQueryStatus("result");
			 String sStatus=LongRunningQueryIdHolders.getInstance().getProperty(sID);
				if(sStatus==null || sStatus.trim().equals(""))
				  sStatus=HsqlDbUtils.getInstance().getStatusFromHsqlDB(sID);
				String contextPath=CommonUtils.getUrl(req,sID);
				//Setting file TO
				fileTO.setRandomUUIDString(sID);
				fileTO.setStatus(sStatus);
				fileTO.setsUrl(contextPath);
				String xmlString=CommonUtils.createXmlForWebService(fileTO);
				System.out.println(" : XML String : "+xmlString);	
				//Setting piped reader 
				comCriteriaTO.setLongRunningPrintWriter(pw);
				//Set data to print writer.
				comCriteriaTO.setDataXml(xmlString);
				//Thread created to load data into response.
				CommonDao commonNameDao= CommonDaoFactory.getInstance().getCommonDAO();
				commonNameDao.generatelongRunningQueryXML(comCriteriaTO);	
				//Long running query response result.
		 }else if(interfaceName == "GetResponseFile".intern()){
			 //Presently not in use.
			 StringBuilder fileData=null;
			 String sID =null;
			 if(inputDoc.getElementsByTagNameNS("*","ID").getLength()>0 && inputDoc.getElementsByTagNameNS("*","ID").item(0).getFirstChild()!=null){
	    		 sID = inputDoc.getElementsByTagNameNS("*","ID").item(0).getFirstChild().getNodeValue();
			 }
			 String sUrl=HsqlDbUtils.getInstance().getUrlFromHsqlDB(sID);
			    /*
				if(sUrl == null) {
					System.out.println("1soap-surl is null try again after a sleep");
					Thread.sleep((long)800);
					sUrl=HsqlDbUtils.getInstance().getUrlFromHsqlDB(sID);
					if(sUrl == null) {
						System.out.println("2soap-surl is null try again");
					}
				}
				*/
				if(sUrl.contains("ftp")){
					String ftpUrl=HsqlDbUtils.getInstance().getUrlFromHsqlDB(sID);
					fileData=FileUtils.getFileDataFromFtp(ftpUrl);
				}else if(sUrl.startsWith("http")) {
				
				}
				else{
					File xmlfile = new File(sUrl);
			        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			        DocumentBuilder builder = factory.newDocumentBuilder();
			        Document document = builder.parse(xmlfile);
			        fileData=FileUtils.readDataFromFile(document);
				}
				
				pw.write(fileData.toString());
		 }
		 
		 if(interfaceName == "Query".intern() || interfaceName == "CoordinateQuery".intern() || interfaceName == "TimeQuery".intern() || interfaceName == "SQLSelect".intern()){ 
			 //Indicator to define VOTABLE for Web Service request
			 comCriteriaTO.setStatus("WebService");
			 //Setting piped reader 
			 comCriteriaTO.setPrintWriter(pw);
	    	 //Thread created to load data into PipeReader.
			 new QueryThreadAnalizer(comCriteriaTO).start();				
			 logger.info(" : Done VOTABLE : ");				
		 }
			
		 responseReader= new StreamSource(pr); 
			 
		 logger.info(" : Returning response reader soap output : ");
		 return responseReader; 
		}catch (Exception e) {
			e.printStackTrace();
			logger.fatal(" Exception occured while creating soap output : ",e);
	    }
		return null;
	}
	
	
	
	@SuppressWarnings("unused")
	private  StreamSource getInputSourceByFile(String filePath) throws IOException 
	{
		InputStream is = null;
		try {
			File path=new File(filePath);
			is = new FileInputStream(path);
			return new StreamSource(is, path.getName());
		} catch (IOException e) {
			if (is != null) {
				try {
					is.close();
					} catch (Exception x) { /** ignore */ }
			}
			throw e;
		}
	}

	
	/*
	 * Method used to convert Source to dom object.
	 */
	private synchronized Element toDocument(Source src) throws TransformerException {
        DOMResult result = new DOMResult();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        try {
            transformer.transform(src, result);
        } catch (TransformerException te) {
            throw new TransformerException("Error while applying template", te);
        }
        Element root = ((Document)result.getNode()).getDocumentElement();
       return root;
    }

}