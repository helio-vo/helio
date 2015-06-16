BEGIN;
DELETE FROM goes_xray_flare where time_start >= '2012-01-01 00:00:00';
SELECT setval('goes_xray_flare_goes_id_seq', (select max(goes_id) from goes_xray_flare)+1); 
COPY goes_xray_flare (time_start,time_peak,time_end,latitude,longitude,optical_class,xray_class,nar,ntime_start,ntime_end,long_carr) FROM '/var/www/hec/temp/GOES.postgres.converted';
COMMIT;

