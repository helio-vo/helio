

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="public" />
        <title>Radio and Xray Flare</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Flare List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Flare</g:link></span>
        </div>
        <div class="body">
            <h1>Show Flare</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:flareInstance, field:'id')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Goes:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:flareInstance, field:'goes')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Start Date:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:flareInstance, field:'startDate')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Position X:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:flareInstance, field:'positionX')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Position Y:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:flareInstance, field:'positionY')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Position Z:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:flareInstance, field:'positionZ')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Radial Offset:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:flareInstance, field:'radialOffset')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Image:</td>


         <td valign="top" class="value"> <img src="${createLinkTo(dir:'images',file:'image1.jpg')}" alt="quick2 look" /></td>
                                   <!--td valign="top" class="value">{fieldValue(bean:flareInstance, field:'image')}</td-->
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${flareInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
