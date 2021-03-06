DROP TABLE sec_catalogue;
CREATE TABLE sec_catalogue (
        cat_id                  INTEGER,
        name                    VARCHAR(32),
        description             VARCHAR(128),
        type                    VARCHAR(20),
        status                  VARCHAR(20),
        url                     VARCHAR(128),
        hec_groups_id           INTEGER,
        bg_color                VARCHAR(15),
        longdescription         VARCHAR(256),
        timefrom   DATE,
        timeto     DATE,
        update_timeto  VARCHAR(2),
        flare  VARCHAR(2),
        cme    VARCHAR(2),
        swind  VARCHAR(2),
        part   VARCHAR(2),
        otyp   VARCHAR(2),
        solar  VARCHAR(2),
        ips    VARCHAR(2),
        geo    VARCHAR(2),
        planet    VARCHAR(2),
        sort INTEGER,
        PRIMARY KEY (cat_id)    
);

GRANT SELECT ON TABLE sec_catalogue TO apache;


INSERT INTO sec_catalogue VALUES(01,'goes_sxr_flare','GOES Soft X-ray Flare List','event','active','ftp://ftp.ngdc.noaa.gov/STP/SOLAR_DATA/SOLAR_FLARES/FLARES_XRAY',1,'ccaaaa','Edited list of GOES flares, locations from SXT, EIT, SXI (NGDC)','1975/09/01','2999/01/01','y','y','','','','r','y','','','',1);
INSERT INTO sec_catalogue VALUES(02,'ngdc_halpha_flare','NGDC H-alpha Flare List','event','inactive','http://www.ngdc.noaa.gov/stp/solar/solarflares.html',1,'ccaaaa','List of H-alpha flares assembled from ground-based observatories (NGDC)','1980/01/01','2999/01/01','y','y','','','','r','y','','','',2);
INSERT INTO sec_catalogue VALUES(03,'noaa_energetic_event','NOAA Solar Energetic Event List','event','active','ftp://ftp.swpc.noaa.gov/pub/warehouse/',1,'ccbbbb','Energetic Solar Events, plus radio observations (NOAA)','1996/01/01','2999/01/01','y','y','','','','','','','','',3);
INSERT INTO sec_catalogue VALUES(04,'yohkoh_hxr_flare','Yohkoh/HXT Hard X-ray Flare List','event','closed','http://gedas22.stelab.nagoya-u.ac.jp/HXT/catalogue/image_html/199110.html',3,'ccbbbb','Flare list from the Yohkoh Hard X-ray Telescope','1991/10/01','2001/12/13','','y','','','','r','y','','','',21);
INSERT INTO sec_catalogue VALUES(05,'rhessi_hxr_flare','RHESSI Hard X-ray Flare List','event','active','http://hesperia.gsfc.nasa.gov/ssw/hessi/dbase',3,'ccbbbb','List of hard X-ray flares seen by RHESSI);','2002/02/12','2999/01/01','y','y','','','','r','y','','','',22);
INSERT INTO sec_catalogue VALUES(06,'kso_halpha_flare','Kanzelhoehe Solar Observatory H-alpha Flare List','event','active','http://www.kso.ac.att',3,'ccbbbb','Flares observed in H-alpha by the Kanzelhoehe Observatory','1984/01/02','2999/01/01','y','y','','','','r','y','','','',23);
INSERT INTO sec_catalogue VALUES(07,'soho_eit_wave_transient','SOHO/EIT Wave Transient List','list','closed','http://umbra.nascom.nasa.gov/eit',3,'ccbbbb','Waves seen in the EUV by SOHO/EIT','1997/03/25','1998/06/16','','','','','','r','y','','','',25);
INSERT INTO sec_catalogue VALUES(08,'yohkoh_sxt_trace_list','Yohkoh SXT TRACE flare list','list','deleted','http://www.lmsal.com/nitta/sxt_trace_flares/list.html',3,'ccbbbb','Common flares observed by Yohkoh/SXT and TRACE','1998/07/14','2001/12/14','','y','','','','r','y','','','',101);
INSERT INTO sec_catalogue VALUES(09,'goes_proton_event','GOES Proton Event List','event','active','http://umbra.nascom.nasa.gov/SEP/seps.html',2,'cccc99','Geo-effective Solar Energetic Proton (SEP) events (NOAA)','1976/04/30','2999/01/01','y','','','','y','I','','','y','',7);
INSERT INTO sec_catalogue VALUES(10,'soho_lasco_cme','SOHO/LASCO CME Event List','event','inactive','http://cdaw.gsfc.nasa.gov/CME_list/UNIVERSAL/text_ver/univ_all.txt',1,'cccc99','Edited list of CMEs detected by SOHO/LASCO (NASA/GSFC)','1996/01/11','2999/01/01','y','','y','','','r','y','','','',5);
INSERT INTO sec_catalogue VALUES(11,'lasco_cme_list','LASCO Preliminary CME List','event','deleted','ftp://lasco6.nascom.nasa.gov/pub/lasco/status',3,'cccc99','List of CMEs detected by SOHO/LASCO built from Alerts (SOHO/GSFC)','1996/01/14','2999/01/01','y','','y','','','r','y','','','',102);
INSERT INTO sec_catalogue VALUES(12,'bas_magnetic_storms','BAS Magnetic Storms','event','deleted','http://www.antarctica.ac.uk',2,'cccc99','Geo-effective magnetic storms, Dst < -50 nT (Contrib.: Richard Horne, BAS)','1992/01/08','2002/12/28','','','','y','','I','','','y','',103);
INSERT INTO sec_catalogue VALUES(13,'noaa_active_region_summary','NOAA/USAF Solar Active Region Summary List','index','active','ftp://ftp.swpc.noaa.gov/pub/warehouse/',4,'ivory','Parameters describing active regions: lat, long, size... (NOAA/USAF)','1996/01/02','2999/01/01','y','','','','','','','','','',40);
INSERT INTO sec_catalogue VALUES(14,'soho_camp','SoHO Campaign','list','deleted','http://sohowww.nascom.nasa.gov/data/summary/asplanned/campaign/soho_campaign.dat',5,'ivory','List of observing campaigns planned for SOHO Mission (SOHO/GSFC)','1995/12/02','2999/01/01','y','','','','','','','','','',104);
INSERT INTO sec_catalogue VALUES(15,'noaa_daily_solar_data','NOAA Daily Solar Data List','index','active','http://www.sec.noaa.gov/ftpdir/indices/old_indices',4,'ivory','Parameters describing solar activity: SSN, flare types... (NOAA)','1994/01/01','2999/01/01','y','','','','','','','','','',41);
INSERT INTO sec_catalogue VALUES(16,'sidc_sunspot_number','SIDC Smoothed Monthly Sunspot No.','index','deleted','http://sidc.oma.be/DATA/monthssn.dat',5,'ivory','Smoothed monthly sunspot number (SIDC, Belgium)','1749/07/01','2999/01/01','y','','','','','','','','','',105);
INSERT INTO sec_catalogue VALUES(17,'drao_10cm_flux','DRAO 10.7cm Radio Flux Monitor','index','deleted','http://www.spaceweather.gc.ca/sx-eng.php',5,'ivory','General index of solar activity (DRAO)','1975/01/01','2999/01/01','y','','','','','','','','','',106);
INSERT INTO sec_catalogue VALUES(18,'aad_gle','AAD Ground Level Enhancement List','event','static','http://neutronm.bartol.udel.edu/~pyle/GLE_list.txt',2,'cccc99','Enhancements detected by ground-based neutron monitors (Bartol)','1942/02/28','2006/12/13','','','','','y','I','','','y','',9);
INSERT INTO sec_catalogue VALUES(19,'ngdc_aastar_storm','NGDC AA* Major Magnetic Storm','event','static','ftp://ftp.ngdc.noaa.gov/STP/GEOMAGNETIC_DATA/AASTAR/',2,'cccc99','Major Magnetic Storms according to the AA* criteria (NGDC)','1868/03/20','2999/01/01','y','','','y','','I','','','y','',10);
INSERT INTO sec_catalogue VALUES(20,'ngdc_apstar_storm','NGDC Ap* Major Magnetic Storm','event','static','ftp://ftp.ngdc.noaa.gov/STP/GEOMAGNETIC_DATA/APSTAR/',2,'cccc99','Major Magnetic Storms according to the Ap* criteria (NGDC))','1932/03/10','2999/01/01','y','','','y','','I','','','y','',11);
INSERT INTO sec_catalogue VALUES(21,'ngdc_ssc','NGDC Storm Sudden Commencement List','event','static','ftp://ftp.ngdc.noaa.gov/STP/SOLAR_DATA/SUDDEN_COMMENCEMENTS/STORM2.SSC',2,'cccc99','Indicator of the onset of a Geo-magnetic storm (NGDC)','1968/01/02','2999/01/01','y','','','y','','I','','','y','',12);
INSERT INTO sec_catalogue VALUES(22,'cme_forbush_event','CME-related Forbush Decrease Event List','event','static','http://helios.izmiran.rssi.ru/',2,'cccc99','Forbush Decreases, related to CMEs (Contrib.: E. Eroshenko, Izmiran)','1957/08/29','2006/12/14','','','','','y','I','','','y','',8);
INSERT INTO sec_catalogue VALUES(23,'solar_wind_event','Solar Wind Events','event','deleted','http://www-istp.gsfc.nasa.gov/scripts/sw-cat/grep-ls/sw-cat-categories.html',2,'cccc99','Solar Wind Catalog (ISTP)','1993/01/10','2001/03/05','','','','','','','','','','',107);
INSERT INTO sec_catalogue VALUES(24,'stereo_hi_cme','STEREO Heliospheric Imager CME Event List','event','closed','http://www.sstd.rl.ac.uk/Stereo/Events%20Page.html',1,'ivory','STEREO/HI CME list','2007/03/31','2008/12/07','','','y','','','r','','y','','',6);
INSERT INTO sec_catalogue VALUES(25,'stereo_hi_sw_transient','STEREO/HI Solar Wind Transient List','event','inactive','http://www.sstd.rl.ac.uk/Stereo/HIEventList.html',3,'ivory','STEREO/HI solar wind transient events','2007/04/01','2010/09/19','','','','y','','r','','y','','',17);
INSERT INTO sec_catalogue VALUES(26,'wind_mfi_mag_cloud','WIND/MFI Magnetic Cloud List','event','static','http://wind.nasa.gov/mfi/mag_cloud_pub1.html#table',3,'ivory','WIND IMF MAGNETIC CLOUDS','1995/02/08','2007/11/20','','','','y','','I','','','','',13);
INSERT INTO sec_catalogue VALUES(27,'wind_mfi_bs_crossing_time','WIND/MFI Bow Shock Crossing Time List','event','deleted','http://wind.nasa.gov/mfi/bow_shock.html',3,'ivory','WIND IMF Bow Shock crossing','1994/11/12','2003/10/13','','','','y','','I','','','','',26);
INSERT INTO sec_catalogue VALUES(28,'istp_sw_event','ISTP Solar Wind Candidate Event List','event','closed','http://www-spof.gsfc.nasa.gov/scripts/sw-cat/Catalog_events.html',3,'ivory','ISTP solar wind catalogue candidate events','1993/01/10','2001/03/05','','','','y','','I','','','y','',16);
INSERT INTO sec_catalogue VALUES(29,'soho_pm_ip_shock','SOHO/CELIAS/MTOF/PM Interplanetary Shock List','event','inactive','http://umtof.umd.edu/pm/FIGS.html',3,'ivory','An incomplete list of possible Interplanetary Shocks observed by the PM','1996/09/26','2999/01/01','y','','','y','','I','','','','',15);
INSERT INTO sec_catalogue VALUES(30,'wind_mfi_ip_shock','WIND/MFI Interplanetary Shock List','event','closed','http://wind.nasa.gov/mfi_instrument/mfi/ip_shock.html',3,'ivory','WIND IMF IPS','1994/12/05','2999/01/01','y','','','y','','I','','','','',14);
INSERT INTO sec_catalogue VALUES(31,'wind_sw_crossing_time','WIND Solar Wind Crossing Time List','event','deleted','http://www-spof.gsfc.nasa.gov/scripts/sw-cat/Catalog_SWtimes.html',3,'ivory','WIND SOLAR WIND TIMES','1994/11/16','2999/01/01','y','','','y','','','','','','',27);
INSERT INTO sec_catalogue VALUES(32,'imp8_sw_crossing_time','IMP-8 Solar Wind Crossing Time List','event','deleted','http://www-spof.gsfc.nasa.gov/scripts/sw-cat/Catalog_SWtimes.html',3,'ivory','IMP8 SOLAR WIND TIMES','1993/01/30','2999/01/01','y','','','y','','','','','','',28);
INSERT INTO sec_catalogue VALUES(33,'tsrs_solar_radio_event','Trieste Solar Radio System (TSRS) Solar Radio Event List','event','inactive','http://radiosun.oats.inaf.it',3,'ivory','Trieste Solar Radio System event list','2000/07/12','2999/01/01','y','','','','','r','','','','',24);
INSERT INTO sec_catalogue VALUES(34,'latest_gev_flare','latest_gev_flare','event','deleted','http://www.mssl.ucl.ac.uk/~rdb/egso-sec/latest_gev.txt',3,'ivory','latest_gev_flare','2010/10/13','2999/01/01','y','','','','','','','','','',108);
INSERT INTO sec_catalogue VALUES(35,'gevloc_flares','gevloc_flares','event','deleted','http://www.mssl.ucl.ac.uk/~rdb/soars/gevloc_flares.txt',3,'ivory','gevloc_flares','2010/11/16','2999/01/01','y','','','','','','','','','',109);
INSERT INTO sec_catalogue VALUES(36,'wind_typeii_soho_cme','Type II Radio Burst (WIND) and Associated CME (SOHO) List','event','static','http://cdaw.gsfc.nasa.gov/CME_list/radio/waves_type2.html',3,'ivory','Wind/WAVES type II bursts and CMEs','1997/04/01','2999/01/01','y','','y','','','r','','y','','',20);
INSERT INTO sec_catalogue VALUES(37,'wind_stereo_ii_iv_radioburst','WIND and STEREO Candidate Type II and IV Radio Burst List','event','static','http://lep694.gsfc.nasa.gov/waves/',3,'ivory','HEC metadata for WAVES Experiment on WIND and STEREO - Type II/IV List','1994/12/23','2999/01/01','y','','','','','r','','y','','',19);
INSERT INTO sec_catalogue VALUES(38,'ulysses_swoops_icme','Ulysses/SWOOPS Interplanetary CME List','event','closed','http://swoops.lanl.gov/Ulysses-SWOOPSICMElist.doc',3,'ivory','Ulysses/SWOOPS CME list','1992/03/10','2999/01/01','y','','y','','','I','','y','','',18);
INSERT INTO sec_catalogue VALUES(39,'stereo_euvi_event','STEREO/SECCHI/EUVI Event List','event','closed','http://secchi.lmsal.com/EUVI/euvi_catalog.txt',3,'ivory','STEREO EUVI catalog','2006/12/04','2999/01/01','y','','','','','r','y','','','',4);
INSERT INTO sec_catalogue VALUES(40,'cactus_soho_cme','CACTus SOHO/LASCO CME List','event','static','http://sidc.oma.be/cactus',3,'ivory','CMEs autonomously detected by CACTus from SOHO/LASCO or STEREO/SECCHI','1997/05/17','2010/06/29','','','y','','','r','y','','','',29);
INSERT INTO sec_catalogue VALUES(41,'cactus_stereoa_cme','CACTus STEREO-A/COR CME List','event','static','http://sidc.oma.be/cactus',3,'ivory','CMEs autonomously detected by CACTus from SOHO/LASCO or STEREO/SECCHI','2007/03/03','2011/03/29','','','y','','','r','y','','','',30);
INSERT INTO sec_catalogue VALUES(42,'cactus_stereob_cme','CACTus STEREO-B/COR CME List','event','static','http://sidc.oma.be/cactus',3,'ivory','CMEs autonomously detected by CACTus from SOHO/LASCO or STEREO/SECCHI','2007/03/18','2011/03/28','','','y','','','r','y','','','',31);
INSERT INTO sec_catalogue VALUES(43,'cactus_soho_flow','CACTus SOHO/LASCO Flow List','event','static','http://sidc.oma.be/cactus',3,'ivory','CMEs autonomously detected by CACTus from SOHO/LASCO or STEREO/SECCHI','1997/05/06','2010/06/29','','','y','','','r','y','','','',32);
INSERT INTO sec_catalogue VALUES(44,'cactus_stereoa_flow','CACTus STEREO-A/COR Flow List','event','static','http://sidc.oma.be/cactus',3,'ivory','CMEs autonomously detected by CACTus from SOHO/LASCO or STEREO/SECCHI','2007/03/07','2011/03/29','','','y','','','r','y','','','',33);
INSERT INTO sec_catalogue VALUES(45,'cactus_stereob_flow','CACTus STEREO-B/COR Flow List','event','static','http://sidc.oma.be/cactus',3,'ivory','CMEs autonomously detected by CACTus from SOHO/LASCO or STEREO/SECCHI','2007/03/01','2011/03/27','','','y','','','r','y','','','',34);
INSERT INTO sec_catalogue VALUES(46,'ulysses_grb_xray_flare','Ulysses/GRB X-ray Flare List','event','closed','http://www.ssl.berkeley.edu/ipn3/grb.htm',3,'ivory','Ulysses GRB','1990/11/11','2003/11/03','','y','','','','r','y','y','','',35);
INSERT INTO sec_catalogue VALUES(47,'halo_cme_flare_magnetic_storm','SOHO/LASCO Halo CME with Associated Flare and Magnetic Storm List','list','closed','http://',3,'ivory','Halo CME Gopalswamy','1996/04/29','2005/12/07','','y','y','','','','y','','y','',36);
INSERT INTO sec_catalogue VALUES(48,'cactus_all','CACTus SOHO & STEREO CME & Flow List','event','static','http://sidc.oma.be/cactus',3,'ivory','CMEs autonomously detected by CACTus from SOHO/LASCO or STEREO/SECCHI','1997/05/06','2011/03/29','','','y','','','r','y','','','',37);
INSERT INTO sec_catalogue VALUES(49,'mars_earth_icme','Mars Earth ICME','event','static','http://',3,'ivory','Mars Earth ICME','2001/04/04','2003/11/21','','','y','','','','y','y','y','y',42);
