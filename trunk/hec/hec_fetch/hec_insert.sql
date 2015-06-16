-- EGSO - SFEC server
-- creation script
-- by M.Jurcev,A.Santin last rev. 27-jan-2007


BEGIN;
DELETE FROM hessi_flare where time_start >= date_trunc('year', now());
SELECT setval('hessi_flare_hef_id_seq', (SELECT MAX(hef_id) FROM hessi_flare)+1);
COPY hessi_flare (flare_number,time_start,time_peak,time_end,duration,count_sec_peak,total_count,energy_kev,x_arcsec,y_arcsec,radial_arcsec,nar) FROM '/var/www/hec_fetch/temp/HEF.postgres.converted';
COMMIT;

BEGIN;
DELETE FROM sidc_sunspot_number where time_start >= date_trunc('year', now());
SELECT setval('sidc_sunspot_number_ssn_id_seq', (SELECT MAX(ssn_id) FROM sidc_sunspot_number)+1);
COPY sidc_sunspot_number (time_start,time_end,ssn) FROM '/var/www/hec_fetch/temp/SSN.postgres.converted';
COMMIT;

BEGIN;
DELETE FROM dsd_list where time_start >= date_trunc('year', now());
SELECT setval('dsd_list_dsd_id_seq', (SELECT MAX(dsd_id) FROM dsd_list)+1);
COPY dsd_list (time_start,radio_10cm,sesc_ssn,ss_area,new_regions,stan_smf,xray_bkg,c_flares,m_flares,x_flares,opts_flares,opt1_flares,opt2_flares,opt3_flares) FROM '/var/www/hec_fetch/temp/DSD.postgres.converted';
COMMIT;


BEGIN;
DELETE FROM srs_list where time_start >= date_trunc('year', now());
SELECT setval('srs_list_srs_id_seq', (SELECT MAX(srs_id) FROM srs_list)+1);
COPY srs_list (time_start,nar,latitude,longitude,long_carr,area,zurich_class,p_value,c_value,long_extent,n_spots,mag_class,region_type) FROM '/var/www/hec_fetch/temp/SRS2.postgres.converted';
update srs_list set solarmonitor_link='http://www.solarmonitor.org/region.php?date='||to_char(time_start,'YYYYMMDD')||'&region='||nar where longitude >= -90;
COMMIT;

BEGIN;
DELETE FROM sgas_event where time_start >= date_trunc('year', now());
SELECT setval('sgas_event_sgs_id_seq', (SELECT MAX(sgs_id) FROM sgas_event)+1);
COPY sgas_event (time_start,time_peak,time_end,nar,latitude,longitude,xray_class,optical_class,radio_245mhz,radio_10cm,radio_sweep_ii,radio_sweep_iv,swf,ntime_start,ntime_end,long_carr) FROM '/var/www/hec_fetch/temp/SGS2.postgres.converted';
COMMIT;

BEGIN;
DELETE FROM kso_flare where time_start >= date_trunc('year', now());
SELECT setval('kso_flare_kso_id_seq', (SELECT MAX(kso_id) FROM kso_flare)+1);
COPY kso_flare (time_start,time_start_m,time_peak,time_peak_m,time_end,time_end_m,latitude,longitude,long_carr,optical_class) FROM '/var/www/hec_fetch/temp/KSO.postgres.converted';
delete from kso_flare where time_start is null;
COMMIT;

--KMB: Hmmm little more of a struggle to quickly fix the npe to just do 2012, so it will load everything for now.
BEGIN;
DELETE FROM noaa_proton_event;
SELECT SETVAL((SELECT pg_get_serial_sequence('noaa_proton_event','npe_id')), 1, false);
COPY noaa_proton_event (time_start,time_peak,nar,latitude,longitude,proton_flux,assoc_cme,assoc_flare_pk,xray_class,optical_class) FROM '/var/www/hec_fetch/temp/NPE.postgres.converted';
COMMIT;

BEGIN;
--DELETE FROM cactus_soho_cme where time_start >= date_trunc('year', now());
DELETE FROM cactus_soho_cme where time_start >= '2014-01-01 00:00:00';
SELECT setval('cactus_soho_cme_hec_id_seq', (SELECT MAX(hec_id) FROM cactus_soho_cme)+1);
COPY cactus_soho_cme (sat_id,r_hci,long_hci,lat_hci,time_start,duration,pa,pa_width,v,dv,v_min,v_max,flag_halo,cme_number,event_detail) FROM '/var/www/hec_fetch/mssl_fetch/soho-2014_cme.txt' with delimiter '|' csv header;
COMMIT;

