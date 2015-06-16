BEGIN;
DROP TABLE istp_solar_wind_cat;
COMMIT;
CREATE TABLE istp_solar_wind_cat (
        istp_solar_wind_cat_id SERIAL,
        time_start TIMESTAMP,
        time_end TIMESTAMP,
        category VARCHAR(32),
        spacecraft VARCHAR(32),
        rem VARCHAR(1024),
PRIMARY KEY (istp_solar_wind_cat_id));

GRANT SELECT ON TABLE istp_solar_wind_cat TO apache;

BEGIN;
DELETE FROM istp_solar_wind_cat;
COPY istp_solar_wind_cat (time_start,time_end,category,spacecraft,rem) FROM '/var/www//hec/temp/ISTP_SOLAR_WIND_CATALOG.postgres.converted';
COMMIT;
