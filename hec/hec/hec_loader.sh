#! /bin/sh

SECROOT=/var/www/hec

php -f $SECROOT/hec_doc_generator.php

php -f $SECROOT/hec_range.php
php -f $SECROOT/hec_gui_range.php
php -f $SECROOT/hec_graph.php

