/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.io.*;
import org.apache.log4j.Logger;


/**
 *
 * @author ricardodavid.guevara
 */
public class FileOutput {

    //private static final String CLASS = FileOutput.CLASS.getClass().getName();
    private static final String CLASS = "FileOutput";
    private static final Boolean debug = true;

    
    public static void write(String data) {
       final String methodName = "write";
        Logger logger = Logger.getLogger(CLASS + " " + methodName);
        if(debug)logger.error("entry data size = " + data.length());
        PrintWriter outFile = null;
        try {

            
            String file = "OutputData.xml";
            outFile = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            outFile.println(data);
            outFile.close();
            if(debug)logger.error("exit ok, file written " );
            
        } catch (IOException ex) {
            if(debug)logger.error("catch " + ex );
        } finally {
            
            outFile.close();
        }

    }

    

    
}
