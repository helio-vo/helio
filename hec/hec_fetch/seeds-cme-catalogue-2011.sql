--seeds-cme-catalogue-2011.sql

--phil's sorting
--HEC_id::cme_number::time_start::n_frame::pa::pa_width::v::accel::event_detail

--bob's sorting
--time_start         ,pa   ,pa_width,v    ,accel  ,cme_number  ,n_frame,event_detail
--2011-01-02T06:24:05|  273|  35    |  342|  -16.7|  201101-001|  11   |  http://spaceweather.gmu.edu/seeds/mkmovie.php?cme=20110102.062405.w035.v0342.p273&frame=11

DROP VIEW seeds_cme;

BEGIN;
DROP TABLE seeds_cme;
COMMIT;
CREATE TABLE seeds_cme (
        hec_id      SERIAL,
        cme_number  VARCHAR(32),
        time_start  TIMESTAMP,
        n_frame     INTEGER,
        pa          REAL,
        pa_width    REAL,
        v           REAL,
        accel       REAL,
        event_detail VARCHAR(4096),
    PRIMARY KEY (hec_id));

GRANT SELECT ON TABLE seeds_cme TO apache;

BEGIN;
DELETE FROM seeds_cme;
SELECT SETVAL((SELECT pg_get_serial_sequence('seeds_cme','hec_id')), 1, false);
COPY seeds_cme (time_start,pa,pa_width,v,accel,cme_number ,n_frame,event_detail) FROM '/var/www/hec/temp/seeds-cme-catalogue-2011.postgres.converted';
COMMIT;

/*
sql.query.time.constraint.seeds_cme=time_start>='[:kwstartdate:]' AND time_start<='[:kwenddate:]'
sql.query.instr.constraint.seeds_cme=
sql.query.coordinates.constraint.seeds_cme=
sql.query.orderby.constraint.seeds_cme=
sql.query.maxrecord.constraint.seeds_cme=20000
sql.columnnames.seeds_cme=HEC_id::cme_number::time_start::n_frame::pa::pa_width::v::accel::event_detail
sql.columndesc.seeds_cme=Event Identification number (HEC internal number).::SEEDS monthly count prefixed with 'yyymm-'::start time of the CME::number of frames in the movie::Position angle (degrees, counterclockwise from North)::Angular width of the CME (degrees)::velocity (linear fit)::2nd order polynomial fit to the Height-time::url of the movie
sql.columnucd.seeds_cme=meta.record::meta.number::time.start::meta.number::pos.posAng::pos.posAng;phys.angSize::phys.veloc::phys.acceleration::meta.ref.url
sql.columnutypes.seeds_cme=::::helio:time.time_start::::helio:location.pa::helio:location.pa_width::helio:cme.velocity.v::helio:cme.acceleration::
votable.query.info.seeds_cme=TABLE_NAME,seeds_cme
*/
