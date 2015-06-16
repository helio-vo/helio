<?php
	# =============================================
	# HELIO 2010 - by A.Santin
	# INAF - Trieste Astronomical Observatory
	# helio-tester.php
	# last update 2011-mar-16
	# =============================================

//error_reporting(E_ALL);
//ini_set("display_errors", 1);

  require ("sec_global.php");
  require ("hec_env.php");

  echo (sec_header("HELIO tester"));	

  echo '<html><title>Helio tester</title><body>';

  echo '<h1>HelioQueryService tester</h1>';

  echo '<form method=get action="helio-tester2.php" target="_blank">';	

	echo '<p><table><tr>';
	echo '<td><p>Starting date:</td><td>';
	sec_datetimesec("from",TRUE);
	echo '</td></tr><tr><td>Ending date:</td><td>';
	sec_datetimesec("to",FALSE);
	echo '</td></tr></table>';

  echo '<h1>HEC</h1>';

  $heclist = array(
             "goes_xray_flare"=>"goes_xray_flare",
             "halpha_flares_event"=>"halpha_flares_event",
             "hessi_flare"=>"hessi_flare",
             "kso_flare"=>"kso_flare",
             "sgas_event"=>"sgas_event",
             "soho_camp"=>"soho_camp",
             "yohkoh_flare_list"=>"yohkoh_flare_list",
             "yohkoh_sxt_trace_list"=>"yohkoh_sxt_trace_list",
             "lasco_cme_cat"=>"lasco_cme_cat",
             "noaa_proton_event"=>"noaa_proton_event",
             "bas_magnetic_storms"=>"bas_magnetic_storms",
             "gle_list"=>"gle_list",
             "aastar_list"=>"aastar_list",
             "apstar_list"=>"apstar_list",
             "ssc_list"=>"ssc_list",
             "forbush_list"=>"forbush_list",
             "hessi_flare"=>"hessi_flare",
             "eit_list"=>"eit_list",
             "lasco_cme_list"=>"lasco_cme_list",
             "srs_list"=>"srs_list",
             "dsd_list"=>"dsd_list",
             "sidc_sunspot_number"=>"sidc_sunspot_number",
             "drao_10cm_flux"=>"drao_10cm_flux",
             "solar_wind_event"=>"solar_wind_event",
             "hi_cme_list"=>"hi_cme_list",
             "hi_event"=>"hi_event",
             "wind_imf_mag_cloud"=>"wind_imf_mag_cloud",
             "wind_imf_bow_shock"=>"wind_imf_bow_shock",
             "istp_solar_wind_cat"=>"istp_solar_wind_cat",
             "soho_pm_ips"=>"soho_pm_ips",
             "wind_imf_ips"=>"wind_imf_ips",
             "wind_solar_wind_times"=>"wind_solar_wind_times",
             "imp8_solar_wind_times"=>"imp8_solar_wind_times",
             "tsrs_event_list"=>"tsrs_event_list",
             "latest_gev_flare"=>"latest_gev_flare",
             "gevloc_flares"=>"gevloc_flares",
             "wind_waves_type_ii_burst"=>"(wind_waves_type_ii_burst)",
             "wind_typeii_soho_cme"=>"wind_typeii_soho_cme",
             "waves_exp_wind_stereo"=>"waves_exp_wind_stereo",
             "ulysses_swoops_icme"=>"ulysses_swoops_icme",
             "stereo_euvi_catalog"=>"(stereo_euvi_catalog)",
             "stereo_euvi_event"=>"stereo_euvi_event",
             "cactus_soho_cme_list"=>"cactus_soho_cme_list",
             "cactus_sta_cme_list"=>"cactus_sta_cme_list",
             "cactus_stb_cme_list"=>"cactus_stb_cme_list",
             "cactus_soho_flow_list"=>"cactus_soho_flow_list",
             "cactus_sta_flow_list"=>"cactus_sta_flow_list",
             "cactus_stb_flow_list"=>"cactus_stb_flow_list",
             "halo_cme_flare_magnetic_storm"=>"halo_cme_flare_magnetic_storm",
             "ulysses_grb_xray_flare"=>"ulysses_grb_xray_flare",
             "-----"=>"-----",
             "cactus_soho_cme"=>"cactus_soho_cme",
             "cactus_stereoa_cme"=>"cactus_stereoa_cme",
             "cactus_stereob_cme"=>"cactus_stereob_cme",
             "cactus_soho_flow"=>"cactus_soho_flow",
             "cactus_stereoa_flow"=>"cactus_stereoa_flow",
             "cactus_stereob_flow"=>"cactus_stereob_flow",
             "-----."=>"-----.",
"goes_sxr_flare"=>"goes_sxr_flare",
"ngdc_halpha_flare"=>"ngdc_halpha_flare",
"noaa_energetic_event"=>"noaa_energetic_event",
"soho_lasco_cme"=>"soho_lasco_cme",
"stereo_hi_cme"=>"stereo_hi_cme",
"goes_proton_event"=>"goes_proton_event",
"bas_magnetic_storms"=>"bas_magnetic_storms",
"aad_gle"=>"aad_gle",
"ngdc_aastar_storm"=>"ngdc_aastar_storm",
"ngdc_apstar_storm"=>"ngdc_apstar_storm",
"ngdc_ssc"=>"ngdc_ssc",
"cme_forbush_event"=>"cme_forbush_event",
"solar_wind_event"=>"solar_wind_event",
"yohkoh_hxr_flare"=>"yohkoh_hxr_flare",
"rhessi_hxr_flare"=>"rhessi_hxr_flare",
"kso_halpha_flare"=>"kso_halpha_flare",
"soho_eit_wave_transient"=>"soho_eit_wave_transient",
"yohkoh_sxt_trace_list"=>"yohkoh_sxt_trace_list",
"lasco_cme_list"=>"lasco_cme_list",
"stereo_hi_sw_transient"=>"stereo_hi_sw_transient",
"wind_mfi_mag_cloud"=>"wind_mfi_mag_cloud",
"wind_mfi_bs_crossing_time"=>"wind_mfi_bs_crossing_time",
"istp_sw_event"=>"istp_sw_event",
"soho_pm_ip_shock"=>"soho_pm_ip_shock",
"wind_mfi_ip_shock"=>"wind_mfi_ip_shock",
"wind_sw_crossing_time"=>"wind_sw_crossing_time",
"imp8_sw_crossing_time"=>"imp8_sw_crossing_time",
"tsrs_solar_radio_event"=>"tsrs_solar_radio_event",
"latest_gev_flare"=>"latest_gev_flare",
"gevloc_flares"=>"gevloc_flares",
"wind_typeii_soho_cme"=>"wind_typeii_soho_cme",
"wind_stereo_ii_iv_radioburst"=>"wind_stereo_ii_iv_radioburst",
"ulysses_swoops_icme"=>"ulysses_swoops_icme",
"stereo_euvi_event"=>"stereo_euvi_event",
"noaa_active_region_summary"=>"noaa_active_region_summary",
"noaa_daily_solar_data"=>"noaa_daily_solar_data",
"soho_camp"=>"soho_camp",
"sidc_sunspot_number"=>"sidc_sunspot_number",
"drao_10cm_flux"=>"drao_10cm_flux",
"cactus_soho_cme"=>"cactus_soho_cme",
"halo_cme_flare_magnetic_storm"=>"halo_cme_flare_magnetic_storm",
"ulysses_grb_xray_flare"=>"ulysses_grb_xray_flare",
"cactus_stereoa_cme"=>"cactus_stereoa_cme",
"cactus_stereob_cme"=>"cactus_stereob_cme",
"cactus_soho_flow"=>"cactus_soho_flow",
"cactus_stereoa_flow"=>"cactus_stereoa_flow",
"cactus_stereob_flow"=>"cactus_stereob_flow",
"mars_earth_icme"=>"mars_earth_icme",
"wind_ace_sir"=>"wind_ace_sir",
"goes_flare_sep_event"=>"goes_flare_sep_event",
"timed_see_flare"=>"timed_see_flare",
"hfc_wind_waves"=>"hfc_wind_waves",
"ulysses_hxr_flare"=>"ulysses_hxr_flare",
"ulysses_hxr_flare_farside"=>"ulysses_hxr_flare_farside",
"goes_flare_no_sep_radio_cme"=>"goes_flare_no_sep_radio_cme",
"stereo_impactplastic_icme"=>"stereo_impactplastic_icme",
"stereoa_impactplastic_sir"=>"stereoa_impactplastic_sir",
"stereob_impactplastic_sir"=>"stereob_impactplastic_sir",
"stereoa_impactplastic_shock"=>"stereoa_impactplastic_shock",
"stereob_impactplastic_shock"=>"stereob_impactplastic_shock",
"icme_earth_cycle23"=>"icme_earth_cycle23",
"cme_inner_heliosphere"=>"cme_inner_heliosphere",
"seeds_cme"=>"seeds_cme",
"nmdb_gle_list"=>"nmdb_gle_list",
"seeds_soho"=>"seeds_soho",
"seeds_sta"=>"seeds_sta",
"seeds_stb"=>"seeds_stb",
"solar_sep_event"=>"solar_sep_event",
             "------"=>"------",
             "catalogues"=>"catalogues",
             "hec_catalogue"=>"hec_catalogue"
             );
  $s="";
  echo '<select name="hec',"$varname",'" >';
  foreach ($heclist as $k=>$v) {
    echo "<option value=\"$k\"$s>$v";
  }
  echo '</select>';  

	echo '<p><input type="submit" name ="Search" value="GO HEC">';
  echo '<br><br>';
  echo '<a href="http://'.$server_proxy_host .':'. $server_proxy_port . '/' . $hec_context . '/hec_gui.php" target="_blank">HEC new GUI</a><br>';
  echo '<a href="http://'.$server_proxy_host .':'. $server_proxy_port . '/' . $hec_context . '/hec_ui.php" target="_blank">HEC old UI</a><br>';
  echo '<a href="http://www.helio-vo.eu" target="_blank">www.helio-vo.eu</a><br>';
  echo '<a href="http://helio-dev.i4ds.ch/helio-monitoring/" target="_blank">Helio Monitoring Service</a> ';
  echo '<a href="http://helio-dev.cs.technik.fhnw.ch/cgi-bin/nagios3/status.cgi?host=all" target="_blank">Helio Monitoring Service</a><br>';
  echo '<a href="https://spreadsheets.google.com/spreadsheet/ccc?key=0AtF5iHwKnbwHdHBpM29rU19YYVhOZHFVaGpZLUc4UWc&hl=en_US&authkey=CPmeofYC" target="_blank">Anja'."'".'s UCD/UTypes</a><br>';
  echo '<a href="http://helio.i4ds.technik.fhnw.ch/Helio-dev/prototype/explorer" target="_blank">Helio HFE</a><br>';
  echo '<a href="http://cdhf5.bnsc.rl.ac.uk/cdaweb.new/HELIO/" target="_blank">HELIO Event Catalogue Inventory</a><br>';
  echo '<a href="http://'.$server_proxy_host.':' . $server_proxy_port . '/' $hec_hqi_context . '/VOSI/tables" target="_blank">HEC tables</a>&#160;';
  echo '<a href="http://'.$server_proxy_host.':' . $server_proxy_port . '/' $hec_hqi_context . '/VOSI/capabilities" target="_blank">HEC capabilities</a><br>';
  echo '<a href="http://'.$server_proxy_host.':' . $server_proxy_port . '/' $uoc_hqi_context . '/VOSI/tables" target="_blank">UOC tables</a>&#160;';
  echo '<a href="http://'.$server_proxy_host.':' . $server_proxy_port . '/' $uoc_hqi_context . '/VOSI/capabilities" target="_blank">UOC capabilities</a><br>';
  //echo '<br>';
  echo '<a href="http://'.$server_proxy_host.':' . $server_proxy_port . '/'  $stilts_context . '/task/sqlclient?db=jdbc:postgresql://'.$server_nonproxy_host.'/hec&user=apache&sql=select%20name,description,flare,cme,swind,part,otyp,solar,ips,geo,planet%20FROM%20hec_catalogue%20WHERE%20status%20NOT%20LIKE%20%27deleted%27%20ORDER%20BY%20sort&ofmt=text" target="_blank">HEC crosstable</a>&nbsp;';
  echo '<a href="http://'.$server_proxy_host .':' $server_proxy_port . '/' . $stilts_context . '/task/sqlclient?db=jdbc:postgresql://'.$server_nonproxy_host.'/hec&user=apache&sql=select%20name,description,timefrom,timeto,type,status,flare,cme,swind,part,otyp,solar,ips,geo,planet%20FROM%20hec_catalogue%20WHERE%20status%20NOT%20LIKE%20%27deleted%27%20ORDER%20BY%20sort&ofmt=text" target="_blank">HEC crosstable2</a>&nbsp;';
  echo '<a href="http://'.$server_proxy_host . ':'. $server_proxy_port .'/' . $hec_context . '/hec-crosstable.html" target="_blank">HEC crosstable3</a>&nbsp;';
  echo '<a href="http://'.$server_proxy_host .':' . $server_proxy_port . '/' . $stilts_context . '/task/sqlclient?db=jdbc:postgresql://' . $server_nonproxy_host . '/hec&user=apache&sql=select%20count%28*%29%20FROM%20hec_catalogue%20WHERE%20status%20NOT%20LIKE%20%27deleted%27&ofmt=text" target="_blank">HEC count</a><br>';
  echo '<a href="http://mon.itor.us/sharedPage.jsp?tI=pSRAmMcPtFVOPGEFPAqWFA%253D%253D&uI=KUNt4SiMVMLh1gDedD7TLQ%253D%253D" target="_blank">mon.itor.us/Tools</a>&#160;';
  echo '<a href="http://mon.itor.us/sharedPage.jsp?tI=hSN4uh98%252BE8DONGwJhR36g%253D%253D&uI=KUNt4SiMVMLh1gDedD7TLQ%253D%253D" target="_blank">mon.itor.us/WebMapView</a>&#160;';
  echo '<a href="http://mon.itor.us/sharedPage.jsp?tI=e%25252FEqwtaL4AlMuqfAH9qVtA%253D%253D&uI=KUNt4SiMVMLh1gDedD7TLQ%253D%253D" target="_blank">mon.itor.us/GaugeView</a>&#160;<br>';
  echo '<a href="http://'.$server_proxy_host .':'. $server_proxy_port '/' . $statistics_context' .'/awstats.pl" target="_blank">awstats</a><br>';
  echo '<a href="http://'.$server_proxy_host . ':'. $server_proxy_port .'/' . $hec_context . '/helio-tester.php" target="_blank">festung1</a>&#160;';
  echo '<a href="http://'.$server_proxy_host . ':'. $server_proxy_port .'/' . $hec_context . '/helio-tester.php" target="_blank">festung2</a>&#160;';
  echo '<a href="http://'.$server_proxy_host . ':'. $server_proxy_port .'/' . $hec_context . '/helio-tester.php" target="_blank">festung3</a>&#160;';
  echo '<a href="http://'.$server_proxy_host . ':'. $server_proxy_port .'/' . $hec_context . '/helio-tester.php" target="_blank">festung4</a>&#160;';
	
	echo '<p><br><input type="submit" name ="Search" value="ALL HEC"> VERY SLOW!';//.'&bullet; &radic;';
  echo '<br><hr>';
  echo '<h1>UOC</h1>';
  $heclist = array(
             "planetary_cat"=>"planetary_cat",
//             "instruments"=>"instruments(VIEW)",
//             "timeinterval"=>"timeinterval(VIEW)",
             "ghan_cat"=>"ghan_cat",
             "eis_cat"=>"eis_cat",
             "solar_cat"=>"solar_cat",
             "cds_cat"=>"cds_cat",
             "sxt_cat"=>"sxt_cat",
             "trace_cat"=>"trace_cat",
             "instrument_information"=>"instrument_information",
             "pointed_instrument"=>"pointed_instrument"
             );
  $s="";
  echo '<select name="uoc',"$varname",'">';
  foreach ($heclist as $k=>$v) {
    echo "<option value=\"$k\"$s>$v";
  }
  echo '</select>';  
	echo '<p>instrument code: <input type="text" name ="instr_code" value="">';

	echo '<p><input type="submit" name ="Search" value="GO UOC">';

  echo '<br><hr>';
  echo '<h1>UOC free SQL</h1>';
	echo '<p>UOC free SQL: <input type="text" size="100" name="uoc_sql" value="select * from ghan_cat limit 10">';
