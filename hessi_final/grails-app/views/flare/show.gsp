<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <title>Show Flare</title>
    <g:javascript library="FancyZoom"/>
    <g:javascript library="FancyZoomHTML"/>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="javascript:history.back()">back</a></span>
</div>
<div class="body">
    <h1>Products for ${flareInstance.flareListId}</h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="dialog">
        <table cellpadding="0" cellspacing="0">
            <th width="40">

            </th>
            <th>
                Start Date
            </th>
            <th width="90">
                Duration [s]
            </th>
            <th width="150">
                Product Type
            </th>
            <th width="100">
                Energy [keV]
            </th>
            <th width="80">
                Image Algorithm
            </th>
						<g:each var="p" status="i" in="${flareInstance.products}">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    <td style="text-align:center">
                        <a href="http://hercules.ethz.ch:8081/archive/${p.imageFilePath}/${p.imageFileName}">
                            <img class="zoom" src="${createLinkTo(dir: 'images', file: 'zoom.png')}" alt="quick look"/>
                        </a>
                    </td>
                    <td>
                        <g:link controller="product" action="show" id="${p.id}">${p.startDate}</g:link>
                    </td>
                    <td>
                        ${p.duration}&nbsp;
                    </td>
                    <td>
                        ${p.productTypeFull}&nbsp;
                    </td>
                    <td>
                        <g:if test="${p.minEnergy!=0 || p.minEnergy!=0}" >
                            ${p.minEnergy} - ${p.maxEnergy}
                        </g:if>
                        <g:else>&nbsp;</g:else>
                    </td>
                    <td>
                        ${p.imageAlgorithmFull}&nbsp;
                    </td>

                </tr>
            </g:each>
        </table>

        <h2>Details</h2>
        <table>
            <tbody>

            <tr class="prop">
                <td valign="top" class="name">Event-Key:</td>

                <td valign="top" class="value">${fieldValue(bean: flareInstance, field: 'id')}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name">X Position [arcsec]:</td>

                <td valign="top" class="value">${fieldValue(bean: flareInstance, field: 'posX')}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name">Y Position [arcsec]:</td>

                <td valign="top" class="value">${fieldValue(bean: flareInstance, field: 'posY')}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name">GOES Class:</td>

                <td valign="top" class="value">${fieldValue(bean: flareInstance, field: 'classSpecification')}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name">Comments:</td>

                <td valign="top" class="value">${fieldValue(bean: flareInstance, field: 'comments')}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name">Distance To Sun Center [arcsec]:</td>

                <td valign="top" class="value">${fieldValue(bean: flareInstance, field: 'distanceSun')}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name">Flare List Id:</td>

                <td valign="top" class="value">${fieldValue(bean: flareInstance, field: 'flareListId')}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name">Number Of Sources:</td>

                <td valign="top" class="value">${fieldValue(bean: flareInstance, field: 'sources')}</td>

            </tr>

            </tbody>
        </table>

    </div>
</div>
<script type="text/javascript">
    document.observe("dom:loaded", function(e) {
        var baseurl = "${createLinkTo(dir: '')}";
        setupZoom();
    });
</script>
</body>
</html>
