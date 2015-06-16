BEGIN;
DELETE FROM stereoa_impactplastic_sir;
copy stereoa_impactplastic_sir from '/var/www/hec/STEREO-A_sir.csv'  with DELIMITER as '|' null as ' ' csv header;
COMMIT;
