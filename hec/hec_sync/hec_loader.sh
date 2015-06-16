#! /bin/sh

rm hec_insert.sql
wget "http://helio.mssl.ucl.ac.uk/usdownloads/Helio/hec_insert.sql"
SECROOT=/var/www/hec_sync

./DumpPreviousTables.sh

./download_run_changes_frombase.sh

./download_extract_data_cache.sh

rm $SECROOT/hec_insert.log
date >> $SECROOT/hec_insert.log
psql -d hec -f $SECROOT/hec_insert.sql >> hec_insert.log 2>&1
cp $SECROOT/hec_insert.log /var/www/hec/
#-----------------------------------------
# write log and summary
#-----------------------------------------
date >> $SECROOT/sec.log

./DumpCurrentTables.sh

./CompareDumpedFiles.sh

