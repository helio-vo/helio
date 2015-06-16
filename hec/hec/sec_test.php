<?php
	# =============================================
	# EGSO 2003,2004 - by Max Jurcev
	# INAF - Trieste Astronomical Observatory
	# ---------------------------------------------
	require ("sec_global.php");
	echo (sec_header("EGSO SEC test"));

	# =============================================
	# connect db and perform the query
	# =============================================
	$dbconn = pg_connect("dbname=hec");
	if (!$dbconn) {
		sec_error('Unable to connect to the database');
		exit;
	}
	$result = pg_exec($dbconn,"SELECT * FROM sgas_event ORDER BY time_start");
	if (!$result) {
		sec_error('Error while reading the database');
		exit;
	}
	sec_showresult($result);
	pg_close($dbconn);
	echo (sec_footer());
  ?>
