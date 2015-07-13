<?php
  # =============================================
  # HELIO 2009,2011 - by A.Santin
  # INAF - Trieste Astronomical Observatory
  # ---------------------------------------------
  # HEC - UI main page
  # hec_gui_range.php
  # last 11-feb-2011, 01-set-2011
  # =============================================

  require ("hec_global.php");
 
    $dbconn = pg_connect("dbname=hec");
    if (!$dbconn) {
      echo "Error: unable to connect database";
    }
    
  	$f2 = fopen("$secdir/temp/catalogues.postgres.converted",'w');

    // read database for table list
    $sql_string="SELECT cat_id,name,description,type,status,url,hec_groups_id,bg_color,longdescription,
       timefrom,timeto,update_timeto,
       flare,cme,swind,part,otyp,solar,ips,geo,planet
     FROM hec_catalogue ORDER BY hec_groups_id;";// WHERE hec_groups_id = $g ORDER BY cat_id;";
    $result = pg_exec($dbconn,$sql_string);
    if (!$result) {
      echo "error\n";
    }

    $now = date('Ymd');
    while ($r = pg_fetch_array($result)) {
        $timeto = $r['timeto'];
        $timefrom = $r['timefrom'];
        //search for last data in the catalogue (and filter any future date)
//        if (strpos(' '.$timeto,'2999')==1) {
        if ($r['update_timeto']=='y') {
          //$sql_string2="SELECT max(time_start) as timemax FROM ".$r['name']." WHERE time_start<='$now'";//select max(end) where end<=now
          $sql_string2="SELECT min(time_start) as timemin, max(time_start) as timemax FROM ".$r['name'];
          $result2 = pg_exec($dbconn,$sql_string2);
          if ($result2) {
            $r2 = pg_fetch_array($result2);
            $timeto = substr($r2['timemax'],0,10);
            $timefrom = substr($r2['timemin'],0,10);
          } else {
            $timeto = '&nbsp;';//if any error leave empty
            $timefrom = '&nbsp;';//if any error leave empty
          }//if
          $sql_string3="UPDATE hec_catalogue SET timeto='".$timeto."' ,timefrom='" . $timefrom . "' WHERE name='".$r['name']."';";
          echo $sql_string3."\n";
          $result3 = pg_exec($dbconn,$sql_string3);

        }//if 2999
        echo('<td>'.$timeto.'</td>'."\n");
        if ($r['status']!='deleted') fwrite($f2,$r['name']."\t".$r['description']."\t".$r['type']."\t".$r['status']."\t".$r['timefrom']."\t".$timeto."\n");
 
    }//while
  	fclose($f2);


  	$f2 = fopen("hec-crosstable.html",'w');
    // read database for table list
    $sql_string="SELECT description,
       flare,cme,swind,part,otyp,solar,ips,geo,planet
     FROM hec_catalogue WHERE status NOT LIKE 'deleted' ORDER BY sort;";
    $result = pg_exec($dbconn,$sql_string);
    if (!$result) {
      echo "error\n";
    }
    
    //generate cross table
    fwrite($f2,'<html><title>HEC cross table</title><head>');
    fwrite($f2,'<style TYPE="text/css">
  table.hec th, table.hec td {
  font-size : 70%;
  font-family : "Myriad Web",Verdana,Helvetica,Arial,sans-serif;
  background : ivory none; color : black; } </style>');
    fwrite($f2,'</head><body>');
    fwrite($f2,"<table class='hec' border=1>");
    fwrite($f2,"<tr><th>Description</th><th>Flare</th><th>CME</th><th>Solar Wind</th><th>Particle</th><th>In situ/Remote</th><th>Solar</th><th>IPS</th><th>GEO</th><th>Planet</th></tr>\n");
    while ($r = pg_fetch_array($result)) {
      fwrite($f2,'<tr>');
      fwrite($f2,'<td>'.$r['description'].'</td>');
      for ($i=1; $i<10; $i++) {
        if ($r[$i]=='y' or $r[$i]=='Y')
          fwrite($f2,'<td align="center">&bull;</td>');
        else if ($r[$i]=='I')
          fwrite($f2,'<td align="center">I</td>');
        else if ($r[$i]=='r')
          fwrite($f2,'<td align="center">R</td>');
        else
          fwrite($f2,'<td>&nbsp;</td>');
      }
      fwrite($f2,"</tr>\n");
    }//while
    fwrite($f2,"</table>\n");
    fwrite($f2,'</body></html>');

  	fclose($f2);


?>
