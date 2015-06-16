--icme_earth_cycle23.sql

--time_start|time_start_icme|time_end_icme|flag_a_w|time_offset_start_sig|flag_toff_start_sig|time_offset_end_sig|flag_toff_end_sig|time_offset_start_mc|flag_toff_start_mc|time_offset_end_mc|flag_toff_end_mc|bidir_flow_e|bidir_flow_ion|qual_time_est|flag_weak_event|v_incr_sw|flag_upstream_shock|v_mean_sw|v_max_sw|b_mean|flag_mag_cloud|flag_mc_list|dst_min|flag_dst|v_1au|time_lasco|flag_halo
--time_start          |time_start_icme     |time_end_icme       |flag_a_w|time_offset_start_sig|flag_toff_start_sig|time_offset_end_sig|flag_toff_end_sig|time_offset_start_mc|flag_toff_start_mc|time_offset_end_mc|flag_toff_end_mc|bidir_flow_e|bidir_flow_ion|qual_time_est|flag_weak_event|v_incr_sw|flag_upstream_shock|v_mean_sw|v_max_sw|b_mean|flag_mag_cloud|flag_mc_list|dst_min|flag_dst|v_1au|time_lasco|flag_halo
--1996-05-27T15:00:00Z|1996-05-27T15:00:00Z|1996-05-29T03:00:00Z|        |NaN                  |nd                 |NaN                |nd               |0                   |                  |+4                |                |N           |nd            |2            |               |0        |                   |370      |400     |9     |2             |            |-33    |        |NaN  |NaN       | 
--1997-05-15T01:59:00Z|1997-05-15T09:00:00Z|1997-05-16T00:00:00Z|        |NaN                  |nd                 |NaN                |nd               |0                   |                  |0                 |                |N           |Y             |1            |               |150      |S                  |450      |480     |21    |2             |            |-115   |        |616  |1997-05-12T05:30:00Z|H

DROP VIEW icme_earth_cycle23;

BEGIN;
DROP TABLE icme_earth_cycle23;
COMMIT;
CREATE TABLE icme_earth_cycle23 (
        hec_id      SERIAL,
        time_start  TIMESTAMP,
        time_start_icme   TIMESTAMP,
        time_end_icme    TIMESTAMP,
        flag_a_w VARCHAR(4),
        time_offset_start_sig INTEGER,
        flag_toff_start_sig VARCHAR(4),
        time_offset_end_sig INTEGER,
        flag_toff_end_sig VARCHAR(4),
        time_offset_start_mc INTEGER,
        flag_toff_start_mc VARCHAR(4),
        time_offset_end_mc INTEGER,
        flag_toff_end_mc VARCHAR(4),
        bidir_flow_e VARCHAR(4),
        bidir_flow_ion VARCHAR(4),
        qual_time_est INTEGER,
        flag_weak_event VARCHAR(4),
        v_incr_sw INTEGER,
        flag_upstream_shock VARCHAR(4),
        v_mean_sw INTEGER,
        v_max_sw INTEGER,
        b_mean INTEGER,
        flag_mag_cloud INTEGER,
        flag_mc_list VARCHAR(4),
        dst_min INTEGER,
        flag_dst VARCHAR(4),
        v_1au INTEGER,
        time_lasco TIMESTAMP,
        flag_halo VARCHAR(4),        
    PRIMARY KEY (hec_id));

GRANT SELECT ON TABLE icme_earth_cycle23 TO apache;

BEGIN;
DELETE FROM icme_earth_cycle23;
SELECT SETVAL((SELECT pg_get_serial_sequence('icme_earth_cycle23','hec_id')), 1, false);
COPY icme_earth_cycle23 (time_start,time_start_icme,time_end_icme,flag_a_w,time_offset_start_sig,flag_toff_start_sig,time_offset_end_sig,flag_toff_end_sig,time_offset_start_mc,flag_toff_start_mc,time_offset_end_mc,flag_toff_end_mc,bidir_flow_e,bidir_flow_ion,qual_time_est,flag_weak_event,v_incr_sw,flag_upstream_shock,v_mean_sw,v_max_sw,b_mean,flag_mag_cloud,flag_mc_list,dst_min,flag_dst,v_1au,time_lasco,flag_halo) FROM '/var/www/hec/temp/icme_earth_cycle23.postgres.converted';
COMMIT;

