package eu.heliovo.dpas.ie.services.directory.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import eu.heliovo.dpas.ie.services.directory.transfer.FtpDataTO;

public class FtpUtils {
	FTPClient client=null;
	static LinkedList<DPASResultItem> 	results = null;
	/**
	 * 
	 * @param host
	 * @param user
	 * @param pass
	 * @throws IOException
	 */
	public FtpUtils(String host,String user,String pass) throws IOException
	{
		 client = new FTPClient();
		 client.connect(host);
         client.login(user, pass);
         results = new LinkedList<DPASResultItem>();
	}
	
	public LinkedList<DPASResultItem> returnDPASResultItem()
	{
		return results;
	}
	
	public FTPClient getFtpConection() throws IOException
	{
		return client;
	}
	
	/**
	 * @param ftpTO
	 * @return
	 * @throws Exception
	 */
	public DPASResultItem getFtpFileDetails(FtpDataTO ftpTO) throws Exception
	{
		DPASResultItem				currDpasResult	=	new DPASResultItem();
		Calendar					currCalendar	=	null;
		Calendar					fromDate=Calendar.getInstance();
		Calendar					toDate=Calendar.getInstance();
		System.out.println("-------->  Working Folder ------->"+ftpTO.getWorkingDir());
		fromDate.setTime(ftpTO.getDateValueFrom());
		toDate.setTime(ftpTO.getDateValueTo());
		boolean status=client.changeWorkingDirectory(ftpTO.getWorkingDir()); 
		client.enterLocalPassiveMode();
		if(status){
	        FTPFile[] ftpFiles = client.listFiles();
	        for (FTPFile ftpFile : ftpFiles) {
	        	if (ftpFile.getType() == FTPFile.FILE_TYPE && !ftpFile.getName().contains("robots.txt")) {
	                currDpasResult	=	new DPASResultItem();
	    			currCalendar	=	new GregorianCalendar();
	    			ftpTO.setFtpFileName(ftpFile.getName());
	    			//Getting actual date value
	    			ftpTO.setFtpDateFileName(getFileNameBasedOnPattern(ftpTO));
	    			//Setting time
	    			currCalendar.setTime(convertDateFormatBasedOnProvider(ftpTO));
	    			System.out.println("FTPFile: " + ftpFile.getName() +  ";"+ftpFile.getTimestamp().getTime()+" : "+ FileUtils.byteCountToDisplaySize(ftpFile.getSize()));
	    			//System.out.println("fromDate  "+fromDate.getTime()+"  currCalendar "+currCalendar.getTime()+" toDate "+toDate.getTime());
	    			if(currCalendar.after(fromDate) && currCalendar.before(toDate)){
		    			currDpasResult.urlFITS	=	"ftp://"+ftpTO.getFtpHost()+"/"+ftpTO.getWorkingDir()+"/"+ftpFile.getName();
		    			currDpasResult.measurementStart	=	currCalendar;
		    			currDpasResult.fileSize =	 FileUtils.byteCountToDisplaySize(ftpFile.getSize());
		    			
		    			results.add(currDpasResult);
	    			}
	            }
	        }
	        client.changeWorkingDirectory("/");
	}else{
		System.out.println("  Error occured while changing directory : "+client.getReplyString());
	}
		
		return null;
	}
	
	/**
	 * 
	 * @param ftpTO
	 * @return
	 */
	public Calendar getProviderDateBasedOnFormat(FtpDataTO ftpTO)
	{
		return null;
	}
	
	/**
	 * 
	 * @param ftpTO
	 * @return
	 * @throws Exception
	 */
	private String getFileNameBasedOnPattern(FtpDataTO ftpTO) throws Exception
	{
		String sDateValue="";
		try{
			Matcher m = Pattern.compile(ftpTO.getFtpPattern()).matcher(ftpTO.getFtpFileName());
			if(m.find()){
				if(ftpTO.getProviderSource()!=null && ftpTO.getProviderSource().trim().equalsIgnoreCase("goes")){
					sDateValue=ftpTO.getFtpFileName().substring(m.start(), m.end());
				}else{
					sDateValue=ftpTO.getFtpFileName().substring(m.start(), m.end());
				}
			}
		}catch(Exception e)
		{
			
		}
		return sDateValue;
	}
	
	/**
	 * 
	 * @param ftpTO
	 * @return
	 */
	private Date convertDateFormatBasedOnProvider(FtpDataTO ftpTO)
	{
		try
		{
			//Parse the orignal date
			SimpleDateFormat sdfSource = new SimpleDateFormat(ftpTO.getFtpDateFormat());
			//Parse string date to Date object
			System.out.println(ftpTO.getFtpDateFileName());
			return sdfSource.parse(ftpTO.getFtpDateFileName());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	 
}
