BEGIN;
DELETE FROM cactus_stereob_cme where time_start >= '2012-01-01';
SELECT setval('cactus_stereob_cme_hec_id_seq', (SELECT MAX(hec_id) FROM cactus_stereob_cme)+1);
COPY cactus_stereob_cme (sat_id,r_hci,long_hci,lat_hci,time_start,duration,pa,pa_width,v,dv,v_min,v_max,flag_halo,cme_number,event_detail) FROM '/var/www/hec/stb-2012_cme.txt' with delimiter '|' csv header;
COMMIT;
