--nmdb_gle_list.sql


DROP TABLE nmdb_gle_list;

BEGIN;
DROP TABLE nmdb_gle_list;
COMMIT;
CREATE TABLE nmdb_gle_list (
        hec_id      SERIAL,
        num  INTEGER,
        time_start TIMESTAMP,
        time_end TIMESTAMP,
        time_onset TIMESTAMP,
        comments VARCHAR(1024),
        url VARCHAR(1024),
    PRIMARY KEY (hec_id));

GRANT SELECT ON TABLE nmdb_gle_list TO apache;

BEGIN;
DELETE FROM nmdb_gle_list;
SELECT SETVAL((SELECT pg_get_serial_sequence('nmdb_gle_list','hec_id')), 1, false);
COPY nmdb_gle_list (num,time_start,time_end,time_onset,comments,url) FROM '/var/www/hec/temp/nmdb_gle_list.postgres.converted';
COMMIT;

/*
sql.query.time.constraint.nmdb_gle_list='[:kwenddate:]'>=time_start AND '[:kwstartdate:]'<=time_end
sql.query.instr.constraint.nmdb_gle_list=
sql.query.coordinates.constraint.nmdb_gle_list=
sql.query.orderby.constraint.nmdb_gle_list=
sql.query.maxrecord.constraint.nmdb_gle_list=20000
sql.columnnames.nmdb_gle_list=HEC_id::num::time_start::time_end::time_onset::comments::url
sql.columndesc.nmdb_gle_list=Event Identification number (HEC internal number).::num::time_start::time_end::time_onset::comments::url
sql.columnucd.nmdb_gle_list= 
sql.columnutypes.nmdb_gle_list= 
votable.query.info.nmdb_gle_list=TABLE_NAME,nmdb_gle_list
*/
