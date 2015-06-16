psql -d hec -c"create table stereoa_het_sep(no integer,sat_id varchar(50), time_start timestamp, time_end timestamp, flux_max real, fluence integer, comment varchar(300));";
psql -d hec -c"create table stereob_het_sep(no integer,sat_id varchar(50), time_start timestamp, time_end timestamp, flux_max real, fluence integer, comment varchar(300));";
psql -d hec -c"grant select on table stereoa_het_sep to apache";
psql -d hec -c"grant select on table stereob_het_sep to apache";
wget "http://msslkz.mssl.ucl.ac.uk/Helio/hec_fillmetadata.sql";
psql -d hec -f hec_fillmetadata.sql;
