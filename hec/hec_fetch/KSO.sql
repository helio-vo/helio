BEGIN;
DELETE FROM kso_flare where time_start >= date_trunc('year', now());
SELECT setval('kso_flare_kso_id_seq', (SELECT MAX(kso_id) FROM kso_flare)+1);
COPY kso_flare (time_start,time_start_m,time_peak,time_peak_m,time_end,time_end_m,latitude,longitude,long_carr,optical_class) FROM '/var/www//hec/temp/KSO.postgres.converted';
COMMIT;
