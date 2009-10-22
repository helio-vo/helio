package ch.i4ds.helio.dpas;

import java.util.*;

/**
 * Data container for the results of the DPAS. Each ResultItem contains data about a single
 * result. A result usually consists of some metadata, like date/time specifications, some
 * FITS data files and often some quicklook images.
 * 
 * @author Simon Felix (de@iru.ch)
 */
public class ResultItem
{
  //for all data providers
  public Calendar measurementStart;
  public Calendar measurementEnd;
  public String urlFITS;
  
  //used by hessi
  public String urlCorrectedRate;
  public String urlFrontRate;
  public String urlPartRate;
  public String urlRate;
  public String urlRearRate;
  
  //used by hessi flare list
  public int flareNr;
  public Calendar measurementPeak;
  public int peakCS;
  public int totalCounts;
  public int energyKeVFrom;
  public int energyKeVTo;
  public int xPos;
  public int yPos;
  public int radial;
  public int AR;
  
  //used by phoenix2
  public String urlPhaseFITSGZ;
  public String urlIntensityFITSGZ;
  
  //used by SolarMonitor SEIT
  public String urlPreview;
  public String urlPreviewThumb;
}