BEGIN;
--DELETE FROM cactus_soho_flow where time_start >= date_trunc('year', now());
DELETE FROM cactus_soho_flow where time_start >= '2014-01-01 00:00:00';
SELECT setval('cactus_soho_flow_hec_id_seq', (SELECT MAX(hec_id) FROM cactus_soho_flow)+1);
COPY cactus_soho_flow (sat_id,r_hci,long_hci,lat_hci,time_start,duration,pa,pa_width,v,dv,v_min,v_max,flag_halo,cme_number,event_detail) FROM '/var/www/hec_fetch/mssl_fetch/soho-2014_flow.txt' with delimiter '|' csv header;
COMMIT;

BEGIN;
--DELETE FROM cactus_stereoa_cme where time_start >= date_trunc('year', now());
DELETE FROM cactus_stereoa_cme where time_start >= '2014-01-01 00:00:00';
SELECT setval('cactus_stereoa_cme_hec_id_seq', (SELECT MAX(hec_id) FROM cactus_stereoa_cme)+1);
COPY cactus_stereoa_cme (sat_id,r_hci,long_hci,lat_hci,time_start,duration,pa,pa_width,v,dv,v_min,v_max,flag_halo,cme_number,event_detail) FROM '/var/www/hec_fetch/mssl_fetch/sta-2014_cme.txt' with delimiter '|' csv header;
COMMIT;

BEGIN;
--DELETE FROM cactus_stereoa_flow where time_start >= date_trunc('year', now());
DELETE FROM cactus_stereoa_flow where time_start >= '2014-01-01 00:00:00';
SELECT setval('cactus_stereoa_flow_hec_id_seq', (SELECT MAX(hec_id) FROM cactus_stereoa_flow)+1);
COPY cactus_stereoa_flow (sat_id,r_hci,long_hci,lat_hci,time_start,duration,pa,pa_width,v,dv,v_min,v_max,flag_halo,cme_number,event_detail) FROM '/var/www/hec_fetch/mssl_fetch/sta-2014_flow.txt' with delimiter '|' csv header;
COMMIT;

BEGIN;
--DELETE FROM cactus_stereob_cme where time_start >= date_trunc('year', now());
DELETE FROM cactus_stereob_cme where time_start >= '2014-01-01 00:00:00';
SELECT setval('cactus_stereob_cme_hec_id_seq', (SELECT MAX(hec_id) FROM cactus_stereob_cme)+1);
COPY cactus_stereob_cme (sat_id,r_hci,long_hci,lat_hci,time_start,duration,pa,pa_width,v,dv,v_min,v_max,flag_halo,cme_number,event_detail) FROM '/var/www/hec_fetch/mssl_fetch/stb-2014_cme.txt' with delimiter '|' csv header;
COMMIT;

BEGIN;
--DELETE FROM cactus_stereob_flow where time_start >= date_trunc('year', now());
DELETE FROM cactus_stereob_flow where time_start >= '2014-01-01 00:00:00';
SELECT setval('cactus_stereob_flow_hec_id_seq', (SELECT MAX(hec_id) FROM cactus_stereob_flow)+1);
COPY cactus_stereob_flow (sat_id,r_hci,long_hci,lat_hci,time_start,duration,pa,pa_width,v,dv,v_min,v_max,flag_halo,cme_number,event_detail) FROM '/var/www/hec_fetch/mssl_fetch/stb-2014_flow.txt' with delimiter '|' csv header;
COMMIT;

BEGIN;
--DELETE FROM seeds_soho where time_start >= date_trunc('year', now());
DELETE FROM seeds_soho where time_start >= '2014-01-01 00:00:00';
SELECT setval('seeds_soho_hec_id_seq', (SELECT MAX(hec_id) FROM seeds_soho)+1);
COPY seeds_soho (sat_id,r_hci,long_hci,lat_hci,time_start,pa,pa_width,v,accel,cme_number,n_frame,seeds_id) FROM '/var/www/hec_fetch/mssl_fetch/seeds_soho_2014.txt' with delimiter '|' null as ''  csv header;
update seeds_soho set event_detail='http://spaceweather.gmu.edu/seeds/mkmovie.php?cme='||trim(seeds_id)||'&frame='||n_frame;
update seeds_soho set event_detail='http://spaceweather.gmu.edu/seeds/mkmovie_ql.php?cme='||trim(seeds_id)||'&frame='||n_frame where position('Q' in cme_number) > 1;
COMMIT;

BEGIN;
--DELETE FROM seeds_sta where time_start >= date_trunc('year', now());
DELETE FROM seeds_sta where time_start >= '2014-01-01 00:00:00';
SELECT setval('seeds_sta_hec_id_seq', (SELECT MAX(hec_id) FROM seeds_sta)+1);
COPY seeds_sta (sat_id,r_hci,long_hci,lat_hci,time_start,pa,pa_width,v,accel,cme_number,n_frame,seeds_id) FROM '/var/www/hec_fetch/mssl_fetch/seeds_sta_2014.txt' with delimiter '|' null as '' csv header;
update seeds_sta set event_detail='http://spaceweather.gmu.edu/seeds/mkmovie.php?cme='||trim(seeds_id)||'&frame='||n_frame||'&cor2=a';
COMMIT;

