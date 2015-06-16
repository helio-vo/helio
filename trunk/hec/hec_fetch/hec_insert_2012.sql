-- EGSO - SFEC server
-- creation script
-- by M.Jurcev,A.Santin last rev. 27-jan-2007


BEGIN;
DELETE FROM hessi_flare where time_start >= '2012-01-01';
SELECT setval('hessi_flare_hef_id_seq', (SELECT MAX(hef_id) FROM hessi_flare)+1);
COPY hessi_flare (flare_number,time_start,time_peak,time_end,duration,count_sec_peak,total_count,energy_kev,x_arcsec,y_arcsec,radial_arcsec,nar) FROM '/var/www//hec/temp/HEF.postgres.converted';
COMMIT;

BEGIN;
DELETE FROM sidc_sunspot_number where time_start >= '2012-01-01';
SELECT setval('sidc_sunspot_number_ssn_id_seq', (SELECT MAX(ssn_id) FROM sidc_sunspot_number)+1);
COPY sidc_sunspot_number (time_start,time_end,ssn) FROM '/var/www//hec/temp/SSN.postgres.converted';
COMMIT;

BEGIN;
DELETE FROM dsd_list;
DELETE FROM dsd_list where time_start >= '2012-01-01';
SELECT setval('dsd_list_dsd_id_seq', (SELECT MAX(dsd_id) FROM dsd_list)+1);
COPY dsd_list (time_start,radio_10cm,sesc_ssn,ss_area,new_regions,stan_smf,xray_bkg,c_flares,m_flares,x_flares,opts_flares,opt1_flares,opt2_flares,opt3_flares) FROM '/var/www//hec/temp/DSD.postgres.converted';
COMMIT;


BEGIN;
DELETE FROM srs_list where time_start >= '2012-01-01';
SELECT setval('srs_list_srs_id_seq', (SELECT MAX(srs_id) FROM srs_list)+1);
COPY srs_list (time_start,nar,latitude,longitude,long_carr,area,zurich_class,p_value,c_value,long_extent,n_spots,mag_class,region_type) FROM '/var/www//hec/temp/SRS2.postgres.converted';
COMMIT;

BEGIN;
DELETE FROM sgas_event where time_start >= '2011-12-31';
SELECT setval('sgas_event_sgs_id_seq', (SELECT MAX(sgs_id) FROM sgas_event)+1);
COPY sgas_event (time_start,time_peak,time_end,nar,latitude,longitude,xray_class,optical_class,radio_245mhz,radio_10cm,radio_sweep_ii,radio_sweep_iv,swf,ntime_start,ntime_end,long_carr) FROM '/var/www//hec/temp/SGS2.postgres.converted';
COMMIT;

BEGIN;
DELETE FROM kso_flare where time_start >= '2012-01-01';
SELECT setval('kso_flare_kso_id_seq', (SELECT MAX(kso_id) FROM kso_flare)+1);
COPY kso_flare (time_start,time_start_m,time_peak,time_peak_m,time_end,time_end_m,latitude,longitude,long_carr,optical_class) FROM '/var/www//hec/temp/KSO.postgres.converted';
COMMIT;

--KMB: Hmmm little more of a struggle to quickly fix the npe to just do 2012, so it will load everything for now.
BEGIN;
DELETE FROM noaa_proton_event;
SELECT SETVAL((SELECT pg_get_serial_sequence('noaa_proton_event','npe_id')), 1, false);
COPY noaa_proton_event (time_start,time_peak,nar,latitude,longitude,proton_flux,assoc_cme,assoc_flare_pk,xray_class,optical_class) FROM '/var/www//hec/temp/NPE.postgres.converted';
COMMIT;

BEGIN;
DELETE FROM cactus_soho_cme where time_start >= '2012-01-01';
SELECT setval('cactus_soho_cme_hec_id_seq', (SELECT MAX(hec_id) FROM cactus_soho_cme)+1);
COPY cactus_soho_cme (sat_id,r_hci,long_hci,lat_hci,time_start,duration,pa,pa_width,v,dv,v_min,v_max,flag_halo,cme_number,event_detail) FROM '/var/www/hec/soho-2012_cme.txt' with delimiter '|' csv header;
COMMIT;

BEGIN;
DELETE FROM cactus_soho_flow where time_start >= '2012-01-01';
SELECT setval('cactus_soho_flow_hec_id_seq', (SELECT MAX(hec_id) FROM cactus_soho_flow)+1);
COPY cactus_soho_flow (sat_id,r_hci,long_hci,lat_hci,time_start,duration,pa,pa_width,v,dv,v_min,v_max,flag_halo,cme_number,event_detail) FROM '/var/www/hec/soho-2012_flow.txt' with delimiter '|' csv header;
COMMIT;

BEGIN;
DELETE FROM cactus_stereoa_cme where time_start >= '2012-01-01';
SELECT setval('cactus_stereoa_cme_hec_id_seq', (SELECT MAX(hec_id) FROM cactus_stereoa_cme)+1);
COPY cactus_stereoa_cme (sat_id,r_hci,long_hci,lat_hci,time_start,duration,pa,pa_width,v,dv,v_min,v_max,flag_halo,cme_number,event_detail) FROM '/var/www/hec/sta-2012_cme.txt' with delimiter '|' csv header;
COMMIT;

