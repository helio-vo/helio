<?php
	# =============================================
	# HELIO 2010/2011 - by A.Santin
	# INAF - Trieste Astronomical Observatory
	# helio-tester2.php
	# last update 2011-mar-16
	# =============================================

import_request_variables("pg");
/*
print_r($_GET);
echo '<br>';
print_r($_POST);
echo '<br>';
*/

$heclist = array(
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


);

    while ((!checkdate($mo_from,$d_from,$y_from)) and ($d_from>0)) { $d_from--; }
    if (!checkdate($mo_from,$d_from,$y_from)) {
      sec_error('Start time is invalid, please verify');
      exit;
    }
    while ((!checkdate($mo_to,$d_to,$y_to)) and ($d_to>0)) { $d_to--; }
    if (!checkdate($mo_to,$d_to,$y_to)){
      sec_error('End time is invalid, please verify');
      exit;
    }

		if (mktime($h_from,$mi_from,$s_from,$mo_from,$d_from,$y_from) >
			  mktime($h_to,$mi_to,$s_to,$mo_to,$d_to,$y_to)) {
			sec_error('End date is greater than start date, please verify');
			exit;
		}
		$from = sprintf("%04d-%02d-%02dT%02d:%02d:%02d",
			        $y_from,$mo_from,$d_from,$h_from,$mi_from,$s_from);
		$to = sprintf("%04d-%02d-%02dT%02d:%02d:%02d",
              $y_to,$mo_to,$d_to,$h_to,$mi_to,$s_to);

    $http_host = $_SERVER['HTTP_HOST'];//140.105.77.30

    if ($Search=="GO HEC") {
      $service="helio-hec";
      $table=$hec;
      $instr_code="";
      $url="http://$http_host:8080/$service/HelioQueryService?STARTTIME=$from&ENDTIME=$to&FROM=$table".$instr_code;
    } elseif ($Search=="GO UOC") {
      $service="helio-uoc";
      $table=$uoc;
      if ($instr_code<>"") $instr_code="&INSTRUMENT=".$instr_code;
      $url="http://$http_host:8080/$service/HelioQueryService?STARTTIME=$from&ENDTIME=$to&FROM=$table".$instr_code;
    } elseif ($Search=="ALL HEC") {
      $service="helio-hec";
      $url="http://$http_host:8080/$service/LongRunningQueryService?FROM=hec_catalogue";
      $cmd = 'wget "'.$url.'" -O -';
      $id = shell_exec($cmd);
      $id = substr($id, 0, -5);
      $id = substr($id, 4, 100);
      //$id="5ba8dc11-deab-4fa2-8d1d-6d55b0813afc";
      $url="http://$http_host:8080/$service/ServiceJobStatus?MODE=file&ID=$id";
      $cmd = 'wget "'.$url.'" -O -';
      $i=0;
      do {
        sleep(1);
        $result = shell_exec($cmd);
        $len = strlen($result);
      } while (($len==0) and ($i++<5));
      if ($len>500) echo 'LongRunningQueryService PASS'; else echo '<font color=red>LongRunningQueryService NOT PASS</font>';
      echo " $len i=$i<br>\n";
      echo "<a href='$url'>$url</a><br>\n";
      $i=1;
      foreach ($heclist as $k=>$v) {
       echo $i++." $v : ";
       $url="http://$http_host:8080/$service/HelioQueryService?STARTTIME=$from&ENDTIME=$to&FROM=".$v;
       $cmd = 'wget "'.$url.'" -O -';;
       $result = shell_exec($cmd);
       $len = strlen($result);
       if ($len>500) echo 'PASS'; else echo '<font color=red>NOT PASS</font>'; 
       echo " $len<br>\n";
      }//foreach
      $url='---';
      $service='---';
      $table='---';
      exit(0);
    } else {
      $uoc_sql = str_replace("%","%25",$uoc_sql);
      $uoc_sql = str_replace(" ","%20",$uoc_sql);
      $uoc_sql = str_replace("\'","'",$uoc_sql);
      $service=$uoc_sql;
      $url="http://$http_host:8081/stilts/task/sqlclient?db=jdbc:postgresql://$http_host/uoc&user=apache&sql=".$uoc_sql."&ofmt=".$stilts_out;
//      if ($passwd!="heliovo") $url="permission error";
    }

    echo "<META HTTP-EQUIV=\"refresh\" CONTENT=\"0; URL=$url\">";

    echo "<a href=\"$url\"  target=\"_blank\">$service $table</a><br>";

?>
