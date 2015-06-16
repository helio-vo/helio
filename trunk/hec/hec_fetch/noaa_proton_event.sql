DROP TABLE noaa_proton_event;
CREATE TABLE noaa_proton_event (
        npe_id                  SERIAL,
        time_start              TIMESTAMP,
        time_peak               TIMESTAMP,
        nar                             INTEGER,
        latitude                FLOAT,
        longitude               FLOAT,
        proton_flux             FLOAT,
        assoc_cme               VARCHAR(64),
        assoc_flare_pk  TIMESTAMP,
        xray_class              VARCHAR(16),
        optical_class   VARCHAR(32),
        PRIMARY KEY (npe_id)
);

BEGIN;
DELETE FROM noaa_proton_event;
SELECT SETVAL((SELECT pg_get_serial_sequence('noaa_proton_event','npe_id')), 1, false);
COPY noaa_proton_event (time_start,time_peak,nar,latitude,longitude,proton_flux,assoc_cme,assoc_flare_pk,xray_class,optical_class) FROM '/var/www//hec/temp/NPE.postgres.converted';
COMMIT;
