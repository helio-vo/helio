BEGIN;
delete from mslrad_sep;
COPY mslrad_sep(time_start, time_peak_flare, xray_class, length_spiral) FROM '/var/www/hec/mslrad_sep.txt' with delimiter ',';
COMMIT;
