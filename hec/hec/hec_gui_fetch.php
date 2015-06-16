<?php
  # =============================================
  # HELIO 2009,2011 - by A.Santin
  # INAF - Trieste Astronomical Observatory
  # ---------------------------------------------
  # HEC - UI main page
  # hec_gui_fetch.php
  # last 11-feb-2011, 18-feb-2011
  # page title changed by Alex 02-Apr-2011
  # =============================================

  import_request_variables("g");
#extract($_GET);

  require("hec_env.php");
  //print_r($_GET);

  function msg_error($text) {
    echo "<h2>$text</h2>";
    echo '<input type=button value="Close" onClick="window.close()" name="button">';
  }

function replace_urls_with_links($result) {
          $p0 = 0;
          $len = strlen($result);
          $ii = 0;
          str_replace('http:','PP',$result,$cnt);//count http to change
          for ($ii=0;$ii<$cnt;$ii++) {
            $p = stripos($result,'http:',$p0);//search next
            $e = stripos($result,'</TD>',$p+1);//find end
            $link = substr($result,$p,$e-$p);//extract link
            $link = str_replace('&quot;','',$link);
            if(strpos($link,'href') === false && strpos($link,'target') === false  ) {
                $result = str_replace($link . '<','[<a href="'.$link.'" target="_blank" >URL</a>]<',$result);
            }
            $p0 = $e+8;//skip enough to avoid recursion
          }//for
        return $result;
}


  if ($_GET['cmd']!='') {

//    header('Content-disposition: attachment; filename='.date('Ymd_His').'.'.$_GET['type']);
    if ($_GET['type']=='votable') header('Content-type: text/xml');
//    if ($_GET['type']=='csv') header('Content-type: text/ascii');
    if ($_GET['type']=='hqs') header('Content-type: text/xml');

    //echo(urldecode($_GET['cmd']));
    //echo("<br />");
    //echo("From Get <br />");
    //echo($_GET['cmd']);
    //echo("From Get <br />");
    //echo($_GET['sql']);
    //echo("<br />");
    //$cmd = 'wget "'.urldecode($_GET['cmd']).'" -O -';
    $query = $_GET['sql'];
    //echo("Initial <br />");
    //echo($query);
    //echo("<br />");
    $query = str_replace("\'","'",$query);
    //echo("After replace <br />");
    //echo("<br />");
    //echo($query);
    //echo("<br />");
    $query = urlencode($query);
    //echo("Encoded <br />");
    //echo($query);
    //echo("<br />");
    $cmd = 'wget "' .  $_GET['cmd'] . '&sql=' .$query . '" -O -';
    //echo($cmd);
    //echo("<br />");
    $result = shell_exec($cmd);
    if (($_GET['type']=='votable') or ($_GET['type']=='csv')) {
      $result = substr($result,strpos($result,"\n")+1,1e7);//skip STILTS sql string
      $result = substr($result,0,strpos($result,"Elapsed"));//skip wget elapsed
    }
    print_r($result);
  } else {
    require ("hec_global.php");
    echo (hec_header("HELIO HEC - Results"));

    while ((!checkdate($mo_from,$d_from,$y_from)) and ($d_from>0)) { $d_from--; }
    if (!checkdate($mo_from,$d_from,$y_from)) {
            msg_error('Start time is invalid, please verify');
            exit;
    }
    while ((!checkdate($mo_to,$d_to,$y_to)) and ($d_to>0)) { $d_to--; }
    if (!checkdate($mo_to,$d_to,$y_to)){
            msg_error('End time is invalid, please verify');
            exit;
    }
    if (mktime($h_from,$mi_from,$s_from,$mo_from,$d_from,$y_from) >
            mktime($h_to,$mi_to,$s_to,$mo_to,$d_to,$y_to)) {
            msg_error('End date is greater than start date, please verify');
            exit;
    }
    if (!array_key_exists('$h_from',$_GET)) $h_from=0;
    if (!array_key_exists('$mi_from',$_GET)) $mi_from=0;
    if (!array_key_exists('$s_from',$_GET)) $s_from=0;
    $from = sprintf("%04d-%02d-%02d %02d:%02d:%02d",
            $y_from,$mo_from,$d_from,$h_from,$mi_from,$s_from);
    if (!array_key_exists('$h_to',$_GET)) $h_to=23;
    if (!array_key_exists('$mi_to',$_GET)) $mi_to=59;
    if (!array_key_exists('$s_to',$_GET)) $s_to=59;
    $to = sprintf("%04d-%02d-%02d %02d:%02d:%02d",
            $y_to,$mo_to,$d_to,$h_to,$mi_to,$s_to);
    $query_time = "time_start>='$from' AND time_start<='$to'";

    $a = 1;
    foreach($_GET as $k=>$v) {
      //print_r($_SERVER);
      $empty = true;
      if ($v=='istable') {
        $empty = false;
        $query = "select * from $k where $query_time";//%20limit%2010
        if (strpos($k,'mars')!==false) {
          $query_time = "time_start_mars>='$from' AND time_start_mars<='$to'";
          $query = "select * from $k where $query_time";
        }

        $ofmt = 'html-element';
        $cmd = 'wget "http://'.$server_proxy_host .':' . $server_proxy_port . '/' . $stilts_context . '/task/sqlclient?db=jdbc:postgresql://'.$server_nonproxy_host.'/hec&user=apache&sql='.urlencode($query).'&ofmt='.$ofmt.'" -O -';
        //$cmd = 'wget "http://festung1.oats.inaf.it:8081/stilts/task/sqlclient?db=jdbc:postgresql://140.105.77.30/hec&user=apache&sql=select%20*%20from%20goes_xray_flare%20limit%2010&ofmt=html-element" -O -';
        //echo($cmd);
        //passthru($cmd,$result);
        $result = shell_exec($cmd);
        if (strlen($result)>1e7) { echo '<h2>Too big!</h2>'; exit; }
        $result = substr($result,strpos($result,"\n"),1e7);//skip STILTS sql string
        $result = substr($result,0,strpos($result,"Elapsed"));//skip STILTS wget elapse

        echo('<a name="a'.$a.'"></a>');
        echo($a.'.Download as&nbsp;');

        $ofmt = 'hqs';//http://140.105.77.30:8080/helio-hec-r3/HelioQueryService?STARTTIME=2011-02-02T00:00:00&ENDTIME=2011-03-02T23:59:59&FROM=goes_xray_flare');
        $cmd = 'http://'.$server_proxy_host.':' . $server_proxy_port . '/' . $hec_hqi_context . '/HelioQueryService?STARTTIME='.$from.'&ENDTIME='.$to.'&FROM='.$k;
        echo('HELIO Service <a href="hec_gui_fetch.php?cmd='.urlencode($cmd).'&type='.$ofmt.'"> HelioQueryInterface VoTable</a>');
        $ofmt = 'votable';

        $cmd = 'http://'.$server_proxy_host .':' . $server_proxy_port . '/'. $stilts_context . '/task/sqlclient?db=jdbc:postgresql://'.$server_nonproxy_host .'/hec&user=apache&ofmt='.$ofmt;
        echo(' (for debugging porpoise <a href="hec_gui_fetch.php?cmd='.urlencode($cmd).'&sql='.urlencode($query).'&type='.$ofmt.'">STILTS VoTable</a>&nbsp;or&nbsp;');

        $ofmt = 'csv';
        $cmd = 'http://'.$server_proxy_host.':' . $server_proxy_port . '/' . $stilts_context . '/task/sqlclient?db=jdbc:postgresql://'.$server_nonproxy_host.'/hec&user=apache&ofmt='.$ofmt;
        echo('<a href="hec_gui_fetch.php?cmd='.urlencode($cmd).'&sql='.urlencode($query).'&type='.$ofmt.'">STILTS CSV</a>)');

        echo('<br>&nbsp;(or&nbsp;go to <a href="#a'.($a-1).'">prev </a>/<a href="#a'.++$a.'">next </a>table header in case of multiple selection)<br>');
        echo ('<b>'.$query.'</b>'); 

	$newDataResults = replace_urls_with_links($result);
        //output to browser
        print_r($newDataResults);
        echo('<p>');
      }//if istable
    }//foreach
    if ($empty) msg_error('ERROR: Select at least one catalogue!');
    echo (sec_footer());
  }//if cmd!=''


?>
