/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.i4ds.helio.dpas;

import java.io.IOException;
import java.util.Calendar;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author de
 */
@WebService
public interface HessiService
{
  @WebMethod
  public String[] getAhoi();

  public static class MeasurementURLs
  {
    public Calendar measurementStart;
    public String urlFITS;
    public String urlCorrectedRate;
    public String urlFrontRate;
    public String urlPartRate;
    public String urlRate;
    public String urlRearRate;
  }

  @WebMethod
  public MeasurementURLs[] getListOfFiles(String _dateFrom,String _dateTo) throws IOException;
}