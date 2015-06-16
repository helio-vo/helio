-- EGSO - SFEC server
-- creation script
-- by M.Jurcev,A.Santin last rev. 09-may-2005

DELETE FROM sgas_event;
--COPY sgas_event (time_start,time_peak,time_end,nar,latitude,longitude,xray_class,optical_class,radio_245mhz,radio_10cm,radio_sweep_ii,radio_sweep_iv,swf) FROM '/var/www/html/sec/solar.info/SEL/SGAS.postgres.converted';
COPY sgas_event (time_start,time_peak,time_end,nar,latitude,longitude,xray_class,optical_class,radio_245mhz,radio_10cm,radio_sweep_ii,radio_sweep_iv,swf,ntime_start,ntime_end,long_carr) FROM '/var/www/html/sec/temp/SGS.postgres.converted';

DELETE FROM lasco_cme_list;
COPY lasco_cme_list (time_start,pa_central,description) FROM '/var/www/html/sec/temp/LCL.postgres.converted';

DELETE FROM lasco_cme_cat;
COPY lasco_cme_cat (time_start,pa_central,pa_width,linear_speed,speed2_init,speed2_final,speed2_20r,acceleration,pa_measure) FROM '/var/www/html/sec/temp/LCC.postgres.converted';
	
DELETE FROM hessi_flare;
COPY hessi_flare (flare_number,time_start,time_peak,time_end,duration,count_sec_peak,total_count,energy_kev,x_arcsec,y_arcsec,radial_arcsec,nar) FROM '/var/www/html/sec/temp/HEF.postgres.converted';

DELETE FROM noaa_proton_event;
COPY noaa_proton_event (time_start,time_peak,nar,latitude,longitude,proton_flux,assoc_cme,assoc_flare_pk,xray_class,optical_class) FROM '/var/www/html/sec/temp/NPE.postgres.converted';

DELETE FROM sidc_sunspot_number;
COPY sidc_sunspot_number (time_start,time_end,ssn) FROM '/var/www/html/sec/temp/SSN.postgres.converted';

DELETE FROM drao_10cm_flux;
COPY drao_10cm_flux (time_start,time_end,sfu_observed,sfu_adjusted,sfu_series_d) FROM '/var/www/html/sec/temp/SFM.postgres.converted';

DELETE FROM dsd_list;
COPY dsd_list (time_start,radio_10cm,sesc_ssn,ss_area,new_regions,stan_smf,xray_bkg,c_flares,m_flares,x_flares,opts_flares,opt1_flares,opt2_flares,opt3_flares) FROM '/var/www/html/sec/temp/DSD.postgres.converted';

DELETE FROM yohkoh_flare_list;
COPY yohkoh_flare_list (time_start,time_peak,time_end,nar,latitude,longitude,xray_class,optical_class,hxt_lo,hxt_m1,hxt_m2,hxt_hi,rem,yoh_event) FROM '/var/www/html/sec/temp/YFC.postgres.converted';

DELETE FROM srs_list;
COPY srs_list (time_start,nar,latitude,longitude,long_carr,area,zurich_class,p_value,c_value,long_extent,n_spots,mag_class,region_type) FROM '/var/www/html/sec/temp/SRS.postgres.converted';

DELETE FROM bas_magnetic_storms;
COPY bas_magnetic_storms (time_start,time_peak,time_end,dst,hduration) FROM '/var/www/html/sec/temp/BMS.postgres.converted';

DELETE FROM goes_xray_flare;
COPY goes_xray_flare (time_start,time_peak,time_end,latitude,longitude,optical_class,xray_class,nar,ntime_start,ntime_end,long_carr) FROM '/var/www/html/sec/temp/GOES.postgres.converted';
COPY goes_xray_flare (time_start,time_peak,time_end,latitude,longitude,optical_class,xray_class,nar,ntime_start,ntime_end,long_carr) FROM '/var/www/html/sec/temp/latest_gev5.csv';

DELETE FROM soho_camp;
COPY soho_camp (sohoc_num,sohoc_name,sohoc_type,sohoc_obj,sohoc_coord,sohoc_part,sohoc_comm,time_start,time_end) FROM '/var/www/html/sec/temp/SOHO.postgres.converted';

DELETE FROM kso_flare;
COPY kso_flare (time_start,time_start_m,time_peak,time_peak_m,time_end,time_end_m,latitude,longitude,long_carr,optical_class) FROM '/var/www/html/sec/temp/KSO.postgres.converted';

DELETE FROM eit_list;
COPY eit_list (previmg_time, img_time, quality,latitude,longitude,speed_planeofsky, speed_proj, pa_central, description,time_start) FROM '/var/www/html/sec/temp/EIT.postgres.converted';

DELETE FROM yohkoh_sxt_trace_list;
COPY yohkoh_sxt_trace_list (link,time_start_sxt,time_end_sxt,xray_class,n_img,x_arcsec_sxt,y_arcsec_sxt,time_start,time_end,time_sxt_trace,wl_dom,x_arcsec,y_arcsec,n171,n195,n284,n1600,n1216,nwl) FROM '/var/www/html/sec/temp/YST.postgres.converted';

DELETE FROM halpha_flares_event;
COPY halpha_flares_event (time_start,time_peak,time_end,latitude,longitude,optical_class,xray_class,nar,long_carr) FROM '/var/www/html/sec/temp/HA.postgres.converted';
