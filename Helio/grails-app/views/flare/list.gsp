

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Flare List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New Flare</g:link></span>
            <span class="menuButton"><g:link action="search">Search for Goes</g:link>
</span>
        </div>
        <div class="body">
            <h1>Flare List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="endDate" title="End Date" />
                        
                   	        <g:sortableColumn property="goes" title="Goes" />
                        
                   	        <g:sortableColumn property="positionX" title="Position X" />
                        
                   	        <g:sortableColumn property="positionY" title="Position Y" />
                        
                   	        <g:sortableColumn property="positionZ" title="Position Z" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${flareInstanceList}" status="i" var="flareInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${flareInstance.id}">${fieldValue(bean:flareInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:flareInstance, field:'endDate')}</td>
                        
                            <td>${fieldValue(bean:flareInstance, field:'goes')}</td>
                        
                            <td>${fieldValue(bean:flareInstance, field:'positionX')}</td>
                        
                            <td>${fieldValue(bean:flareInstance, field:'positionY')}</td>
                        
                            <td>${fieldValue(bean:flareInstance, field:'positionZ')}</td>
                        
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