BEGIN;
DELETE FROM cactus_stereoa_flow where time_start >= '2012-01-01';
SELECT setval('cactus_stereoa_flow_hec_id_seq', (SELECT MAX(hec_id) FROM cactus_stereoa_flow)+1);
COPY cactus_stereoa_flow (sat_id,r_hci,long_hci,lat_hci,time_start,duration,pa,pa_width,v,dv,v_min,v_max,flag_halo,cme_number,event_detail) FROM '/var/www/hec/sta-2012_flow.txt' with delimiter '|' csv header;
COMMIT;

BEGIN;
DELETE FROM cactus_stereob_cme where time_start >= '2012-01-01';
SELECT setval('cactus_stereob_cme_hec_id_seq', (SELECT MAX(hec_id) FROM cactus_stereob_cme)+1);
COPY cactus_stereob_cme (sat_id,r_hci,long_hci,lat_hci,time_start,duration,pa,pa_width,v,dv,v_min,v_max,flag_halo,cme_number,event_detail) FROM '/var/www/hec/stb-2012_cme.txt' with delimiter '|' csv header;
COMMIT;

BEGIN;
DELETE FROM cactus_stereob_flow where time_start >= '2012-01-01';
SELECT setval('cactus_stereob_flow_hec_id_seq', (SELECT MAX(hec_id) FROM cactus_stereob_flow)+1);
COPY cactus_stereob_flow (sat_id,r_hci,long_hci,lat_hci,time_start,duration,pa,pa_width,v,dv,v_min,v_max,flag_halo,cme_number,event_detail) FROM '/var/www/hec/stb-2012_flow.txt' with delimiter '|' csv header;
COMMIT;

BEGIN;
DELETE FROM seeds_soho where time_start >= '2012-01-01';
SELECT setval('seeds_soho_hec_id_seq', (SELECT MAX(hec_id) FROM seeds_soho)+1);
COPY seeds_soho (sat_id,r_hci,long_hci,lat_hci,time_start,pa,pa_width,v,accel,cme_number,n_frame,seeds_id) FROM '/var/www/hec/seeds_soho_2012.txt' with delimiter '|' null as '  '  csv header;
update seeds_soho set event_detail='http://spaceweather.gmu.edu/seeds/mkmovie.php?cme='||trim(seeds_id)||'&frame='||n_frame;
update seeds_soho set event_detail='http://spaceweather.gmu.edu/seeds/mkmovie_ql.php?cme='||trim(seeds_id)||'&frame='||n_frame where position('Q' in cme_number) > 1;
COMMIT;

BEGIN;
DELETE FROM seeds_sta where time_start >= '2012-01-01';
SELECT setval('seeds_sta_hec_id_seq', (SELECT MAX(hec_id) FROM seeds_sta)+1);
COPY seeds_sta (sat_id,r_hci,long_hci,lat_hci,time_start,pa,pa_width,v,accel,cme_number,n_frame,seeds_id) FROM '/var/www/hec/seeds_sta_2012.txt' with delimiter '|' null as '  ' csv header;
update seeds_sta set event_detail='http://spaceweather.gmu.edu/seeds/mkmovie.php?cme='||trim(seeds_id)||'&frame='||n_frame||'&cor2=a';
COMMIT;

BEGIN;
DELETE FROM seeds_stb where time_start >= '2012-01-01';
SELECT setval('seeds_stb_hec_id_seq', (SELECT MAX(hec_id) FROM seeds_stb)+1);
COPY seeds_stb (sat_id,r_hci,long_hci,lat_hci,time_start,pa,pa_width,v,accel,cme_number,n_frame,seeds_id) FROM '/var/www/hec/seeds_stb_2012.txt' with delimiter '|' null as '  ' csv header;
update seeds_stb set event_detail='http://spaceweather.gmu.edu/seeds/mkmovie.php?cme='||trim(seeds_id)||'&frame='||n_frame||'&cor2=b';
COMMIT;

BEGIN;
DELETE FROM gevloc_flares where time_start >= '2012-01-01';
SELECT setval('gevloc_flares_gevloc_flares_id_seq', (SELECT MAX(gevloc_flares_id) FROM gevloc_flares)+1);
COPY gevloc_flares (time_start,xray_class,loc) FROM '/var/www/hec/temp/GEVLOC_FLARES.postgres.converted';
COMMIT;

BEGIN;
DELETE FROM latest_gev_flare where time_start >= '2012-01-01';
SELECT setval('latest_gev_flare_latest_gev_flare_id_seq', (SELECT MAX(latest_gev_flare_id) FROM latest_gev_flare)+1);
COPY latest_gev_flare (time_start,time_peak,time_end,nar,latitude,longitude,xray_class,optical_class) FROM '/var/www/hec/temp/LATEST_GEV_FLARE.postgres.converted';
COMMIT;