BEGIN;
--DELETE FROM seeds_stb where time_start >= date_trunc('year', now());
DELETE FROM seeds_stb where time_start >= '2014-01-01 00:00:00';
SELECT setval('seeds_stb_hec_id_seq', (SELECT MAX(hec_id) FROM seeds_stb)+1);
COPY seeds_stb (sat_id,r_hci,long_hci,lat_hci,time_start,pa,pa_width,v,accel,cme_number,n_frame,seeds_id) FROM '/var/www/hec_fetch/mssl_fetch/seeds_stb_2014.txt' with delimiter '|' null as '' csv header;
update seeds_stb set event_detail='http://spaceweather.gmu.edu/seeds/mkmovie.php?cme='||trim(seeds_id)||'&frame='||n_frame||'&cor2=b';
COMMIT;

BEGIN;
DELETE FROM gevloc_flares where time_start >= date_trunc('year', now());
SELECT setval('gevloc_flares_gevloc_flares_id_seq', (SELECT MAX(gevloc_flares_id) FROM gevloc_flares)+1);
COPY gevloc_flares (time_start,xray_class,loc) FROM '/var/www/hec_fetch/temp/GEVLOC_FLARES.postgres.converted';
COMMIT;

BEGIN;
DELETE FROM lasco_cme_cat;
SELECT SETVAL((SELECT pg_get_serial_sequence('lasco_cme_cat','lcc_id')), 1, false);
COPY lasco_cme_cat (time_start,time_end,pa_central,pa_width,linear_speed,speed2_init,speed2_final,speed2_20r,acceleration,pa_measure) FROM '/var/www/hec_fetch/temp/LCC.postgres.converted';
COMMIT;

BEGIN;
--DELETE FROM latest_gev_flare where time_start >= date_trunc('year', now());
DELETE FROM latest_gev_flare where time_start >= '2013-06-01';
SELECT setval('latest_gev_flare_latest_gev_flare_id_seq', (SELECT MAX(latest_gev_flare_id) FROM latest_gev_flare)+1);
COPY latest_gev_flare (time_start,time_peak,time_end,nar,latitude,longitude,xray_class,optical_class) FROM '/var/www/hec_fetch/temp/LATEST_GEV_FLARE.postgres.converted';
COMMIT;

BEGIN;
DELETE FROM gevloc_sxr_flare where time_start >= '2014-06-01';
COPY gevloc_sxr_flare FROM '/var/www/hec_fetch/mssl_fetch/gevloc1_list_201406z.csv' with delimiter '|'  null as ' ' csv header;
COMMIT;

BEGIN;
DELETE FROM soho_pm_ips;
copy soho_pm_ips(time_start, zone, time_start_flare,xray_class,time_travel,url_pm,url_flare,comment) from '/var/www/hec_fetch/mssl_fetch/soho_pm_shock.csv'  with DELIMITER as '|' null as '' csv header;
COMMIT;

BEGIN;
DELETE FROM stereoa_impactplastic_shock;
copy stereoa_impactplastic_shock(id,spacecraft,time_start,mag_ratio,norm_angle,beta,mach_no,data_avail,source,f_r_shock,comment) from '/var/www/hec_fetch/mssl_fetch/STEREO-A_shock.csv'  with DELIMITER as '|' csv header;
COMMIT;



BEGIN;
DELETE FROM stereoa_het_sep;
copy stereoa_het_sep(no,sat_id,time_start,time_end,flux_max,fluence,comment) from '/var/www/hec_fetch/mssl_fetch/stereoa_het_sep.csv'  with DELIMITER as '|' csv header;
COMMIT;

BEGIN;
DELETE FROM stereob_het_sep;
copy stereob_het_sep(no,sat_id,time_start,time_end,flux_max,fluence,comment) from '/var/www/hec_fetch/mssl_fetch/stereob_het_sep.csv'  with DELIMITER as '|' csv header;
COMMIT;

BEGIN;
DELETE FROM sdemon_dimming where time_start >= date_trunc('year', now());
copy sdemon_dimming from '/var/www/hec_fetch/mssl_fetch/sdemon_dimming.csv'  with DELIMITER as '|' csv header;
COMMIT;

BEGIN;
DELETE FROM sdemon_flare where time_start >= date_trunc('year', now());
copy sdemon_flare from '/var/www/hec_fetch/mssl_fetch/sdemon_flare.csv'  with DELIMITER as '|' csv header;
COMMIT;

