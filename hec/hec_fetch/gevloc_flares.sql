BEGIN;
DELETE FROM gevloc_flares where time_start >= '2012-01-01';
SELECT setval('gevloc_flares_gevloc_flares_id_seq', (SELECT MAX(gevloc_flares_id) FROM gevloc_flares)+1);
COPY gevloc_flares (time_start,xray_class,loc) FROM '/var/www/hec/temp/GEVLOC_FLARES.postgres.converted';
COMMIT;
