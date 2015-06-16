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

//KMB: load the cactus and seeds files.  This just needs to be a wget because they are already in a format for a basic Postgres COPY to work later.
        exec ("rm mssl_fetch/seeds*_2013.txt");
        exec ("rm mssl_fetch/seeds*_2014.txt");
        exec ("rm mssl_fetch/soho-2013*.txt");
        exec ("rm mssl_fetch/soho-2014*.txt");
        exec ("rm mssl_fetch/sta-2013*.txt");
        exec ("rm mssl_fetch/sta-2014*.txt");
        exec ("rm mssl_fetch/stb-2013*.txt");
        exec ("rm mssl_fetch/stb-2014*.txt");
        exec ("rm mssl_fetch/STEREO-A*.csv");
        exec ("rm mssl_fetch/STEREO-B*.csv");
        exec ("rm mssl_fetch/gevloc1_list_201406z.csv");
        exec ("rm mssl_fetch/latest_gev_full.txt");
        exec ("rm mssl_fetch/soho_pm_shock.csv");
        exec ("rm mssl_fetch/sdemon_dimming.csv");
        exec ("rm mssl_fetch/sdemon_flare.csv");
        exec ("rm mssl_fetch/stereoa_het_sep.csv");
        exec ("rm mssl_fetch/stereob_het_sep.csv");
        exec ("rm mssl_fetch/corimp_savgol.csv");
        exec ("rm mssl_fetch/corimp_quadratic.csv");
        exec ("rm mssl_fetch/corimp_linear.csv");
        exec ("rm mssl_fetch/fermi_latevent.csv");
        exec ("rm mssl_fetch/fermi_gbmflare.csv");

        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/corimp_savgol.csv");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/corimp_quadratic.csv");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/corimp_linear.csv");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/fermi_latevent.csv");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/fermi_gbmflare.csv");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/seeds_soho_2014.txt");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/seeds_sta_2014.txt");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/seeds_stb_2014.txt");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/soho-2014_cme.txt");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/soho-2014_flow.txt");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/sta-2014_cme.txt");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/sta-2014_flow.txt");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/stb-2014_cme.txt");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/stb-2014_flow.txt");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/gevloc1_list_201406z.csv");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/soho_pm_shock.csv");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/STEREO-A_icme.csv");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/STEREO-A_sir.csv");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/STEREO-A_shock.csv");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/STEREO-B_icme.csv");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/STEREO-B_sir.csv");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/STEREO-B_shock.csv");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/latest_gev_full.txt");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/stereoa_het_sep.csv");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/stereob_het_sep.csv");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/sdemon_dimming.csv");
        exec ("wget -P mssl_fetch http://www.mssl.ucl.ac.uk/~rdb/helio-hec/sdemon_flare.csv");

        // get files from HTTP
        //exec ("wget -N --directory-prefix=$tempdir http://www.mssl.ucl.ac.uk/~rdb/egso-sec/latest_gev.txt");
        exec ("cat mssl_fetch/latest_gev_full.txt | sort | uniq > $tempdir/latest_gev.txt.last");
//        exec ("/bin/rm $tempdir/latest_gev.txt");

        exec ("wget -N --directory-prefix=$tempdir http://www.mssl.ucl.ac.uk/~rdb/soars/gevloc_flares.txt");
        exec ("cat $tempdir/gevloc_flares.txt | sort | uniq > $tempdir/gevloc_flares.txt.last");


        // parse files and create postgres-ready file
        $f1 = fopen("$tempdir/LATEST_GEV_FLARE.postgres.converted",'w');
        $f2 = fopen("$tempdir/latest_gev.txt.last",'r');
//2010/09/24 01:20:00; 2010/09/24 01:25:00; 2010/09/24 01:31:00; 11109; ; ; B2.9; ;
        while (!feof ($f2)) {
          $buffer = fgets($f2);
          $items = explode(";", $buffer);
          //print_r($items);
          for ($i=0; $i<count($items); $i++) {
            $items[$i] = trim($items[$i]);
            if ($items[$i]=="") $items[$i] = "\N";
          }//for
          if (count($items)==9) fwrite($f1,$items[0]."\t".$items[1]."\t".$items[2]."\t".$items[3]."\t".$items[4]."\t".$items[5]."\t".$items[6]."\t".$items[7]."\n");
          if (count($items)==10) fwrite($f1,$items[0]."\t".$items[1]."\t".$items[2]."\t".$items[3]."\t".$items[4]."\t".$items[5]."\t".$items[6]."\t".$items[7]. "\t". $items[8] . "\n");
          //echo(count($items)."\n");
        }//while
        fclose($f2);
        fclose($f1);

        $f1 = fopen("$tempdir/GEVLOC_FLARES.postgres.converted",'w');
        $f2 = fopen("$tempdir/gevloc_flares.txt.last",'r');
//2010/10/28 10:12:00  B2.0  N18W34
        while (!feof ($f2)) {
          $buffer = fgets($f2);
          if (substr($buffer,0,1)!=";") {
            $items = explode("  ", $buffer);
            //print_r($items);
            for ($i=0; $i<count($items); $i++) {
              $items[$i] = trim($items[$i]);
              if ($items[$i]=="") $items[$i] = "\N";
            }//for
            if (count($items)==3) fwrite($f1,$items[0]."\t".$items[1]."\t".$items[2]."\n");
          }//if
        }//while
        fclose($f2);
        fclose($f1);

?>
