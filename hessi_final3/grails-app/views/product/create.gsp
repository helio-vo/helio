

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Create Product</title>         
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Product List</g:link></span>
        </div>
        <div class="body">
            <h1>Create Product</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${productInstance}">
            <div class="errors">
                <g:renderErrors bean="${productInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="duration">Duration:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:productInstance,field:'duration','errors')}">
                                    <input type="text" id="duration" name="duration" value="${fieldValue(bean:productInstance,field:'duration')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="flare">Flare:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:productInstance,field:'flare','errors')}">
                                    <g:select optionKey="id" from="${Flare.list()}" name="flare.id" value="${productInstance?.flare?.id}" ></g:select>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="imageAlgorithm">Image Algorithm:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:productInstance,field:'imageAlgorithm','errors')}">
                                    <input type="text" id="imageAlgorithm" name="imageAlgorithm" value="${fieldValue(bean:productInstance,field:'imageAlgorithm')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="imageFileName">Image File Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:productInstance,field:'imageFileName','errors')}">
                                    <input type="text" id="imageFileName" name="imageFileName" value="${fieldValue(bean:productInstance,field:'imageFileName')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="imageFilePath">Image File Path:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:productInstance,field:'imageFilePath','errors')}">
                                    <input type="text" id="imageFilePath" name="imageFilePath" value="${fieldValue(bean:productInstance,field:'imageFilePath')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="maxEnergy">Max Energy:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:productInstance,field:'maxEnergy','errors')}">
                                    <input type="text" id="maxEnergy" name="maxEnergy" value="${fieldValue(bean:productInstance,field:'maxEnergy')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="minEnergy">Min Energy:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:productInstance,field:'minEnergy','errors')}">
                                    <input type="text" id="minEnergy" name="minEnergy" value="${fieldValue(bean:productInstance,field:'minEnergy')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="productType">Product Type:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:productInstance,field:'productType','errors')}">
                                    <input type="text" id="productType" name="productType" value="${fieldValue(bean:productInstance,field:'productType')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="startDate">Start Date:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:productInstance,field:'startDate','errors')}">
                                    <g:datePicker name="startDate" value="${productInstance?.startDate}" ></g:datePicker>
                                </td>
                            </tr> 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Create" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
