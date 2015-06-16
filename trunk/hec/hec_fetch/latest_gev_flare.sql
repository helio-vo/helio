BEGIN;
DELETE FROM latest_gev_flare where time_start >= '2012-01-01';
SELECT setval('latest_gev_flare_latest_gev_flare_id_seq', (SELECT MAX(latest_gev_flare_id) FROM latest_gev_flare)+1);
COPY latest_gev_flare (time_start,time_peak,time_end,nar,latitude,longitude,xray_class,optical_class) FROM '/var/www/hec/temp/LATEST_GEV_FLARE.postgres.converted';
COMMIT;
