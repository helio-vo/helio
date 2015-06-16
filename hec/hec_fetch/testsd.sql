BEGIN;
DELETE FROM sdemon_flare where time_start >= date_trunc('year', now());
copy sdemon_flare from '/var/www/hec_fetch/mssl_fetch/sdemon_flare.csv'  with DELIMITER as '|' csv header;
COMMIT;
