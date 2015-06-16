BEGIN;
DELETE FROM dsd_list;
SELECT setval('dsd_list_dsd_id_seq', (SELECT MAX(dsd_id) FROM dsd_list)+1);
COPY dsd_list (time_start,radio_10cm,sesc_ssn,ss_area,new_regions,stan_smf,xray_bkg,c_flares,m_flares,x_flares,opts_flares,opt1_flares,opt2_flares,opt3_flares) FROM '/var/www//hec/temp/DSD.postgres.converted';
COMMIT;
