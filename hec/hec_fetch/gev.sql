BEGIN;
DELETE FROM gevloc_sxr_flare where time_start >= '2013-06-01';
COPY gevloc_sxr_flare FROM '/var/www/hec/gevloc1_list_201306z.csv' with delimiter '|'  null as ' ' csv header;
COMMIT;
