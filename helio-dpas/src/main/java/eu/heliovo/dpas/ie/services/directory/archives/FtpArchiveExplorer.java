/*
 * 
 */
package eu.heliovo.dpas.ie.services.directory.archives;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import eu.heliovo.dpas.ie.services.directory.transfer.FtpDataTO;
import eu.heliovo.dpas.ie.services.directory.utils.DPASResultItem;
import eu.heliovo.dpas.ie.services.directory.utils.DateIterator;
import eu.heliovo.dpas.ie.services.directory.utils.DebugUtilities;
import eu.heliovo.dpas.ie.services.directory.utils.FtpUtils;

public class FtpArchiveExplorer
{
	private	DebugUtilities		debugUtils	=	new DebugUtilities();
	private FtpDataTO 			ftpTO 		= null;
	/**
	 * Instantiates a new new archive explorer.
	 * 
	 * @param path
	 *            the path
	 */
	public FtpArchiveExplorer(FtpDataTO ftpTO)
	{
		super();
		this.ftpTO = ftpTO;
	}

	/**
	 * @param from
	 * @param to
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws Exception
	 */
	public LinkedList<DPASResultItem> query(Date from, Date to) throws MalformedURLException,IOException,Exception
	{
		SimpleDateFormat df=new SimpleDateFormat(ftpTO.getYearPattern()+ftpTO.getMonthPattern());
		FtpUtils  ftpUtils=new FtpUtils(ftpTO.getFtpHost(),ftpTO.getFtpUser(),ftpTO.getFtpPwd());
		//
		String workingDir=ftpTO.getWorkingDir();
    	Iterator<Date> i = new DateIterator(from, to);
    	while(i.hasNext())
    	{
    		Date date = i.next();
    		String formatDate=df.format(date);
    		System.out.println("------------>"+formatDate);
    		//
    		ftpTO.setWorkingDir(workingDir+formatDate);
    		ftpUtils.getFtpFileDetails(ftpTO);
    	}
    	//
		return ftpUtils.returnDPASResultItem();
	}
}