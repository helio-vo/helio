BEGIN;
DELETE FROM drao_10cm_flux;
COPY drao_10cm_flux (time_start,time_end,sfu_observed,sfu_adjusted,sfu_series_d) FROM '/var/www//hec/temp/SFM.postgres.converted';
COMMIT;
