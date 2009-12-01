

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'flare.label', default: 'Flare')}" />
        <title><g:message code="Flare List" args="[entityName]" /></title>
    </head>
    <body>
      <div class="nav">
      <g:render template="/flare/menubar" />
    </div>
        <div class="body">
            <h1><g:message code="Flare List" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                                                              
                            <g:sortableColumn property="goes_id" title="${message(code: 'flare.goes_id.label', default: 'GOES Id')}" />
                        
                            <g:sortableColumn property="time_start" title="${message(code: 'flare.time_start.label', default: 'Start Date')}" />
                        
                            <g:sortableColumn property="time_peak" title="${message(code: 'flare.time_peak.label', default: 'Peak Date')}" />
                        
                            <g:sortableColumn property="time_end" title="${message(code: 'flare.time_end.label', default: 'End Date')}" />
                        
                            <g:sortableColumn property="nar" title="${message(code: 'flare.nar.label', default: 'Active Region Number')}" />

                            <g:sortableColumn property="latitude" title="${message(code: 'flare.latitude.label', default: 'Latitude')}" />

                            <g:sortableColumn property="longitude" title="${message(code: 'flare.longitude.label', default: 'Longitude')}" />

                            <g:sortableColumn property="long_carr" title="${message(code: 'flare.long_carr.label', default: 'long_carr')}" />

                            <g:sortableColumn property="xray_class" title="${message(code: 'flare.xray_class.label', default: 'GOES Class')}" />

                             <g:sortableColumn property="optical_class" title="${message(code: 'flare.optical_class.label', default: 'Optical Class')}" />

                             <g:sortableColumn property="urlPreview" title="${message(code: 'flare.urlPreview.label', default: 'urlPreview')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${flareInstanceList}" status="i" var="flareInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${flareInstance.id}">${fieldValue(bean: flareInstance, field: "goes_id")}</g:link></td>
                        
                            
                        
                            <td>${fieldValue(bean: flareInstance, field: "time_start")}</td>
                        
                            <td>${fieldValue(bean: flareInstance, field: "time_peak")}</td>
                        
                            <td>${fieldValue(bean: flareInstance, field: "time_end")}</td>
                        
                            <td>${fieldValue(bean: flareInstance, field: "nar")}</td>

                            <td>${fieldValue(bean: flareInstance, field: "latitude")}</td>
                            <td>${fieldValue(bean: flareInstance, field: "longitude")}</td>
                            <td>${fieldValue(bean: flareInstance, field: "long_carr")}</td>
                            <td>${fieldValue(bean: flareInstance, field: "xray_class")}</td>
                            <td>${fieldValue(bean: flareInstance, field: "optical_class")}</td>
                              <td> <a href="${createLinkTo(dir:'images/temp',file:fieldValue(bean:flareInstance, field:'urlPreview'))}">
                <img src="${createLinkTo(dir:'images/temp',file:'small'+fieldValue(bean:flareInstance, field:'urlPreview'))}" alt="no image" />
              </a></td>


                         <!--   <td>
                             <a href="${fieldValue(bean: flareInstance, field: "urlPreview")}">
                               <img src="http://solarmonitor.org/data/20041110/pngs/thmb/seit_00195_thumb.png" alt="no image" />
                             </a></td>-->
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${flareInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