//	echo '<p>passwd: <input type="text" name ="passwd" value="">';

  $stilts_out = array(
             "text"=>"text",
             "votable-tabledata"=>"votable-tabledata"
             );
  $s="";
  echo '<p><select name="stilts_out',"$varname",'">';
  foreach ($stilts_out as $k=>$v) {
    echo "<option value=\"$k\"$s>$v";
  }
  echo '</select>';  
	echo '<p><input type="submit" name ="Search" value="GO UOC freeSQL">';
	
  echo '</form>';	

	echo '<a href="http://festung1.oats.inaf.it/stilts8081/task/sqlclient?db=jdbc:postgresql://festung1.oats.inaf.it/uoc&user=apache&sql=select%20count%28*%29%20from%20pointed_instrument%20&ofmt=text" target="_blank">test STILTS via PROXY</a>';          

  echo '</body></html>';

/*
<a href="http://140.105.77.30:8080/helio-hec-r3/HelioQueryService?STARTTIME=2010-03-29T20:30:56&ENDTIME=2010-03-30T20:30:56&FROM=goes_xray_flare"  target="_blank">goes_xray_flare</a><br>';

<a href="http://140.105.77.30:8080/helio-uoc-r3/HelioQueryService?STARTTIME=2010-03-29T20:30:56&ENDTIME=2010-03-30T20:30:56&FROM=ghan_cat"  target="_blank">ghan</a>
*/

  echo (sec_footer());

?>

