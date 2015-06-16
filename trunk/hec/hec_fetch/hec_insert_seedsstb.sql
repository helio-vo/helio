BEGIN;
--DELETE FROM seeds_stb where time_start >= date_trunc('year', now());
DELETE FROM seeds_stb where time_start >= '2013-01-01 00:00:00';
SELECT setval('seeds_stb_hec_id_seq', (SELECT MAX(hec_id) FROM seeds_stb)+1);
COPY seeds_stb (sat_id,r_hci,long_hci,lat_hci,time_start,pa,pa_width,v,accel,cme_number,n_frame,seeds_id) FROM '/var/www/hec/seeds_stb_2006-2012.txt' with delimiter '|' null as '' csv header;
COPY seeds_stb (sat_id,r_hci,long_hci,lat_hci,time_start,pa,pa_width,v,accel,cme_number,n_frame,seeds_id) FROM '/var/www/hec/seeds_stb_2013.txt' with delimiter '|' null as '' csv header;
update seeds_stb set event_detail='http://spaceweather.gmu.edu/seeds/mkmovie.php?cme='||trim(seeds_id)||'&frame='||n_frame||'&cor2=b';
COMMIT;