BEGIN;
DELETE FROM stereob_impactplastic_shock;
copy stereob_impactplastic_shock(id,spacecraft,time_start,mag_ratio,norm_angle,beta,mach_no,data_avail,source,f_r_shock,comment) from '/var/www/hec_fetch/mssl_fetch/STEREO-B_shock.csv'  with DELIMITER as '|' csv header;
COMMIT;

BEGIN;
DELETE FROM goes_xray_flare where time_start >= '2013-06-01';
copy goes_xray_flare(time_start, time_peak,time_end, nar, latitude, longitude,long_carr,xray_class,optical_class, xray_class_query) from '/var/www/hec_fetch/mssl_fetch/latest_gev_full.txt'  with DELIMITER as ';' null as ' ';
COMMIT;

BEGIN;
DELETE FROM stereoa_impactplastic_icme;
--copy stereoa_impactplastic_icme from '/var/www/hec_fetch/mssl_fetch/STEREO-A_icme.csv'  with DELIMITER as '|' null as ' ' csv header;
copy stereoa_impactplastic_icme(id,flag,spacecraft,time_start,time_mag_obstacle_start,time_end,pt_max,pt_max_sheath,b_max,b_max_sheath,v_max,v_max_sheath,delta_v,groups,comment) from '/var/www/hec_fetch/mssl_fetch/STEREO-A_icme.csv'  with DELIMITER as '|'  csv header;
COMMIT;

BEGIN;
DELETE FROM stereob_impactplastic_icme;
--copy stereob_impactplastic_icme(id,flag,spacecraft,time_start,time_mag_obstacle_start,time_end,pt_max,pt_max_sheath,b_max,b_max_sheath,v_max,v_max_sheath,delta_v,groups,comment) from '/var/www/hec_fetch/mssl_fetch/STEREO-B_icme.csv'  with DELIMITER as '|' null as ' ' csv header;
copy stereob_impactplastic_icme(id,flag,spacecraft,time_start,time_mag_obstacle_start,time_end,pt_max,pt_max_sheath,b_max,b_max_sheath,v_max,v_max_sheath,delta_v,groups,comment) from '/var/www/hec_fetch/mssl_fetch/STEREO-B_icme.csv'  with DELIMITER as '|' null as '' csv header;
COMMIT;

BEGIN;
DELETE FROM stereoa_impactplastic_sir;
copy stereoa_impactplastic_sir(comp_id,id,flag,spacecraft,time_start,time_end,time_pt_max,pt_max,b_max,np_max,v_min,v_max) from '/var/www/hec_fetch/mssl_fetch/STEREO-A_sir.csv'  with DELIMITER as '|' csv header;
COMMIT;

BEGIN;
DELETE FROM stereob_impactplastic_sir;
copy stereob_impactplastic_sir(comp_id,id,flag,spacecraft,time_start,time_end,time_pt_max,pt_max,b_max,np_max,v_min,v_max) from '/var/www/hec_fetch/mssl_fetch/STEREO-B_sir.csv'  with DELIMITER as '|' csv header;
COMMIT;

BEGIN;
DELETE FROM hfc_level3_wind_waves;
copy hfc_level3_wind_waves from '/var/www/hec_fetch/hfc_l3_t3_wind_waves.txt'  with DELIMITER as '|' null as ' ' csv header;
COMMIT;

BEGIN;
DELETE FROM corimp_soho_linear where time_start >= '2015-01-15';
COPY corimp_soho_linear FROM '/var/www/hec_fetch/mssl_fetch/corimp_linear.csv' with delimiter '|' csv header;
COMMIT;

BEGIN;
DELETE FROM corimp_soho_quadratic where time_start >= '2015-01-15';
COPY corimp_soho_quadratic FROM '/var/www/hec_fetch/mssl_fetch/corimp_quadratic.csv' with delimiter '|' csv header;
COMMIT;

BEGIN;
DELETE FROM corimp_soho_savgol where time_start >= '2015-01-15';
COPY corimp_soho_savgol FROM '/var/www/hec_fetch/mssl_fetch/corimp_savgol.csv' with delimiter '|' csv header;
COMMIT;

BEGIN;
DELETE FROM fermi_gbm_flare where time_start >= '2015-01-01';
COPY fermi_gbm_flare FROM '/var/www/hec_fetch/mssl_fetch/fermi_gbmflare.csv' with delimiter '|' csv header;
COMMIT;

BEGIN;
DELETE FROM fermi_lat_event;
COPY fermi_lat_event FROM '/var/www/hec_fetch/mssl_fetch/fermi_latevent.csv' with delimiter '|' csv header;
COMMIT;

BEGIN;
update gevloc_sxr_flare set xray_class=trim(xray_class);
COMMIT;

BEGIN;
update goes_xray_flare set xray_class=trim(xray_class);
COMMIT;
