

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit Flare</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Flare List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Flare</g:link></span>
        </div>
        <div class="body">
            <h1>Edit Flare</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${flareInstance}">
            <div class="errors">
                <g:renderErrors bean="${flareInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${flareInstance?.id}" />
                <input type="hidden" name="version" value="${flareInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="endDate">End Date:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:flareInstance,field:'endDate','errors')}">
                                    <g:datePicker name="endDate" value="${flareInstance?.endDate}" precision="minute" ></g:datePicker>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="goes">Goes:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:flareInstance,field:'goes','errors')}">
                                    <input type="text" id="goes" name="goes" value="${fieldValue(bean:flareInstance,field:'goes')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="positionX">Position X:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:flareInstance,field:'positionX','errors')}">
                                    <input type="text" id="positionX" name="positionX" value="${fieldValue(bean:flareInstance,field:'positionX')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="positionY">Position Y:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:flareInstance,field:'positionY','errors')}">
                                    <input type="text" id="positionY" name="positionY" value="${fieldValue(bean:flareInstance,field:'positionY')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="positionZ">Position Z:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:flareInstance,field:'positionZ','errors')}">
                                    <input type="text" id="positionZ" name="positionZ" value="${fieldValue(bean:flareInstance,field:'positionZ')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="radialOffset">Radial Offset:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:flareInstance,field:'radialOffset','errors')}">
                                    <input type="text" id="radialOffset" name="radialOffset" value="${fieldValue(bean:flareInstance,field:'radialOffset')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="startDate">Start Date:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:flareInstance,field:'startDate','errors')}">
                                    <g:datePicker name="startDate" value="${flareInstance?.startDate}" precision="minute" ></g:datePicker>
                                </td>
                            </tr> 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" value="Update" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
