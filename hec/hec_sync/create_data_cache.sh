\rm make_data_cache.sh
\rm data_cache/*
egrep -i "txt|csv|converted" ../hec_fetch/hec_insert.sql | cut -d"'" -f2 > make_data_cache.sh
chmod 775 make_data_cache.sh
sed -i 's/^/\\cp /g' make_data_cache.sh
sed -i 's/$/ data_cache/g' make_data_cache.sh
./make_data_cache.sh
tar -cvf data_cache.tar data_cache
mv data_cache.tar ./data_cache
scp data_cache/*.tar kmb@msslus:/data2/gaia/usdownloads/Helio
\cp ../hec_fetch/hec_insert.sql .
sed -i 's/\/var\/www\/\/hec_fetch\/temp\//\/var\/www\/hec_sync\/data_cache_download\/data_cache\//g' hec_insert.sql
sed -i 's/\/var\/www\/hec_fetch\/temp\//\/var\/www\/hec_sync\/data_cache_download\/data_cache\//g' hec_insert.sql
sed -i 's/\/var\/www\/hec_fetch\/mssl_fetch\//\/var\/www\/hec_sync\/data_cache_download\/data_cache\//g' hec_insert.sql
sed -i 's/\/var\/www\/hec_fetch\//\/var\/www\/hec_sync\/data_cache_download\/data_cache\//g' hec_insert.sql
scp hec_insert.sql kmb@msslus:/data2/gaia/usdownloads/Helio
