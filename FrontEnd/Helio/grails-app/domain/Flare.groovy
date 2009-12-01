/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ricardodavid.guevara
 */
class Flare {
    int goes_id;
    String ntime_start;
    Date time_start;
    String time_peak;
    String time_end;
    String ntime_end;
    int nar;
    float latitude;
    float longitude;
    float long_carr;
    String xray_class;
    String optical_class;
    String hessi_measurementStart;
    String hessi_measurementEnd;
    String hessi_flareNr;
    String hessi_measurementPeak;
    String hessi_peakCS;
    String hessi_totalCounts;
    String hessi_energyKeVFrom;
    String hessi_energyKeVTo;
    String hessi_xPos;
    String hessi_yPos;
    String hessi_radial;
    String hessi_AR;
    String phoenix2_measurementStart;
    String phoenix2_flareNr;
    String phoenix2_peakCS;
    String phoenix2_totalCounts;
    String phoenix2_energyKeVFrom;
    String phoenix2_energyKeVTo;
    String phoenix2_xPos;
    String phoenix2_yPos;
    String phoenix2_radial;
    String phoenix2_AR;
    String phoenix2_urlPhaseFITSGZ;
    String phoenix2_urlIntensityFITSGZ;
    String urlPreview;
    String urlPreviewThumb;

    static constraints = {
        goes_id(nullable: false)
        time_start(nullable: true);
        time_peak(nullable: true);
        time_end(nullable: true);
        nar(nullable: true);
        latitude(nullable: true);
        longitude(nullable: true);
        long_carr(nullable: true);
        xray_class(nullable: true);
        optical_class(nullable: true);




    }
}

