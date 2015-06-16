BEGIN;
DELETE FROM stereoa_impactplastic_icme;
copy stereoa_impactplastic_icme from '/var/www/hec/STEREO-A_icme.csv'  with DELIMITER as '|'  csv header;
COMMIT;
