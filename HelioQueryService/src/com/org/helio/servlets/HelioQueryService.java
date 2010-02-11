package com.org.helio.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.org.helio.common.dao.CommonDaoFactory;
import com.org.helio.common.dao.interfaces.CommonDao;
import com.org.helio.common.transfer.criteriaTO.CommonCriteriaTO;

/**
 * Servlet implementation class HelioQueryService
 */
public class HelioQueryService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelioQueryService() {
        super();        
    }

	/** 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml;charset=UTF-8");
		 CommonCriteriaTO comCriteriaTO=new CommonCriteriaTO();
		 PrintWriter printWriter = response.getWriter(); 
		try{
			System.out.println(System.getenv("file.path"));
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
		    comCriteriaTO.setPrintWriter(printWriter);
		    //Setting time parameter
		    String sTime=request.getParameter("TIME");
		    if(sTime!=null && !sTime.equals("")){
				String[] dateTime= sTime.split("/");			
				System.out.println(" startDateTime : "+dateTime[0]+" startEndTime : "+dateTime[1]);			
				comCriteriaTO.setStartDateTime(dateTime[0]);
				comCriteriaTO.setEndDateTime(dateTime[1]);						
		    }else{ 
		    	comCriteriaTO.setStartDateTime(dateFormat.format(new Date()));
				comCriteriaTO.setEndDateTime(dateFormat.format(new Date()));
		    }
		    //Setting for Instrument parameter.
		    String sInstrument=request.getParameter("INSTRUMENT");
		    comCriteriaTO.setInstruments(sInstrument);
		    //Setting for List Name parameter.
		    String sListName=request.getParameter("LISTNAME");
		    comCriteriaTO.setListName(sListName);
		    //Setting where cluase parameter
		    String whereClause=request.getParameter("WHERE");
		    comCriteriaTO.setWhereClause(whereClause);
		    //Setting start row parameter
		    String startRow=request.getParameter("STARTINDEX");
		    comCriteriaTO.setStartRow(startRow);
		    //Setting no of row parameter
		    String noOfRows=request.getParameter("MAXRECORDS");
		    comCriteriaTO.setNoOfRows(noOfRows);
			CommonDao commonNameDao= CommonDaoFactory.getInstance().getCommonDAO();
			commonNameDao.generateVOTableDetails(comCriteriaTO);
		}catch(Exception e){
			e.printStackTrace();
		}		
		finally
		{
			if(printWriter!=null){
				printWriter.close();
				printWriter=null;
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
