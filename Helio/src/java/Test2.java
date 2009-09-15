
import ch.i4ds.helio.dpas.HessiService;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ricardodavid.guevara
 */
public class Test2 {

    public Test2(){
        System.out.println("simon doesnt know a girl that speaks spanish");
    }
    public void setTestProperty(HessiService hessiService){
        try {
            System.out.println(hessiService.getAhoi());
        } catch (RemoteException ex) {
            Logger.getLogger(Test2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
