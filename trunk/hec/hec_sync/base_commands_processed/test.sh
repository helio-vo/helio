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
# insert SEC data
#-----------------------------------------
psql -d hec -f $SECROOT/hec_insert.sql

#-----------------------------------------
# write log and summary
#-----------------------------------------
date >> $SECROOT/sec.log

#new GUI
php -f $SECROOT/hec_range.php
php -f $SECROOT/hec_gui_range.php
#psql -d hec -f $SECROOT/catalogues.sql
php -f $SECROOT/hec_graph.php

