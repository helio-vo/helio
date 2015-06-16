BEGIN;
DELETE FROM apstar_list;
SET datestyle = 'ISO,YMD';
COPY apstar_list(time_start,time_peak,time_end,hduration,apstar_max,apstar_ave,apstar_sum) FROM '/var/www/hec_fetch/apstar_csv.txt'  with DELIMITER as ';' null as ' ' csv header;
COMMIT;
