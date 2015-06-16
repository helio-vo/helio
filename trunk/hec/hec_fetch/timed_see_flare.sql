--timed_see_flare.sql

--time_start|time_peak|time_end|doy|lat_hg|long_hg|long_carr|xray_class|nar|nevent|see_xps_index|see_egs_index|obs_time_offset|url_ftp_file
--2002-03-09T18:31:00Z|2002-03-09T18:56:00Z|2002-03-09T19:39:00Z|068|-79|-9|258.27|M2.6|9866|530 |2.06|0.99|213|ftp://laspftp.colorado.edu/pub/SEE_Data/level3a/2002/see__L3A_2002068_010_01.ncdf
--2002-03-30T22:19:00Z|2002-03-30T22:26:00Z|2002-03-30T22:34:00Z|089|NaN|NaN|NaN  |M1.0|NaN |5830|1.02|1.10|-281|ftp://laspftp.colorado.edu/pub/SEE_Data/level3a/2002/see__L3A_2002089_010_01.ncdf


BEGIN;
DROP TABLE timed_see_flare;
COMMIT;
CREATE TABLE timed_see_flare (
        hec_id     SERIAL,
        time_start TIMESTAMP,
        time_peak  TIMESTAMP,
        time_end   TIMESTAMP,
        lat_hg     REAL,
        long_hg    REAL,
        long_carr  REAL,
        xray_class VARCHAR(8),
        nar        INTEGER,
        nevent     INTEGER,
        see_xps_index   REAL,
        see_egs_index   REAL,
        obs_time_offset INTEGER,
        url_ftp_file    VARCHAR(2048),
    PRIMARY KEY (hec_id));

GRANT SELECT ON TABLE timed_see_flare TO apache;

BEGIN;
DELETE FROM timed_see_flare;
SELECT SETVAL((SELECT pg_get_serial_sequence('timed_see_flare','hec_id')), 1, false);
COPY timed_see_flare (time_start,time_peak,time_end,lat_hg,long_hg,long_carr,xray_class,nar,nevent,see_xps_index,see_egs_index,obs_time_offset,url_ftp_file) FROM '/var/www/hec/temp/timed_see_flare.postgres.converted';
COMMIT;

/*
sql.query.time.constraint.timed_see_flare=time_start>='[:kwstartdate:]' AND time_end<='[:kwenddate:]'
sql.query.instr.constraint.timed_see_flare=
sql.query.coordinates.constraint.timed_see_flare= 
sql.query.orderby.constraint.timed_see_flare=
sql.query.maxrecord.constraint.timed_see_flare=20000
sql.columnnames.timed_see_flare=hec_id::time_start::time_peak::time_end::lat_hg::long_hg::long_carr::xray_class::nar::nevent::see_xps_index::see_egs_index::obs_time_offset::url_ftp_file
sql.columndesc.timed_see_flare=Event identification number (HEC internal number)::time_start::time_peak::time_end::lat_hg::long_hg::long_carr::xray_class::nar::nevent::see_xps_index::see_egs_index::obs_time_offset::url_ftp_file
sql.columnucd.timed_see_flare= 
sql.columnutypes.timed_see_flare= 

*/
