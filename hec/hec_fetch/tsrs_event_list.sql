
BEGIN;
DROP TABLE tsrs_event_list;
COMMIT;
CREATE TABLE tsrs_event_list (
        tsrs_event_list_id SERIAL,
        time_start TIMESTAMP,
        time_peak TIMESTAMP,
        time_end TIMESTAMP,
        freq INTEGER,
        cpp INTEGER,
        ps VARCHAR(32),
        sfu_max FLOAT,
        radio_class VARCHAR(64),
        rem VARCHAR(1024),
        file VARCHAR(1024),
PRIMARY KEY (tsrs_event_list_id));

GRANT SELECT ON TABLE tsrs_event_list TO apache;

BEGIN;
DELETE FROM tsrs_event_list;
COPY tsrs_event_list (time_start,time_peak,time_end,freq,cpp,ps,sfu_max,radio_class,rem,file) FROM '/var/www//hec/temp/TSRS.postgres.converted';
COMMIT;

INSERT INTO sec_catalogue VALUES(29,'tsrs_event_list','TSRS event list','event','inactive','http://radiosun.oats.inaf.it',3,'ivory','Trieste Solar Radio System event list');
-- tsrs_event_list:
INSERT INTO sec_cat_attr VALUES(29,     1       );
INSERT INTO sec_cat_attr VALUES(29,     2       );
INSERT INTO sec_cat_attr VALUES(29,     3       );
