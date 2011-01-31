package ch.i4ds.helio.frontend;

import ch.i4ds.helio.core.GetVersion;
import ch.i4ds.helio.core.ResultItem;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import ch.i4ds.helio.frontend.parser.FileOutput;
import ch.i4ds.helio.frontend.parser.TestSAX;

/**
 *
 * @author David
 */
public class ToolBox {

    private static final String CLASS = ToolBox.class.getClass().getName();
    private static String webServiceVersion = "";
    // private static final String CLASS = "ToolBox";
    private static final Boolean debug = true;
    //returns the resultitem for the oldwebservice

    public static Stack<Stack<String>> queryWebService(Date dateStart, Date dateEnd) {

        final String methodName = "queryWebService";
        Logger logger = Logger.getLogger(CLASS + " " + methodName);

        if (debug) {
            logger.error("entry " + dateStart + " " + dateEnd);
        }

        try {
            //    java.lang.String dateStartString = "2004.01.01 00:00:00";
            //  java.lang.String dateEndString = "2004.01.02 00:00:00";
            String dateStartString = ToolBox.getFortmattedDate(dateStart);
            String dateEndString = ToolBox.getFortmattedDate(dateEnd);

            ch.i4ds.helio.core.FrontendFacadeService service = new ch.i4ds.helio.core.FrontendFacadeService();
            ch.i4ds.helio.core.FrontendFacade port = service.getFrontendFacadePort();

            String workflowfile = port.runInitialWorkflow(dateStartString, dateEndString, "", ""); //recieve workflow voTable

            webServiceVersion = port.getVersion();
            System.out.println(webServiceVersion + "sabe");
            
            //String workflowfile = ""; //added in case workflow is not responding

            FileOutput fileOutput = new FileOutput();// using this writer class to avoid errors in glassfish
            fileOutput.write(workflowfile);

            TestSAX sax = new TestSAX("OutputData.xml");//davidTODO: need to adapt this to scalable code
            Stack<Stack<String>> stack = sax.getStack();

            if (debug) {
                logger.error("return result number =" + stack.size());
            }
            return stack;

        } catch (Exception ex) {
            if (debug) {
                ex.printStackTrace();
                logger.error("catch " + ex);
            }
            if (debug) {
                logger.error("return " + null);
            }
            return null;
        }


    }

    public static String getWebServiceVersion(){
        return webServiceVersion;
    }
    //returns the date in the format needed to query the webservices
    public static String getFortmattedDate(Date date) {
        final String methodName = "getFortmattedDate";
        Logger logger = Logger.getLogger(CLASS + " " + methodName);
        if (debug) {
            logger.error("entry " + date);
        }

        String DATE_FORMAT = "yyyy.MM.dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        if (debug) {
            logger.error("return " + sdf.format(date));
        }
        return sdf.format(date);


    }

    /**
    public static Date castDate(XMLGregorianCalendarImpl inDate) {
    try {
    String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
    Date outDate = sdf.parse(inDate.toString());
    //inDate.toGregorianCalendar();
    return outDate;
    } catch (ParseException ex) {

    System.out.println(ex);
    // Logger.getLogger(ToolBox.class.getName()).log(Level.SEVERE, null, ex);
    return new Date();
    }
    }
     * **/
    public static String sliceString(String imageUrl) {

        if (imageUrl == null || imageUrl.equals("")) {
            return null;
        }
        List<String> result = new LinkedList<String>();
        //imageUrl
        StringTokenizer st = new StringTokenizer(imageUrl, "/");
        String s = null;
        while (st.hasMoreTokens()) {
            s = st.nextToken();
        }
        result.add(s);
        imageUrl = imageUrl.substring(0, imageUrl.length() - s.length());
        //result.add();
        // System.out.println(s);


        return s;
    }

    public static String getUrlImage(String imageUrl) {
        try {
            // Create a URL for the image's location
            URL url = new URL(imageUrl);

            java.awt.Image image = ImageIO.read(url); //java.awt.Toolkit.getDefaultToolkit (  ) .createImage ( url ) ;
            int w = image.getWidth(null);
            int h = image.getHeight(null);

            BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = bi.createGraphics();
            g2.drawImage(image, 0, 0, null);
            g2.dispose();
            g2 = null;
            //File f = new File(".\\web-app\\images\\temp\\output.png");
            //System.out.println(f.pathSeparator);
            //System.out.println(f.getAbsolutePath());
            //System.out.println(f.getPath());
            String fileName = sliceString(imageUrl);

            ImageIO.write(bi, "png", new File(".\\web-app\\images\\temp\\" + fileName));

            return fileName;

        } catch (MalformedURLException e) {
            //   Logger.getLogger(ToolBox.class.getName()).log(Level.SEVERE, null, e);

            return null;
        } catch (IOException e) {
            //  Logger.getLogger(ToolBox.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }
}