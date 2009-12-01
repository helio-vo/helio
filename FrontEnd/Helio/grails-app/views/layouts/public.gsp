<html>
  <head>

    <title><g:layoutTitle default="Helio" />
    </title>
    <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'public.css')}">
    <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'calendarview.css')}"/>
    </link>
  
  <g:layoutHead />
  <g:javascript library="application" />
  <g:javascript library="FancyZoom"/>
  <g:javascript library="calendarview"/>
  <g:javascript library="FancyZoomHTML"/>
</head>
<body onload="setupZoom()">
  <table class="contentArea">
    <tr>
      <td>
        <div class="logo"></div>
    <g:layoutBody />
  </td>
</tr>
</table>
</body>
</html>