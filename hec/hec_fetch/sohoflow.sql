BEGIN;
SELECT setval('cactus_soho_flow_hec_id_seq', (SELECT MAX(hec_id) FROM cactus_soho_flow)+1);
COPY cactus_soho_flow (sat_id,r_hci,long_hci,lat_hci,time_start,duration,pa,pa_width,v,dv,v_min,v_max,flag_halo,cme_number,event_detail) FROM '/var/www/hec/soho-2010_flow.txt' with delimiter '|' csv header;
COMMIT;
