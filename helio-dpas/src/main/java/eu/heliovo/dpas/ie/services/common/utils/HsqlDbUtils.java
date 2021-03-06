package eu.heliovo.dpas.ie.services.common.utils;

import org.apache.log4j.Logger;
import eu.heliovo.dpas.ie.services.CommonDaoFactory;
import eu.heliovo.dpas.ie.services.common.dao.exception.DetailsNotFoundException;
import eu.heliovo.dpas.ie.services.common.dao.interfaces.LongRunningQueryDao;
import eu.heliovo.dpas.ie.services.common.dao.interfaces.ShortNameQueryDao;
import eu.heliovo.dpas.ie.services.common.transfer.ResultTO;

public class HsqlDbUtils {
	
	 protected final  Logger logger = Logger.getLogger(this.getClass());
	 private static ClassLoader loader;
	 
	 
	 private HsqlDbUtils(){
	 }
	 
	 public static HsqlDbUtils getInstance() {
			return HsqlProfilerHolder.instance;
	 }

	 private static class HsqlProfilerHolder {
		private static HsqlDbUtils instance = new HsqlDbUtils();
	 }
	 
	 /*
	  * 
	  */
	 public synchronized void  loadProviderAccessTable(String fileName,String tableName) throws DetailsNotFoundException
	 {
		 ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
		 shortNameDao.loadProviderAccessTable(fileName,tableName);
	 }
	 
	 /*
	  * 
	  */
	 public synchronized ResultTO[]  getAccessTableBasedOnInst(String instName,String prvdType) throws DetailsNotFoundException
	 {
		 ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
		 return shortNameDao.getAccessTableBasedOnInst(instName,prvdType);
	 }
	 
	 /*
	  * 
	  */
	 public synchronized ResultTO[]  getFtpAccessTableBasedOnInst(String instName) throws DetailsNotFoundException
	 {
		 ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
		 return shortNameDao.getFtpAccessTableBasedOnInst(instName);
	 }
	
	 /*
	  * 
	  */
	 public synchronized ResultTO[]  getAccessTableDetails(String where) throws DetailsNotFoundException
	 {
		 ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
		 return shortNameDao.getAccessTableDetails(where);
	 }
	 
	 public synchronized void  insertStatusIntoHsqlDB(String randomUUIDString,String status) throws DetailsNotFoundException
	 {
		 LongRunningQueryDao shortNameDao= CommonDaoFactory.getInstance().getLongRunningQueryDao();
		 shortNameDao.insertStatusToHsqlDB(randomUUIDString, status);
	 }
	 
	 public synchronized void insertURLToHsqlDB(String randomUUIDString,String url) throws DetailsNotFoundException
	 {
		 LongRunningQueryDao shortNameDao= CommonDaoFactory.getInstance().getLongRunningQueryDao();
		 shortNameDao.insertURLToHsqlDB(randomUUIDString, url);
	 }
	 
	 public synchronized String  getStatusFromHsqlDB(String randomUUIDString) throws DetailsNotFoundException
	 {
		LongRunningQueryDao shortNameDao= CommonDaoFactory.getInstance().getLongRunningQueryDao();
		String sStatus=shortNameDao.getStatusFromHsqlDB(randomUUIDString);
		return sStatus;
	 }
	 
	 public synchronized String  getUrlFromHsqlDB(String randomUUIDString) throws DetailsNotFoundException
	 {
		LongRunningQueryDao shortNameDao= CommonDaoFactory.getInstance().getLongRunningQueryDao();
		String sUrl=shortNameDao.getUrlFromHsqlDB(randomUUIDString);
		return sUrl;
	 }
	 
	 /**
	  * 
	  * @throws DetailsNotFoundException
	  */
	 public synchronized void  deleteUrlFromHsqlDB() throws DetailsNotFoundException
	 {
		LongRunningQueryDao longRunningDao= CommonDaoFactory.getInstance().getLongRunningQueryDao();
		longRunningDao.deleteUrlFromHsqlDB();
	 }
	 
	 /**
	  * 
	  * @throws DetailsNotFoundException
	  */
	 public synchronized void  deleteStatusFromHsqlDB() throws DetailsNotFoundException
	 {
		LongRunningQueryDao longRunningDao= CommonDaoFactory.getInstance().getLongRunningQueryDao();
		longRunningDao.deleteStatusFromHsqlDB();
	 }
	 
	 /**
	  * 
	  * @throws DetailsNotFoundException
	  */
	 public synchronized void  deleteSavedVoTable() throws DetailsNotFoundException
	 {
		LongRunningQueryDao longRunningDao= CommonDaoFactory.getInstance().getLongRunningQueryDao();
		longRunningDao.deleteSavedVoTable();
	 }
	 
	 /*
	 public void dropTable(){
		 try{
		 Connection con = null;
		 Statement st = null;
		 ResultSet rs=null;
		 Properties prop=HsqlDbUtils.getInstance().loadPropertyValues();
		 //Connecting to database.						
		 con = ConnectionManager.getConnectionLongRunningQuery(prop);
		 st = con.createStatement();
	     st.executeUpdate("drop table job_url_table ");
		 con.commit();
		 prop=null;
		 }catch(Exception e ){
			 e.printStackTrace();
		 }
	 }
    */
}