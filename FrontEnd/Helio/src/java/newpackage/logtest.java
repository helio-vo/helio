package newpackage;


import org.apache.log4j.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ricardodavid.guevara
 */
public class logtest {
    public static void nothing(){
        Logger logger = Logger.getLogger("hol");
        System.out.println(logger.getAdditivity());
        logger.error("a ver");
        logger.debug("this is a test from java");
        System.out.println("dafuck");
    }

}
