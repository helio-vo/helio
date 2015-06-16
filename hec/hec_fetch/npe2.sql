BEGIN;
DELETE FROM noaa_proton_event;
COPY noaa_proton_event (time_start,time_peak,nar,latitude,longitude,proton_flux,assoc_cme,assoc_flare_pk,xray_class,optical_class) FROM '/var/www//hec/temp/NPE2.postgres.converted';
COMMIT;
