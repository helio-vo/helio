--cme_inner_heliosphere.sql

--cme_number|time_start          |v_cme|cme_type|xray_class|optical_class|lat_hg|long_hg|long_carr|time_ips_1au        |time_cme_1au        |v_icme|time_transit_1au|alpha|v_1au_predict|time_transit_1au_predict|flag_cme_interaction
--1         |1998-10-15T10:04:00Z|262  |H       |          |flm          |22    |1      | 89.18   |1998-10-18T19:28:00Z|1998-10-19T05:00:00Z|425   |90.9            |0.14 |522          |84.4                    |
--2         |1999-07-07T19:31:00Z|547  |PH      |C1.1      |SF |-26| 69|255.70                    |1999-07-12T01:20:00Z|NaN                 |NaN   |NaN             |-0.35|367          |89.3| 

DROP VIEW cme_inner_heliosphere;

BEGIN;
DROP TABLE cme_inner_heliosphere;
COMMIT;
CREATE TABLE cme_inner_heliosphere (
        hec_id      SERIAL,
        cme_number INTEGER,
        time_start  TIMESTAMP,
        v_cme INTEGER,
        cme_type VARCHAR(8),
        xray_class VARCHAR(8),
        optical_class VARCHAR(8),
        lat_hg   REAL,
        long_hg  REAL,
        long_carr  REAL,
        time_ips_1au TIMESTAMP,
        time_cme_1au TIMESTAMP,
        v_icme INTEGER,
        time_transit_1au REAL,
        alpha REAL,
        v_1au_predict REAL,
        time_transit_1au_predict REAL,
        flag_cme_interaction VARCHAR(8),
    PRIMARY KEY (hec_id));

GRANT SELECT ON TABLE cme_inner_heliosphere TO apache;

BEGIN;
DELETE FROM cme_inner_heliosphere;
SELECT SETVAL((SELECT pg_get_serial_sequence('cme_inner_heliosphere','hec_id')), 1, false);
COPY cme_inner_heliosphere (cme_number,time_start,v_cme,cme_type,xray_class,optical_class,lat_hg,long_hg,long_carr,time_ips_1au,time_cme_1au,v_icme,time_transit_1au,alpha,v_1au_predict,time_transit_1au_predict,flag_cme_interaction) FROM '/var/www/hec/temp/cme_inner_heliosphere.postgres.converted';
COMMIT;

/*
sql.query.time.constraint.cme_inner_heliosphere='[:kwenddate:]'>=time_start AND '[:kwstartdate:]'<=time_start
sql.query.instr.constraint.cme_inner_heliosphere=
sql.query.coordinates.constraint.cme_inner_heliosphere= 
sql.query.orderby.constraint.cme_inner_heliosphere=
sql.query.maxrecord.constraint.cme_inner_heliosphere=20000
sql.columnnames.cme_inner_heliosphere=HEC_id::cme_number::time_start::v_cme::cme_type::xray_class::optical_class::lat_hg::long_hg::long_carr::time_ips_1au::time_cme_1au::v_icme::time_transit_1au::alpha::v_1au_predict::time_transit_1au_predict::flag_cme_interaction
sql.columndesc.cme_inner_heliosphere=Event Identification number (HEC internal number).::CME number.::The time that the CME was detected by SOHO LASCO.::The initial speed of the CME derived from the 'height-time' plot from the LASCO images.::'"H' indicates a halo event and 'PH' indicates a partial halo event."::Importance of associated flare at X-ray wavelengths -- the peak flux measured at Earth in the 0.1 to 0.8 nm range in units of W m^-2.  (See the 'X-ray Class' in the HEC glossary for the specification of this field)::Average optical importance of associated  flare -- a measurement of flare size and brilliance.  (See the 'Optical Class' in the HEC glossary for the specification of this field). 'flm' indicates a filament eruption.::Heliographic latitude of associated flare.::Heliographic longitude of associated flare.::Carrington longitude of associated flare.::The time of arrival of the interplanetary shock at 1AU.::The time of arrival of the interplanetary CME (i.e. ejecta and/or magnetic cloud).::The ICME speed at 1AU.::The observed CME transit time from the Sun to 1AU.::The speed-distance slope from interplanetary scintillation measurements.::The predicted velocity of the ICME at 1 AU from the LASCO and interplanetary scintillation measurements.::The predicted CME transit time from the Sun to 1AU from the LASCO and interplanetary scintillation measurements.::'"Y' indicates that the CME interacts with other CMEs."
sql.columnucd.cme_inner_heliosphere=::::::::::::::::::::::::::::::::::  
sql.columnutypes.cme_inner_heliosphere=::::::::::::::::::::::::::::::::::  
votable.query.info.cme_inner_heliosphere=TABLE_NAME,cme_inner_heliosphere


*/
