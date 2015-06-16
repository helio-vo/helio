wget -P /tmp "http://msslkz.mssl.ucl.ac.uk/usdownloads/Helio/hec_sql_loadscripts/hec_fillmetadata_cat.sql";
wget -P /tmp "http://www.mssl.ucl.ac.uk/~rdb/helio-hec/fermi_gbmflare_base.csv";
wget -P /tmp "http://msslkz.mssl.ucl.ac.uk/usdownloads/Helio/hec_sql_loadscripts/newheclists_load.sql";
psql -d hec < /tmp/newheclists_load.sql;
psql -d hec < /tmp/hec_fillmetadata_cat.sql;
