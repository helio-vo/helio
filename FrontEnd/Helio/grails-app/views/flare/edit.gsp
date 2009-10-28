

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'flare.label', default: 'Flare')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir: '')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${flareInstance}">
            <div class="errors">
                <g:renderErrors bean="${flareInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${flareInstance?.id}" />
                <g:hiddenField name="version" value="${flareInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="ntime_end"><g:message code="flare.ntime_end.label" default="Ntimeend" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'ntime_end', 'errors')}">
                                    <g:textField name="ntime_end" value="${flareInstance?.ntime_end}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="optical_class"><g:message code="flare.optical_class.label" default="Opticalclass" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'optical_class', 'errors')}">
                                    <g:textField name="optical_class" value="${flareInstance?.optical_class}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="hessi_measurementEnd"><g:message code="flare.hessi_measurementEnd.label" default="Hessimeasurement End" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'hessi_measurementEnd', 'errors')}">
                                    <g:textField name="hessi_measurementEnd" value="${flareInstance?.hessi_measurementEnd}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="nar"><g:message code="flare.nar.label" default="Nar" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'nar', 'errors')}">
                                    <g:textField name="nar" value="${fieldValue(bean: flareInstance, field: 'nar')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="hessi_xPos"><g:message code="flare.hessi_xPos.label" default="Hessix Pos" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'hessi_xPos', 'errors')}">
                                    <g:textField name="hessi_xPos" value="${flareInstance?.hessi_xPos}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="time_start"><g:message code="flare.time_start.label" default="Timestart" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'time_start', 'errors')}">
                                    <g:textField name="time_start" value="${flareInstance?.time_start}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="phoenix2_peakCS"><g:message code="flare.phoenix2_peakCS.label" default="Phoenix2peak CS" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'phoenix2_peakCS', 'errors')}">
                                    <g:textField name="phoenix2_peakCS" value="${flareInstance?.phoenix2_peakCS}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="hessi_measurementStart"><g:message code="flare.hessi_measurementStart.label" default="Hessimeasurement Start" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'hessi_measurementStart', 'errors')}">
                                    <g:textField name="hessi_measurementStart" value="${flareInstance?.hessi_measurementStart}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="phoenix2_xPos"><g:message code="flare.phoenix2_xPos.label" default="Phoenix2x Pos" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'phoenix2_xPos', 'errors')}">
                                    <g:textField name="phoenix2_xPos" value="${flareInstance?.phoenix2_xPos}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="hessi_energyKeVTo"><g:message code="flare.hessi_energyKeVTo.label" default="Hessienergy Ke VT o" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'hessi_energyKeVTo', 'errors')}">
                                    <g:textField name="hessi_energyKeVTo" value="${flareInstance?.hessi_energyKeVTo}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="hessi_measurementPeak"><g:message code="flare.hessi_measurementPeak.label" default="Hessimeasurement Peak" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'hessi_measurementPeak', 'errors')}">
                                    <g:textField name="hessi_measurementPeak" value="${flareInstance?.hessi_measurementPeak}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="phoenix2_energyKeVFrom"><g:message code="flare.phoenix2_energyKeVFrom.label" default="Phoenix2energy Ke VF rom" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'phoenix2_energyKeVFrom', 'errors')}">
                                    <g:textField name="phoenix2_energyKeVFrom" value="${flareInstance?.phoenix2_energyKeVFrom}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="phoenix2_yPos"><g:message code="flare.phoenix2_yPos.label" default="Phoenix2y Pos" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'phoenix2_yPos', 'errors')}">
                                    <g:textField name="phoenix2_yPos" value="${flareInstance?.phoenix2_yPos}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="goes_id"><g:message code="flare.goes_id.label" default="Goesid" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'goes_id', 'errors')}">
                                    <g:textField name="goes_id" value="${fieldValue(bean: flareInstance, field: 'goes_id')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="time_end"><g:message code="flare.time_end.label" default="Timeend" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'time_end', 'errors')}">
                                    <g:textField name="time_end" value="${flareInstance?.time_end}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="longitude"><g:message code="flare.longitude.label" default="Longitude" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'longitude', 'errors')}">
                                    <g:textField name="longitude" value="${fieldValue(bean: flareInstance, field: 'longitude')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="ntime_start"><g:message code="flare.ntime_start.label" default="Ntimestart" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'ntime_start', 'errors')}">
                                    <g:textField name="ntime_start" value="${flareInstance?.ntime_start}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="hessi_totalCounts"><g:message code="flare.hessi_totalCounts.label" default="Hessitotal Counts" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'hessi_totalCounts', 'errors')}">
                                    <g:textField name="hessi_totalCounts" value="${flareInstance?.hessi_totalCounts}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="long_carr"><g:message code="flare.long_carr.label" default="Longcarr" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'long_carr', 'errors')}">
                                    <g:textField name="long_carr" value="${fieldValue(bean: flareInstance, field: 'long_carr')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="phoenix2_AR"><g:message code="flare.phoenix2_AR.label" default="Phoenix2 AR" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'phoenix2_AR', 'errors')}">
                                    <g:textField name="phoenix2_AR" value="${flareInstance?.phoenix2_AR}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="hessi_yPos"><g:message code="flare.hessi_yPos.label" default="Hessiy Pos" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'hessi_yPos', 'errors')}">
                                    <g:textField name="hessi_yPos" value="${flareInstance?.hessi_yPos}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="phoenix2_totalCounts"><g:message code="flare.phoenix2_totalCounts.label" default="Phoenix2total Counts" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'phoenix2_totalCounts', 'errors')}">
                                    <g:textField name="phoenix2_totalCounts" value="${flareInstance?.phoenix2_totalCounts}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="hessi_peakCS"><g:message code="flare.hessi_peakCS.label" default="Hessipeak CS" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'hessi_peakCS', 'errors')}">
                                    <g:textField name="hessi_peakCS" value="${flareInstance?.hessi_peakCS}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="hessi_radial"><g:message code="flare.hessi_radial.label" default="Hessiradial" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'hessi_radial', 'errors')}">
                                    <g:textField name="hessi_radial" value="${flareInstance?.hessi_radial}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="xray_class"><g:message code="flare.xray_class.label" default="Xrayclass" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'xray_class', 'errors')}">
                                    <g:textField name="xray_class" value="${flareInstance?.xray_class}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="phoenix2_urlPhaseFITSGZ"><g:message code="flare.phoenix2_urlPhaseFITSGZ.label" default="Phoenix2url Phase FITSGZ" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'phoenix2_urlPhaseFITSGZ', 'errors')}">
                                    <g:textField name="phoenix2_urlPhaseFITSGZ" value="${flareInstance?.phoenix2_urlPhaseFITSGZ}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="hessi_flareNr"><g:message code="flare.hessi_flareNr.label" default="Hessiflare Nr" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'hessi_flareNr', 'errors')}">
                                    <g:textField name="hessi_flareNr" value="${flareInstance?.hessi_flareNr}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="time_peak"><g:message code="flare.time_peak.label" default="Timepeak" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'time_peak', 'errors')}">
                                    <g:textField name="time_peak" value="${flareInstance?.time_peak}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="phoenix2_urlIntensityFITSGZ"><g:message code="flare.phoenix2_urlIntensityFITSGZ.label" default="Phoenix2url Intensity FITSGZ" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'phoenix2_urlIntensityFITSGZ', 'errors')}">
                                    <g:textField name="phoenix2_urlIntensityFITSGZ" value="${flareInstance?.phoenix2_urlIntensityFITSGZ}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="hessi_energyKeVFrom"><g:message code="flare.hessi_energyKeVFrom.label" default="Hessienergy Ke VF rom" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'hessi_energyKeVFrom', 'errors')}">
                                    <g:textField name="hessi_energyKeVFrom" value="${flareInstance?.hessi_energyKeVFrom}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="phoenix2_flareNr"><g:message code="flare.phoenix2_flareNr.label" default="Phoenix2flare Nr" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'phoenix2_flareNr', 'errors')}">
                                    <g:textField name="phoenix2_flareNr" value="${flareInstance?.phoenix2_flareNr}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="latitude"><g:message code="flare.latitude.label" default="Latitude" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'latitude', 'errors')}">
                                    <g:textField name="latitude" value="${fieldValue(bean: flareInstance, field: 'latitude')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="phoenix2_radial"><g:message code="flare.phoenix2_radial.label" default="Phoenix2radial" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'phoenix2_radial', 'errors')}">
                                    <g:textField name="phoenix2_radial" value="${flareInstance?.phoenix2_radial}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="hessi_AR"><g:message code="flare.hessi_AR.label" default="Hessi AR" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'hessi_AR', 'errors')}">
                                    <g:textField name="hessi_AR" value="${flareInstance?.hessi_AR}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="phoenix2_energyKeVTo"><g:message code="flare.phoenix2_energyKeVTo.label" default="Phoenix2energy Ke VT o" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'phoenix2_energyKeVTo', 'errors')}">
                                    <g:textField name="phoenix2_energyKeVTo" value="${flareInstance?.phoenix2_energyKeVTo}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="phoenix2_measurementStart"><g:message code="flare.phoenix2_measurementStart.label" default="Phoenix2measurement Start" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: flareInstance, field: 'phoenix2_measurementStart', 'errors')}">
                                    <g:textField name="phoenix2_measurementStart" value="${flareInstance?.phoenix2_measurementStart}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
