

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Product List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New Product</g:link></span>
        </div>
        <div class="body">
            <h1>Product List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="duration" title="Duration" />
                        
                   	        <th>Flare</th>
                   	    
                   	        <g:sortableColumn property="imageAlgorithm" title="Image Algorithm" />
                        
                   	        <g:sortableColumn property="imageFileName" title="Image File Name" />
                        
                   	        <g:sortableColumn property="imageFilePath" title="Image File Path" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${productInstanceList}" status="i" var="productInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${productInstance.id}">${fieldValue(bean:productInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:productInstance, field:'duration')}</td>
                        
                            <td>${fieldValue(bean:productInstance, field:'flare')}</td>
                        
                            <td>${fieldValue(bean:productInstance, field:'imageAlgorithm')}</td>
                        
                            <td>${fieldValue(bean:productInstance, field:'imageFileName')}</td>
                        
                            <td>${fieldValue(bean:productInstance, field:'imageFilePath')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${Product.count()}" />
            </div>
        </div>
    </body>
</html>
