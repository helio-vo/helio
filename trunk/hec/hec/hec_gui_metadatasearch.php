<?php
  # =============================================
  # HELIO 2009,2011 - by A.Santin
  # INAF - Trieste Astronomical Observatory
  # ---------------------------------------------
  # HEC - UI main page
  # hec_gui_fetch.php
  # last 11-feb-2011, 18-feb-2011
  # =============================================

  import_request_variables("g");
  require ("sec_global.php");

  echo (sec_header("HELIO HEC - GUI"));

  //print_r($_GET);
  $word = $_GET['q'];
  $cmd = "grep -i ".$word." /var/www/hec/stfc/HEC_ListsAll.html";
  $result = shell_exec($cmd);
  //print_r($result);
  $lines = explode("\n", $result);
  foreach($lines as $line) {
    $line = str_replace($word,"<font style='color:blue; background-color:yellow;'>$word</font>",$line);
    echo '<a href="stfc/HEC_ListsAll.html">'.$line.'</a><br>';
  }

  echo (sec_footer());

?>
