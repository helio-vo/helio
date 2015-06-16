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

SECROOT=/var/www/hec

#-----------------------------------------
# load and parse new data from providers
#-----------------------------------------
#date >> $SECROOT/sec.log
php -f $SECROOT/sec_range.php
#php -f $SECROOT/sec_graph.php