/*
sql.query.time.constraint.icme_earth_cycle23='[:kwenddate:]'>=time_start_icme AND '[:kwstartdate:]'<=time_end_icme
sql.query.instr.constraint.icme_earth_cycle23=
sql.query.coordinates.constraint.icme_earth_cycle23= 
sql.query.orderby.constraint.icme_earth_cycle23=
sql.query.maxrecord.constraint.icme_earth_cycle23=20000
sql.columnnames.icme_earth_cycle23=HEC_id::time_start::time_start_icme::time_end_icme::flag_a_w::time_offset_start_sig::flag_toff_start_sig::time_offset_end_sig::flag_toff_end_sig::time_offset_start_mc::flag_toff_start_mc::time_offset_end_mc::flag_toff_end_mc::bidir_flow_e::bidir_flow_ion::qual_time_est::flag_weak_event::v_incr_sw::flag_upstream_shock::v_mean_sw::v_max_sw::b_mean::flag_mag_cloud::flag_mc_list::dst_min::flag_dst::v_1au::time_lasco::flag_halo
sql.columndesc.icme_earth_cycle23=Event Identification number (HEC internal number).::The start time of the ICME-related “disturbance”. For a fast ICME, this is given by the arrival of the ICME-driven shock. In this case, the time given is typically that of the geomagnetic storm sudden commencement (SC) that frequently accompanies a shock reaching the Earth’s magnetosphere since this provides a spacecraft-independent arrival time that is also appropriate for comparison with phenomena at the Earth.::The start time of the ICME. The ICME start time is inferred primarily from plasma and magnetic field data and given to the nearest hour.::The end time of the ICME. The ICME end time is inferred primarily from plasma and magnetic field data and given to the nearest hour.::If a sudden commencement (SC) is reported this flag is not set. If an SC is not reported, the time of shock passage at ACE (indicated by (A)) is given. If there are no data at ACE, or a shock is not reported at that spacecraft, the shock time at WIND (W) is listed if a shock is reported. If no shock is reported for a slower ICME, a wave-like feature or developing shock may be evident in the plasma and field data. The time of such a feature is given in the table to the nearest hour.::Estimated offset of the start of the compositional/charge state signatures in the vicinity of the ICME relative to the “ICME” start time.::“ns” indicates that there is no compositional/charge state signature while “nc” indicates that there is no change or there is no clear compositional/charge state change. An absence of data is indicated by “nd ”. Close agreement between the ICME and composition/ charge state boundaries (to within an hour or so, limited by the resolution of the SWICS data) is indicated by a “zero hour” offset. Different compositional/charge state changes may not coincide exactly though frequently there is a consensus. Typically the O7/O6 ratio is used as a reference.::Estimated offset of the end of the compositional/charge state signatures in the vicinity of the ICME relative to the “ICME” end time.::“ns” indicates that there is no compositional/charge state signature while “nc” indicates that there is no change or there is no clear compositional/charge state change. An absence of data is indicated by “nd ”. Close agreement between the ICME and composition/ charge state boundaries (to within an hour or so, limited by the resolution of the SWICS data) is indicated by a “zero hour” offset. Different compositional/charge state changes may not coincide exactly though frequently there is a consensus. Typically the O7/O6 ratio is used as a reference.::Time offset (in hours) of the magnetic leading boundary relative to the ICME boundary based primarily on the WIND magnetic cloud list. Additional events identified by Huttunen et al. (2005) in 1996 – 2003 are also included.::'"nd' indicates an absence of data."::Time offset (in hours) of the magnetic trailing boundary relative to the ICME boundary based primarily on the WIND magnetic cloud list. Additional events identified by Huttunen et al. (2005) in 1996 – 2003 are also included.::'"nd' indicates an absence of data. '2' indicates that two magnetic clouds are identified in the interval of interest. The offsets are then estimated from the leading edge of the first magnetic cloud and the trailing edge of the second."::Indicates whether or not bidirectional suprathermal electrons were observed within the ICME (Y/N = yes/no), based on an assessment of the SWEPAM electron pitchangle plots from the ACE Science Center. For events before the launch of ACE, and events where SWEPAM observations are dominated by solar energetic particles, the 260 eV electron pitch-angle plots from the WIND 3DP instrument (http://sprg.ssl.berkeley.edu/wind3dp/) were examined. In a few cases, indicated by “SEP”, it was not possible to assess the presence of BDEs in either data set because of the presence of solar energetic particles. Overall, 67% of the ICMEs (205/308), where an assessment can be made, data show evidence of BDEs.::Indicates the presence of bidirectional energetic ion flows (BIFs) observed by the IMP 8 GME (for the ICME commencing on 31 October 2003, the BIFs are reported by Malandraki et al. (2005)). Here, “. nd ” indicates that either there are no data for a given ICME, the available data cover a limited region of the ICME such that it is not possible to assess whether any bidirectional flows were present, particle counts are insufficient to be able to assess the particle flows, or IMP 8 was inside the bow shock. “Y” or “N” indicate that there is, or is no, evidence (in the available data, which may not extend throughout the ICME interval) of bidirectional flows. Overall, energetic ion flows can be examined for 114 ICMEs, of which 66 (58%) show evidence of BIFs.::indicates the overall “quality” of the ICME boundary time estimates, where ‘1’ is the most reliable.::“W” indicates a marginal event with weak ICME signatures.::The increase in solar wind speed at the upstream disturbance (to the nearest 10 km s−1) estimated from 1-hour averaged data.::An ‘S’ indicates that a shock reported in the ACE or Kasper shock lists contributed to the speed increase (which may be larger than the actual speed increase at the shock if for example, the solar wind speed increased further following the shock). Of the 322 ICMEs, 163 (51%) have identified upstream shocks. Note that in a few cases (e.g., 4 May 1998), there is a large increase in solar wind speed but no shock is reported. Typically, the speed transition is too gradual to be associated with a true shock.::The average ICME speed based on the plasma/field ICME intervals.::The maximum post-shock solar wind speed (i.e., between the shock/disturbance and plasma/field ICME trailing edge),::The average magnetic field strength within the plasma/field ICME, to the nearest 1 nT.::A ‘2’ indicates that the ICME includes a magnetic cloud reported on the WIND magnetic cloud list. In a few cases, an indicated magnetic cloud is not reported on these lists, but the magnetic field characteristics of the ICME are assessed to be consistent with those for a magnetic cloud (e.g., enhanced intensity >10 nT, smooth rotation through a large angle, low proton temperatures, Klein and Burlaga, 1982). A ‘1’ indicates that there is evidence of a rotation in the magnetic fielddirection, but overall, the magnetic field characteristics do not meet those of a magnetic cloud. Events with no magnetic cloud-like magnetic field features are indicated by ‘0’.::'H' indicates a magnetic cloud reported by Huttunen et al. (2005).::The minimum geomagnetic Dst.::‘P’ indicates that the value is provisional, and ‘Q’ that the value is obtained from the “Real-time (Quicklook)” Dst index provided by the World Data Center for Geomagnetism, Kyoto University (http://swdcwww.kugi.kyoto-u.ac.jp/). Otherwise, final values are given. The period considered for each event extends from the disturbance to the trailing edge of the ICME signatures or slightly beyond if a storm driven by the trailing regions of an ICME reaches peak intensity just after ICME passage. A null value indicates that strong geomagnetic activity is already in progress and there is no further intensification associated with the ICME.::The shock transit speed to 1 AU if the probably associated solar event can be identified. If so details are given in the following fields.::The event time generally corresponding to the first observation of the related CME by the LASCO coronagraphs on the SOHO spacecraft.::'"H' denotes that this is reported as a halo CME in the online CME catalogue (http://cdaw.gsfc.nasa.gov/CME_list/). 'dg' indicates a gap in LASCO observations that encompasses the likely time of the associated solar event. SOHO EIT movies and other data (e.g.
sql.columnucd.icme_earth_cycle23=::::::::::::::::::::::::::::::::::::::::::::::::::::::::  
sql.columnutypes.icme_earth_cycle23=::::::::::::::::::::::::::::::::::::::::::::::::::::::::  
votable.query.info.icme_earth_cycle23=TABLE_NAME,icme_earth_cycle23


*/
