BEGIN;
DELETE FROM stereoa_het_sep;
copy stereoa_het_sep(no,sat_id,time_start,time_end,flux_max,fluence,comment) from '/var/www/hec_fetch/mssl_fetch/stereoa_het_sep.csv'  with DELIMITER as '|' csv header;
COMMIT;

BEGIN;
DELETE FROM stereob_het_sep;
copy stereob_het_sep(no,sat_id,time_start,time_end,flux_max,fluence,comment) from '/var/www/hec_fetch/mssl_fetch/stereob_het_sep.csv'  with DELIMITER as '|' csv header;
COMMIT;

