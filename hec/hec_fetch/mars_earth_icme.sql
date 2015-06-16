--mars_earth_icme.sql


BEGIN;
DROP TABLE mars_earth_icme;
COMMIT;
CREATE TABLE mars_earth_icme (
        hec_id       SERIAL,
        event_id     INTEGER,
        time_start_mars   TIMESTAMP,
        p_dyn_mars   REAL,
        counts_max_mars   INTEGER,
        time_start_earth TIMESTAMP,
        p_dyn_earth  REAL,
        v_max_earth  INTEGER,
        density_max_earth  REAL,
        counts_max_goes REAL,
        long_hci_sepn REAL,
    PRIMARY KEY (hec_id));

GRANT SELECT ON TABLE mars_earth_icme TO apache;

BEGIN;
DELETE FROM mars_earth_icme;
COPY mars_earth_icme (event_id,time_start_mars,p_dyn_mars,counts_max_mars,time_start_earth,p_dyn_earth,v_max_earth,density_max_earth,counts_max_goes,long_hci_sepn) FROM '/var/www/hec/temp/mars_earth_icme.postgres.converted';
COMMIT;

/*
sql.query.time.constraint.mars_earth_icme=start_time>='[:kwstartdate:]' AND start_time<='[:kwenddate:]'
sql.query.instr.constraint.mars_earth_icme=
sql.query.coordinates.constraint.mars_earth_icme= 
sql.query.orderby.constraint.mars_earth_icme=
sql.query.maxrecord.constraint.mars_earth_icme=20000
sql.columnnames.mars_earth_icme=hec_id::event_id::time_start_mars::p_dyn_mars::counts_max_mars::time_start_earth::p_dyn_earth::v_max_earth::density_max_earth::counts_max_goes::long_hci_sepn
sql.columndesc.mars_earth_icme=Event identification number (HEC internal number)::event_id::time_start_mars::p_dyn_mars::counts_max_mars::time_start_earth::p_dyn_earth::v_max_earth::density_max_earth::counts_max_goes::long_hci_sepn
sql.columnucd.mars_earth_icme= :: :: :: :: :: :: :: :: :: :: 
sql.columnutypes.mars_earth_icme=

*/
