BEGIN;
DELETE FROM srs_list where time_start >= '2012-01-01';
SELECT setval('srs_list_srs_id_seq', (SELECT MAX(srs_id) FROM srs_list)+1);
COPY srs_list (time_start,nar,latitude,longitude,long_carr,area,zurich_class,p_value,c_value,long_extent,n_spots,mag_class,region_type) FROM '/var/www//hec/temp/SRS2.postgres.converted';
COMMIT;

BEGIN;
DELETE FROM sgas_event where time_start >= '2011-12-31';
SELECT setval('sgas_event_sgs_id_seq', (SELECT MAX(sgs_id) FROM sgas_event)+1);
COPY sgas_event (time_start,time_peak,time_end,nar,latitude,longitude,xray_class,optical_class,radio_245mhz,radio_10cm,radio_sweep_ii,radio_sweep_iv,swf,ntime_start,ntime_end,long_carr) FROM '/var/www//hec/temp/SGS2.postgres.converted';
COMMIT;


