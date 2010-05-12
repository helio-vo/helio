

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'flare.label', default: 'Flare')}" />
  <title><g:message code="Flare Viewer" args="[entityName]" /></title>
</head>
<body>
  <div class="nav">

    <g:render template="/flare/menubar" />

  </div>
  <div class="body">
    <h1><g:message code="Flare Viewer" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <div class="dialog">
      <table>
        <tbody>

          <tr class="prop">
            <td valign="top" class="name"><g:message code="flare.id.label" default="Id" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "id")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.goes_id.label" default="Goesid" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "goes_id")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.time_start.label" default="Timestart" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "time_start")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.time_peak.label" default="Timepeak" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "time_peak")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.time_end.label" default="Timeend" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "time_end")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.nar.label" default="Nar" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "nar")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.latitude.label" default="Latitude" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "latitude")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.longitude.label" default="Longitude" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "longitude")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.long_carr.label" default="Longcarr" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "long_carr")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.xray_class.label" default="Xrayclass" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "xray_class")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.optical_class.label" default="Opticalclass" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "optical_class")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.ntime_end.label" default="Ntimeend" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "ntime_end")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.hessi_measurementEnd.label" default="Hessimeasurement End" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "hessi_measurementEnd")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.hessi_xPos.label" default="Hessix Pos" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "hessi_xPos")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.phoenix2_peakCS.label" default="Phoenix2peak CS" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "phoenix2_peakCS")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.hessi_measurementStart.label" default="Hessimeasurement Start" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "hessi_measurementStart")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.phoenix2_xPos.label" default="Phoenix2x Pos" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "phoenix2_xPos")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.hessi_energyKeVTo.label" default="Hessienergy Ke VT o" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "hessi_energyKeVTo")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.hessi_measurementPeak.label" default="Hessimeasurement Peak" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "hessi_measurementPeak")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.phoenix2_energyKeVFrom.label" default="Phoenix2energy Ke VF rom" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "phoenix2_energyKeVFrom")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.phoenix2_yPos.label" default="Phoenix2y Pos" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "phoenix2_yPos")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.ntime_start.label" default="Ntimestart" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "ntime_start")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.hessi_totalCounts.label" default="Hessitotal Counts" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "hessi_totalCounts")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.phoenix2_AR.label" default="Phoenix2 AR" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "phoenix2_AR")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.hessi_yPos.label" default="Hessiy Pos" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "hessi_yPos")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.phoenix2_totalCounts.label" default="Phoenix2total Counts" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "phoenix2_totalCounts")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.hessi_peakCS.label" default="Hessipeak CS" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "hessi_peakCS")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.hessi_radial.label" default="Hessiradial" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "hessi_radial")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.phoenix2_urlPhaseFITSGZ.label" default="Phoenix2url Phase FITSGZ" /></td>

        <td valign="top" class="value"><a href="${fieldValue(bean: flareInstance, field: "phoenix2_urlPhaseFITSGZ")}">${fieldValue(bean: flareInstance, field: "phoenix2_urlPhaseFITSGZ")}</a></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.hessi_flareNr.label" default="Hessiflare Nr" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "hessi_flareNr")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.phoenix2_urlIntensityFITSGZ.label" default="Phoenix2url Intensity FITSGZ" /></td>

          <td valign="top" class="value"><a href="${fieldValue(bean: flareInstance, field: "phoenix2_urlIntensityFITSGZ")}">${fieldValue(bean: flareInstance, field: "phoenix2_urlIntensityFITSGZ")}</a></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.hessi_energyKeVFrom.label" default="Hessienergy Ke VF rom" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "hessi_energyKeVFrom")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.phoenix2_flareNr.label" default="Phoenix2flare Nr" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "phoenix2_flareNr")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.phoenix2_radial.label" default="Phoenix2radial" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "phoenix2_radial")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.hessi_AR.label" default="Hessi AR" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "hessi_AR")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.phoenix2_energyKeVTo.label" default="Phoenix2energy Ke VT o" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "phoenix2_energyKeVTo")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.phoenix2_measurementStart.label" default="Phoenix2measurement Start" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "phoenix2_measurementStart")}</td>

        </tr>
        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.urlPreview.label" default="urlPreview" /></td>

        <td valign="top" class="value">${fieldValue(bean: flareInstance, field: "urlPreview")}</td>

        </tr>
        <tr class="prop">
          <td valign="top" class="name"><g:message code="flare.urlPreview.label" default="urlPreview" /></td>

        <td valign="top" class="value"><img src="${createLinkTo(dir:'images/temp',file:fieldValue(bean:flareInstance, field:'urlPreview'))}"/></td>

        </tr>

        </tbody>
      </table>
    </div>

  </div>
</body>
</html>
