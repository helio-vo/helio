BEGIN;
DELETE FROM hfc_level3_wind_waves;
copy hfc_level3_wind_waves from '/var/www/hec/hfc_l3_t3_wind_waves.txt'  with DELIMITER as '|' null as ' ' csv header;
COMMIT;
