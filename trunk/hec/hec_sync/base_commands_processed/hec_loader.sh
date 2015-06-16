#! /bin/sh


#--------------------------
# EGSO - SEC server
# INAF - Trieste Astronomical Observatory
# automatic crontab loader
#-----------------------------------------
# AUTOMATIC (crontab) DAILY UPDATING
# OF SEC DATABASE
#-----------------------------------------
# by M.Jurcev, A.Santin -  EGSO
# first revision 05-jan-2004
# last revision  17-jun-2009
#-----------------------------------------

SECROOT=/var/www/hec_fetch

#Now done in hec_sync ./DumpPreviousTables.sh

#-----------------------------------------
# load and parse new data from providers
#-----------------------------------------
php -f $SECROOT/sec_load_hef.php

php -f $SECROOT/sec_load_npe.php

#KMB: LASCO seemed to end in 2010 on the ftp site they are using - lasco6.nascom.nasa.gov/lasco/status
#php -f $SECROOT/sec_load_lcl.php

#KMB: Now seems to be updating. - http://cdaw.gsfc.nasa.gov/CME_list/UNIVERSAL/text_ver/univ_all.txt
php -f $SECROOT/sec_load_lcc.php


php -f $SECROOT/sec_load_ssn.php
#KMB: does not do anything.
#php -f $SECROOT/sec_load_sfm.php

php -f $SECROOT/sec_load_dsd.php

php -f $SECROOT/sec_load_sgs.php
sort $SECROOT/temp/SGS2.postgres.converted | uniq > $SECROOT/temp/SGS.postgres.converted

#php -f $SECROOT/sec_load_yfc.php #closed

php -f $SECROOT/sec_load_srs_new.php
sort $SECROOT/temp/SRS2.postgres.converted | uniq > $SECROOT/temp/SRS.postgres.converted

#KMB: php of bms does not currently download or do anything.
#php -f $SECROOT/sec_load_bms.php #partial?

#php -f $SECROOT/sec_load_goes.php
#cat $SECROOT/temp/GOES.postgres.converted | uniq > $SECROOT/temp/GOES2.postgres.converted
#cp $SECROOT/temp/GOES2.postgres.converted $SECROOT/temp/GOES.postgres.converted

#KMB don't see any 2012 data
#php -f $SECROOT/sec_load_soho.php

#KMB load up data from HFC
#php -f SECROOT/hfc_load_obspm.php

php -f $SECROOT/sec_load_kso.php

#php -f $SECROOT/sec_load_eit.php #closed
#php -f $SECROOT/sec_load_yst.php #closed
#php -f $SECROOT/sec_load_ha.php #still some errors

php -f $SECROOT/hec_load_mssl.php

#-----------------------------------------
# regenerate SEC database structure 
# and online documentation
#-----------------------------------------
#psql -d hec -f $SECROOT/hec_fillmetadata.sql

#php -f $SECROOT/hec_doc_generator.php

#-----------------------------------------
# insert SEC data
#-----------------------------------------
#psql -d hec -f $SECROOT/hec_insert.sql

#-----------------------------------------
# write log and summary
#-----------------------------------------
date >> $SECROOT/sec.log

#Now done in hec_sync ./DumpCurrentTables.sh

#Now done in hec_sync ./CompareDumpedFiles.sh

#new GUI
#php -f $SECROOT/hec_range.php
#php -f $SECROOT/hec_gui_range.php
#psql -d hec -f $SECROOT/catalogues.sql
#php -f $SECROOT/hec_graph.php
