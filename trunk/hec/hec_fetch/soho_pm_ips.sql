
BEGIN;
DROP TABLE soho_pm_ips;
COMMIT;
CREATE TABLE soho_pm_ips (
        soho_pm_ips_id SERIAL,
        time_start TIMESTAMP,
        doy_start INTEGER,
--        zone VARCHAR(8),
        zone INTEGER,
        rem VARCHAR(1024),
PRIMARY KEY (soho_pm_ips_id));

GRANT SELECT ON TABLE soho_pm_ips TO apache;

BEGIN;
DELETE FROM soho_pm_ips;
COPY soho_pm_ips (time_start,doy_start,zone,rem) FROM '/var/www//hec/temp/SOHO_PM_IPS.postgres.converted';
COMMIT;
