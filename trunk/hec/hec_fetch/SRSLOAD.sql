BEGIN;
DELETE FROM srs_list where time_start >= '2011-01-01';
SELECT setval('srs_list_srs_id_seq', (SELECT MAX(srs_id) FROM srs_list)+1);
COPY srs_list (time_start,nar,latitude,longitude,long_carr,area,zurich_class,p_value,c_value,long_extent,n_spots,mag_class,region_type) FROM '/var/www//hec/temp/NEWSRS.txt';
COMMIT;
