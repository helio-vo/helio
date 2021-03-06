package eu.heliovo.dpas.ie.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import eu.heliovo.dpas.ie.services.common.transfer.FileResultTO;
import eu.heliovo.dpas.ie.services.common.utils.CommonUtils;
import eu.heliovo.dpas.ie.services.common.utils.FileUtils;
import eu.heliovo.dpas.ie.services.common.utils.HsqlDbUtils;
import eu.heliovo.dpas.ie.services.common.utils.LongRunningQueryIdHolders;
/**
 * Servlet implementation class HelioQueryService
 */
public class ResultQueryService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected final  Logger logger = Logger.getLogger(this.getClass());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResultQueryService() {
        super();        
    }

	/** 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml;charset=UTF-8");
		PrintWriter pw = response.getWriter(); 
		FileResultTO fileTO=new FileResultTO();
		try{
			
			//Creating a xml file 
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
	        //creating a new instance of a DOM to build a DOM tree.
	        Document doc = docBuilder.newDocument();
	       
			String sID=request.getParameter("ID");
			String sMode=request.getParameter("MODE");
			//Mode to deciede the type of Phase.
			if(sMode!=null && sMode.equalsIgnoreCase("phase")){
				String sStatus=LongRunningQueryIdHolders.getInstance().getProperty(sID);
				if(sStatus==null || sStatus.trim().equals(""))
				  sStatus=HsqlDbUtils.getInstance().getStatusFromHsqlDB(sID);
				//setting file TO.
				fileTO.setRandomUUIDString(sID);
				fileTO.setStatus(sStatus);
				String xmlString=CommonUtils.createXmlForWebService(fileTO);
				System.out.println(" : XML String : "+xmlString);
				pw.write(xmlString);
			}else if(sMode!=null && sMode.equalsIgnoreCase("result")){
				String sStatus=LongRunningQueryIdHolders.getInstance().getProperty(sID);
				if(sStatus==null || sStatus.trim().equals(""))
				  sStatus=HsqlDbUtils.getInstance().getStatusFromHsqlDB(sID);
				String contextPath=CommonUtils.getUrl(request,sID);
				//Setting file TO.
				fileTO.setRandomUUIDString(sID);
				fileTO.setStatus(sStatus);
				fileTO.setsUrl(contextPath);
				String xmlString=CommonUtils.createXmlForWebService(fileTO);
				System.out.println(" : XML String : "+xmlString);
				pw.write(xmlString);
			}else if(sMode!=null && sMode.equalsIgnoreCase("file")){
				StringBuilder fileData=null;
				String sUrl=HsqlDbUtils.getInstance().getUrlFromHsqlDB(sID);
				
				if(sUrl!=null && sUrl.startsWith("ftp")){
					String ftpUrl=HsqlDbUtils.getInstance().getUrlFromHsqlDB(sID);
					fileData=FileUtils.getFileDataFromFtp(ftpUrl);
				}else if(sUrl!=null && !sUrl.trim().equals("")){
					File xmlfile = new File(sUrl);
			        fileData=FileUtils.convertStreamToString(new FileInputStream(xmlfile));
				}
		        //
				if(fileData!=null)
					pw.write(fileData.toString());
			}
			pw.flush();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(" : Exception occured while creating the file :  "+e.getMessage());
		}	
		//
		finally
		{
		
			if(pw!=null){
				pw.close();
				pw=null;
			}
		}
	}
	
	
	
	
	
	 
 }
