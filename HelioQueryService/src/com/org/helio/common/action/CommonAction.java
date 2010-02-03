package com.org.helio.common.action;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;

import javax.servlet.http.HttpServletRequest;
import com.opensymphony.xwork2.ActionSupport;
import com.org.helio.common.dao.CommonDaoFactory;
import com.org.helio.common.dao.interfaces.ShortNameQueryDao;
import com.org.helio.common.transfer.CommonTO;
import com.org.helio.common.util.ConnectionManager;
 
public class CommonAction  extends ActionSupport
{	
	
    private static final long serialVersionUID = 1L;
    protected final  Logger logger = Logger.getLogger(this.getClass());
    
	public String display(){ 
		System.out.println(" WELCOME JSP PAGE");
    	return "SUCCESS";
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


	public String getConfigurationPropertyFilePage(){
		
		ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
		try {
			hmbDatabaseTableList=shortNameDao.getDatabaseTableNames(ConnectionManager.getConnection());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(" Exception occured in getDatabaseTableNames method of ShortNameQueryDaoImpl :",e);
		}
		
		return "SUCCESS";
	}
	
	private CommonTO[] columnTO;
	public CommonTO[] getColumnTO() {
		return columnTO;
	}

	public void setColumnTO(CommonTO[] columnTO) {
		this.columnTO = columnTO;
	}

	public String getTableColumnList(){
		//to get a table name selected.
		HttpServletRequest req = ServletActionContext.getRequest();
		String tableName = req.getParameter("tableName");
		ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
		try {
			columnTO=shortNameDao.getTableColumnNames(ConnectionManager.getConnection(),tableName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(" Exception occured in getTableColumnList method of ShortNameQueryDaoImpl :",e);
	}
	
	return "SUCCESS";
}
       
}
