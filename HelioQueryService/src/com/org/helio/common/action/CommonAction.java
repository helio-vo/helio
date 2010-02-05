package com.org.helio.common.action;

import java.sql.Connection;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.ActionSupport;
import com.org.helio.common.dao.CommonDaoFactory;
import com.org.helio.common.dao.interfaces.ShortNameQueryDao;
import com.org.helio.common.transfer.CommonTO;
import com.org.helio.common.util.ConfigurationProfiler;
import com.org.helio.common.util.ConnectionManager;
import com.org.helio.common.util.InstanceHolders;
 
public class CommonAction  extends ActionSupport
{	
	
    private static final long serialVersionUID = 1L;
    protected final  Logger logger = Logger.getLogger(this.getClass());
    
    public boolean statusDisplay;
    
    
    
	public boolean isStatusDisplay() {
		return statusDisplay;
	}

	public void setStatusDisplay(boolean statusDisplay) {
		this.statusDisplay = statusDisplay;
	}

	public String display(){ 
		String sReturnStatus="ERROR";
		
		//Checking for connection
		Connection con=ConnectionManager.getConnectionForWebApp();
		if(con!=null){
			setStatusDisplay(true);
			sReturnStatus="SUCCESS";
		}else{
			sReturnStatus="ERROR";
			setStatusDisplay(false);
			addActionError("Could not connect database, please check database configuration details.");
		}
		
    	return sReturnStatus;
    }
	
	private String cmbDatabaseTableList;
	private HashMap<String,String> hmbDatabaseTableList;
	
		
	public String getCmbDatabaseTableList() {
		return cmbDatabaseTableList;
	}

	public void setCmbDatabaseTableList(String cmbDatabaseTableList) {
		this.cmbDatabaseTableList = cmbDatabaseTableList;
	}

	public HashMap<String, String> getHmbDatabaseTableList() {
		return hmbDatabaseTableList;
	}

	public void setHmbDatabaseTableList(HashMap<String, String> hmbDatabaseTableList) {
		this.hmbDatabaseTableList = hmbDatabaseTableList;
	}

	/*
	 * Configuration of database table.
	 */
	public String getDatabaseConfigurationPage()
	{
		
		//Setting jdbc driver name
		setJdbcDriverName(InstanceHolders.getInstance().getProperty("jdbc.driver"));
		//Setting jdbc connection url
		setJdbcUrl(InstanceHolders.getInstance().getProperty("jdbc.url"));
		//Setting jdbc connection user
		setJdbcUser(InstanceHolders.getInstance().getProperty("jdbc.user"));
		//Setting jdbc connection password.
		setJdbcPassword(InstanceHolders.getInstance().getProperty("jdbc.password"));
		String sReturnStatus="ERROR";
		//Checking for connection
		Connection con=ConnectionManager.getConnectionForWebApp();
		if(con!=null){
			setStatusDisplay(true);
			sReturnStatus="SUCCESS";
		}else{
			sReturnStatus="ERROR";
			setStatusDisplay(false);
			addActionError(" Database configuration details is incorrect.");
		}
		
		return sReturnStatus;
	}
	

	public String getConfigurationPropertyFilePage(){
		String sReturnStatus="ERROR";
		Connection con=null;
		ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
		try {
			System.out.println(""+getJdbcDriverName()+""+getJdbcUrl()+""+getJdbcUser()+""+getJdbcPassword());
			//Setting jdbc driver name
			if(getJdbcDriverName()!=null && !getJdbcDriverName().equals(""))
				InstanceHolders.getInstance().setProperty("jdbc.driver",getJdbcDriverName());
			else
				setJdbcDriverName(InstanceHolders.getInstance().getProperty("jdbc.driver"));
			//Setting jdbc connection url
			if(getJdbcUrl()!=null && !getJdbcUrl().equals(""))
				InstanceHolders.getInstance().setProperty("jdbc.url",getJdbcUrl());
			else
				setJdbcUrl(InstanceHolders.getInstance().getProperty("jdbc.url"));	
			//Setting jdbc connection user
			if(getJdbcUser()!=null && !getJdbcUser().equals(""))
				InstanceHolders.getInstance().setProperty("jdbc.user",getJdbcUser());
			else
				setJdbcUser(InstanceHolders.getInstance().getProperty("jdbc.user"));
			//Setting jdbc connection password.
			if(getJdbcPassword()!=null && !getJdbcPassword().equals(""))
				InstanceHolders.getInstance().setProperty("jdbc.password",getJdbcPassword());
			else
				setJdbcPassword(InstanceHolders.getInstance().getProperty("jdbc.password"));
			//Checking for connection
			con= ConnectionManager.getConnectionForWebApp();
			if(con!=null){
				setStatusDisplay(true);
				sReturnStatus="SUCCESS";
				//List of table names of the database
				hmbDatabaseTableList=shortNameDao.getDatabaseTableNames(con);
			}else{
				sReturnStatus="ERROR";
				setStatusDisplay(false);
				addActionError("Could not connect database, please check database configuration details.");
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(" Exception occured in getDatabaseTableNames method of ShortNameQueryDaoImpl :",e);
			addActionError("Couldn't retrieve database table name.");
			sReturnStatus="ERROR";
			
		}
		
		return sReturnStatus;
	}
	
	
	
	
	private CommonTO[] columnTO;
	private String tableName;
	public CommonTO[] getColumnTO() {
		return columnTO;
	}

	public void setColumnTO(CommonTO[] columnTO) {
		this.columnTO = columnTO;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableColumnList(){
		//to get a table name selected.
		HttpServletRequest req = ServletActionContext.getRequest();
		String tableName = req.getParameter("tableName");
		
		ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
		try {
			columnTO=shortNameDao.getTableColumnNames(ConnectionManager.getConnectionForWebApp(),tableName);
			setTableName(tableName);
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(" Exception occured in getTableColumnList method of ShortNameQueryDaoImpl :",e);
	}
	
	return "SUCCESS";
   }
	
    String[] addedTableDetails;
	private String filenameandpath;
	private String jdbcDriverName;
	private String jdbcUrl;
	private String jdbcUser;
	private String jdbcPassword;
	
	
	public String[] getAddedTableDetails() {
		return addedTableDetails;
	}
	
	public void setAddedTableDetails(String[] addedTableDetails) {
		this.addedTableDetails = addedTableDetails;
	}

	public String getFilenameandpath() {
		return filenameandpath;
	}

	public void setFilenameandpath(String filenameandpath) {
		this.filenameandpath = filenameandpath;
	}

	public String getJdbcDriverName() {
		return jdbcDriverName;
	}

	public void setJdbcDriverName(String jdbcDriverName) {
		this.jdbcDriverName = jdbcDriverName;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getJdbcUser() {
		return jdbcUser;
	}

	public void setJdbcUser(String jdbcUser) {
		this.jdbcUser = jdbcUser;
	}

	public String getJdbcPassword() {
		return jdbcPassword;
	}

	public void setJdbcPassword(String jdbcPassword) {
		this.jdbcPassword = jdbcPassword;
	}


	public String createConfigurationFile(){
		return "SUCCESS";
	}
	
	private String fileNamePath;
	

	public String getFileNamePath() {
		return fileNamePath;
	}

	public void setFileNamePath(String fileNamePath) {
		this.fileNamePath = fileNamePath;
	}
	
	
       
}
