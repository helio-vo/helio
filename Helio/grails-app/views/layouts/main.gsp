<html>
    <head>

        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:layoutHead />
        <g:javascript library="application" />
        <g:javascript library="FancyZoom"/>
      <g:javascript library="FancyZoomHTML"/>
    </head>
    <body onload="setupZoom()">
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner" />
        </div>	
        <div class="logo"><img src="${resource(dir:'images',file:'grails_logo.jpg')}" alt="Grails" /></div>
        <g:layoutBody />		
    </body>	
</html>