

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="public" />
        <g:set var="entityName" value="${message(code: 'flare.label', default: 'Flare')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
          <g:render template="/flare/menubar" />
<!--
          <span class="menuButton"><a class="home" href="${resource(dir: '')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
-->
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'flare.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="ntime_end" title="${message(code: 'flare.ntime_end.label', default: 'Ntimeend')}" />
                        
                            <g:sortableColumn property="optical_class" title="${message(code: 'flare.optical_class.label', default: 'Opticalclass')}" />
                        
                            <g:sortableColumn property="hessi_measurementEnd" title="${message(code: 'flare.hessi_measurementEnd.label', default: 'Hessimeasurement End')}" />
                        
                            <g:sortableColumn property="nar" title="${message(code: 'flare.nar.label', default: 'Nar')}" />
                        
                            <g:sortableColumn property="hessi_xPos" title="${message(code: 'flare.hessi_xPos.label', default: 'Hessix Pos')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${flareInstanceList}" status="i" var="flareInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${flareInstance.id}">${fieldValue(bean: flareInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: flareInstance, field: "ntime_end")}</td>
                        
                            <td>${fieldValue(bean: flareInstance, field: "optical_class")}</td>
                        
                            <td>${fieldValue(bean: flareInstance, field: "hessi_measurementEnd")}</td>
                        
                            <td>${fieldValue(bean: flareInstance, field: "nar")}</td>
                        
                            <td>${fieldValue(bean: flareInstance, field: "hessi_xPos")}</td>
                        
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
