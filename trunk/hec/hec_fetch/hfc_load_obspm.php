<?php
        # =============================================
        # HELIO 2009 HEC server - by Andrej Santin
        # INAF - Trieste Astronomical Observatory
        # ---------------------------------------------
        // hec_load_mssl.php
        # 1st 03-nov-10, last 06-nov-10
        # =============================================
       // require ("sec_global.php");
        $tempdir = "/var/www/hec_fetch/temp";
        exec ("rm hfc_l3*wind*.txt");
        exec ("wget ftp://ftpbass2000.obspm.fr/pub/helio/hfc_level3/hfc_l3_t3_wind_waves.txt");
?>
