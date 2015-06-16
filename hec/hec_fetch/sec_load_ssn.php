<?php
	# =============================================
	# EGSO 2003 SEC server - by Max Jurcev, Andrej Santin
	# INAF - Trieste Astronomical Observatory
	# ---------------------------------------------
	// read monthly smoothed sunspot number
	// web site: http://sidc.oma.be/DATA/monthssn.dat
	// sec_load_ssn.php
	# 1st 26-nov-03, last 26-nov-03
	# =============================================
	require ("sec_global.php");
//	$tempdir = "/var/www/html/sec/temp";

	
	// get files from HTTP
	exec ("wget http://sidc.oma.be/DATA/monthssn.dat");
	copy ("monthssn.dat",$tempdir."/monthssn.dat");
	unlink ("monthssn.dat");

	// parse files and create postgres-ready file	
	$f1 = fopen("$tempdir/SSN.postgres.converted",'w');
	$f2 = fopen("$tempdir/monthssn.dat",'r');
	while (!feof ($f2)) {
    	$buffer = fgets($f2);
		$ssn = trim(substr($buffer,27,5));
//		echo "$year $month $ssn\n";
		if ($ssn<>"") {
			$year = substr($buffer,0,4);
			$month = substr($buffer,4,2);
                        if($year=="2012") {
			// check for last day of month
			if ($month=="02") {
				if (checkdate(2,29,$year)) { $day = 29; } else { $day = 28; }
			} else {
				if (checkdate($month,31,$year)) { $day = 31; } else { $day = 30; }
			}
			$out = sprintf("%04d-%02d-01 00:00:00\t%04d-%02d-%02d 23:59:59\t%f\n",
				$year,$month,$year,$month,$day,$ssn);
			//$out = "01/$month/$year 00:00:00\t$day/$month/$year 23:59:59\t$ssn\n";
			fwrite($f1,$out);
                        }
		}
	}
	fclose($f2);
	fclose($f1);
?>
