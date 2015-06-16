psql -d hec -c"drop view noaa_active_region_summary";
psql -d hec -c"alter table srs_list add column solarmonitor_link varchar(200)"
psql -d hec -c"create view noaa_active_region_summary as SELECT srs_list.srs_id AS hec_id, srs_list.time_start, srs_list.nar, srs_list.latitude AS lat_hg, srs_list.longitude AS long_hg, srs_list.long_carr, srs_list.area, srs_list.zurich_class, srs_list.p_value, srs_list.c_value, srs_list.long_extent AS dlong_hg, srs_list.n_spots, srs_list.mag_class, srs_list.region_type, srs_list.solarmonitor_link from srs_list order by srs_id";
psql -d hec -c"grant all on noaa_active_region_summary to apache";
psql -d hec -c"update srs_list set solarmonitor_link='http://www.solarmonitor.org/region.php?date='||to_char(time_start,'YYYYMMDD')||'&region='||nar where longitude > 0;";
