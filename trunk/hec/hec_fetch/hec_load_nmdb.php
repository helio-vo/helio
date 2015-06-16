<?php
	# =============================================
	# HELIO 2010 HEC server - by Andrej Santin
	# ---------------------------------------------
	# read NMDB
	# web site: www.nmdb.eu
	# http://www.nmdb.eu/nest/help.php#howto
	# hec_load_nmdb.php
	# 1st 15-feb-2012, last 23-feb-2012
	# =============================================

$tempdir = "/var/www/hec/temp";
$f1 = fopen("$tempdir/nmdb_gle_list.postgres.converted",'w');

$link = mysql_connect('db03.nmdb.eu:3306', 'HELIO-INAF', 'cID5G9mg');
if (!$link) {
    die('Could not connect: ' . mysql_error());
}
echo 'Connected successfully';
mysql_select_db('nmdb') or die('Could not select database');
$query = 'SELECT * FROM GLE';
$result = mysql_query($query) or die('Query failed: ' . mysql_error());
while ($line = mysql_fetch_array($result, MYSQL_ASSOC)) {
/*    echo "\t<tr>\n";
    foreach ($line as $col_value) {
        echo "\t\t<td>$col_value</td>\n";
    }
    echo "\t</tr>\n"; */
    print_r($line);
    $num = $line['num'];
    $link = 'http://www.nmdb.eu/nest/draw_graph.php?formchk=1&allstations=1&output=plot&tabchoice=revori&dtype=corr_for_efficiency&date_choice=bygle&baseline=1&yunits=1&yscale=0&anomalous=1&gle_num='.$num;
    echo $link;
    $cmd = 'wget "'.$link.'" -O -';
    $output = shell_exec($cmd);
    if (stripos($output,"sorry")>0) $link = '';
    $comments = $line[comments];
    if (strlen($comments)==0) $comments='\N';
    fwrite($f1,$num."\t".$line[baseline_start_date_time]."\t".$line[baseline_end_date_time]."\t".$line[onset_date]." ".$line[onset_time]."\t".$comments."\t".$link."\n");
}//while

mysql_free_result($result);
mysql_close($link);
fclose($f1);

?>
