

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show Product</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="javascript:history.back()">back</a></span>
        </div>
        <div class="body">
            <h1>Show Product</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:productInstance, field:'id')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Duration [s]:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:productInstance, field:'duration')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Flare:</td>
                            
                            <td valign="top" class="value"><g:link controller="flare" action="show" id="${productInstance?.flare?.id}">${productInstance?.flare?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Image Algorithm:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:productInstance, field:'imageAlgorithmFull')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Energy [keV]:</td>
                            
                            <td valign="top" class="value">
                                <g:if test="${productInstance.maxEnergy != 0 || productInstance.minEnergy != 0}">
                                    ${fieldValue(bean:productInstance, field:'minEnergy')} - ${fieldValue(bean:productInstance, field:'maxEnergy')}</td>
                                </g:if>
                                <g:else>&nbsp;</g:else>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Product Type:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:productInstance, field:'productTypeFull')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Start Date:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:productInstance, field:'startDate')}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name" colspan="2">
                                <img src="http://hercules.ethz.ch:8081/archive/${productInstance.imageFilePath}/${productInstance.imageFileName}" alt="product" />
                            </td>
                        </tr>
                    
                    </tbody>
                </table>
            </div>           
        </div>
    </body>
</html>
