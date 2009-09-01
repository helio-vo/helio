/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ricardodavid.guevara
 */
import java.util.Date;

public class FlareQuery {


    private Date minDate;
    private Date maxDate;
    private String goes;

    public String getGoes(){
        return this.goes;
    }

    public void setGoes(String goes){
        this.goes = goes;
    }



    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }
}
