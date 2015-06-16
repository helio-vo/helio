egrep -i "txt|csv" hec_insert.sql | cut -d"'" -f2 > make_data_cache.sh
chmod 775 make_data_cache.sh
sed -i 's/^/\\cp /g' make_data_cache.sh
sed -i 's/$/ data_cache/g' make_data_cache.sh
./make_data_cache.sh
