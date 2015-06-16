BEGIN;
DELETE FROM noaa_proton_event;
SELECT SETVAL((SELECT pg_get_serial_sequence('noaa_proton_event','npe_id')), 1, false);
COPY noaa_proton_event (time_start,time_peak,nar,latitude,longitude,proton_flux,assoc_cme,assoc_flare_pk,xray_class,optical_class) FROM '/var/www//hec/temp/NPE.postgres.converted';
COMMIT;
