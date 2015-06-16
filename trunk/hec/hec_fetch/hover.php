<?php
  # =============================================
  # HELIO 2009,2011 - by A.Santin
  # INAF - Trieste Astronomical Observatory
  # ---------------------------------------------
  # HEC - UI main page
  # hec_gui.php
  # last 11-feb-2011, 18-feb-2011
  # header field changed by Alex from 'Catalogue' to 'Catalogue Description', 02-Apr-2011  
  # added cell padding and augmented spacing
  # =============================================
  require ("hec_global.php");
 
  echo('<center>');

  echo (hec_header("HELIO HEC - GUI"));

  echo '<script type="text/javascript" src="hec_gui.js"></script>';
  echo '<script type="text/javascript" src="scrollable.js"></script>';



    echo '<tr><td><b>Catalogues matching selection</b></tr>';
//    echo '&nbsp;<img src="info_icon.gif" title="Select catalogues to get events from and fetch data" border=0 style="font-style:italic;" >';
//    echo '&nbsp;<img src="info_icon.gif" title="Select catalogues to get events from and fetch data" border=0 style="font-style:italic;" '.'onmouseover="this.src=\'image2.gif\'" onmouseout="this.src=\'info_icon.gif\'" >';
//    echo ' <div id="n1" style="position:absolute; left:280; top:300; z-index:1;" > <table bgcolor="yellow" width=250 cellpadding=6 cellspacing=0 border=1 > <tr> <td> <p>Select catalogues to get events from and fetch data.</p> </td> </tr> </table> </div>';

    echo '&nbsp;<img src="info_icon.gif"  onmouseover="hoverover(event,\'status\');" onmouseout="hoverout(event,\'status\');" >';
    echo ' <div id="status" style="position:absolute; left:280; top:300; visibility:hidden; z-index:1;" >';
    echo ' <table bgcolor="#F8FDBA" width=500 cellpadding=6 cellspacing=0 border=1 >';
    echo ' <tr> <td>Closed</td><td>	The catalogue updating has been definitively stopped and will not be resumed</td> </tr>';
    echo ' <tr> <td>Active</td><td>	The catalogue has been updated either on a routinary or on an irregular basis</td> </tr>';
    echo ' <tr> <td>Inactive</td><td>	The catalogue has not been updated either on the provider side or on the HEC service side</tr>';
    echo ' </table> </div>';
    echo '</td>';
    echo '&nbsp;&nbsp;&nbsp;<tr><td colspan="8" align="center"><INPUT TYPE="button" style="background-color:lightgreen; color:black;" value="Submit search" onclick="document.forms[\'catform\'].submit();"></td></tr>';
    echo '<br><br>';
    
    echo '<span title="Hey you are hovering over me!">This text is on the web page</span>';
    echo '<br><br>';
    
    echo '<p style="cursor:Pointer;" title="Find out more about James Bond and his line of work">My name is James Bond</p>';
    echo '<br><br>';

    
  echo (sec_footer());

?>
