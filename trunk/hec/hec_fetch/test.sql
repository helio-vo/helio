BEGIN;
DELETE FROM stereoa_impactplastic_shock;
copy stereoa_impactplastic_shock(id,spacecraft,time_start,mag_ratio,norm_angle,beta,mach_no,data_avail,source,f_r_shock,comment) from '/var/www/hec/STEREO-A_shock.csv'  with DELIMITER as '|' csv header;
COMMIT;

BEGIN;
DELETE FROM stereob_impactplastic_shock;
copy stereob_impactplastic_shock(id,spacecraft,time_start,mag_ratio,norm_angle,beta,mach_no,data_avail,source,f_r_shock,comment) from '/var/www/hec/STEREO-B_shock.csv'  with DELIMITER as '|' csv header;
COMMIT;


BEGIN;
DELETE FROM stereoa_impactplastic_icme;
--copy stereoa_impactplastic_icme from '/var/www/hec/STEREO-A_icme.csv'  with DELIMITER as '|' null as ' ' csv header;
copy stereoa_impactplastic_icme(id,flag,spacecraft,time_start,time_mag_obstacle_start,time_end,pt_max,pt_max_sheath,b_max,b_max_sheath,v_max,v_max_sheath,delta_v,groups,comment) from '/var/www/hec/STEREO-A_icme.csv'  with DELIMITER as '|'  csv header;
COMMIT;

BEGIN;
DELETE FROM stereob_impactplastic_icme;
copy stereob_impactplastic_icme(id,flag,spacecraft,time_start,time_mag_obstacle_start,time_end,pt_max,pt_max_sheath,b_max,b_max_sheath,v_max,v_max_sheath,delta_v,groups,comment) from '/var/www/hec/STEREO-B_icme.csv'  with DELIMITER as '|' csv header;
COMMIT;

BEGIN;
DELETE FROM stereoa_impactplastic_sir;
copy stereoa_impactplastic_sir(comp_id,id,flag,spacecraft,time_start,time_end,time_pt_max,pt_max,b_max,np_max,v_min,v_max) from '/var/www/hec/STEREO-A_sir.csv'  with DELIMITER as '|' csv header;
COMMIT;

BEGIN;
DELETE FROM stereob_impactplastic_sir;
copy stereob_impactplastic_sir(comp_id,id,flag,spacecraft,time_start,time_end,time_pt_max,pt_max,b_max,np_max,v_min,v_max) from '/var/www/hec/STEREO-B_sir.csv'  with DELIMITER as '|' csv header;
COMMIT;
