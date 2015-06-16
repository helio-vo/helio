BEGIN;
DELETE FROM stereoa_impactplastic_sir;
SELECT setval('stereoa_impactplastic_sir_hec_id_seq',1); 
COPY stereoa_impactplastic_sir (comp_id,id,flag,spacecraft,time_start,time_end,time_pt_max,pt_max,b_max,np_max,v_min,v_max) FROM '/var/www/hec/manLoaded/stereoa_impactplastic_sir.txt' with delimiter '|'  null as ' ' csv header;
COMMIT;

BEGIN;
DELETE FROM stereob_impactplastic_sir;
SELECT setval('stereob_impactplastic_sir_hec_id_seq',1); 
COPY stereob_impactplastic_sir (comp_id,id,flag,spacecraft,time_start,time_end,time_pt_max,pt_max,b_max,np_max,v_min,v_max) FROM '/var/www/hec/manLoaded/stereob_impactplastic_sir.txt' with delimiter '|' null as ' ' csv header;
COMMIT;

BEGIN;
DELETE FROM stereoa_impactplastic_shock;
SELECT setval('stereoa_impactplastic_shock_hec_id_seq',1);
COPY stereoa_impactplastic_shock (id,time_start,mag_ratio,norm_angle,beta,mach_no,data_avail,f_r_shock,comment) FROM '/var/www/hec/manLoaded/stereoa_impactplastic_shock.txt' with delimiter '|' null as ' ' csv header;
COMMIT;


BEGIN;
DELETE FROM stereob_impactplastic_shock;
SELECT setval('stereob_impactplastic_shock_hec_id_seq',1);
COPY stereob_impactplastic_shock (id,time_start,mag_ratio,norm_angle,beta,mach_no,data_avail,f_r_shock,comment) FROM '/var/www/hec/manLoaded/stereob_impactplastic_shock.txt' with delimiter '|' null as ' ' csv header;
COMMIT;

BEGIN;
DELETE FROM stereo_impactplastic_icme;
SELECT setval('stereo_impactplastic_icme_hec_id_seq',1);
COPY stereo_impactplastic_icme (id,flag,spacecraft,time_start,time_mag_obstacle_start,time_end,pt_max,pt_max_sheath,b_max,b_max_sheath,v_max,v_max_sheath,delta_v,groups,comment) FROM '/var/www/hec/manLoaded/stereo_impactplastic_icme.txt' with delimiter '|' null as ' ' csv header;
COMMIT;

BEGIN;
DELETE FROM gradual_sep;
SELECT setval('gradual_sep_hec_id_seq',1);
COPY gradual_sep (id , time_start , time_peak , duration , flux_peak , fluence , coverage) FROM '/var/www/hec/manLoaded/gradual_sep_event.txt' with delimiter '|' null as ' ' csv header;
COMMIT;
