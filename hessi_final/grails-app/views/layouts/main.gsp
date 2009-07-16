<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><g:layoutTitle default="HEDC - HESSI Experimental Data Center"/></title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="description" content="HEDC - HESSI Experimental Data Center"/>
    <meta name="keywords" content="hedc, hessi, flare, rhessi, eth, ethz"/>
    <meta name="robots" content="index,follow"/>
    <meta name="language" content="en"/>

    <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'main.css')}"/>
    <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'calendarview.css')}"/>
    <link rel="shortcut icon" href="${createLinkTo(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
    <g:layoutHead/>
    <g:javascript library="scriptaculous" />
    <g:javascript library="application"/>
    <g:javascript library="calendarview"/>
</head>
<body>
<div class="site">
    <div class="head">
        <div class="left">
            <div class="right">
                HEDC - RHESSI European Data Center
            </div>
        </div>
    </div>
    <div class="navigation">
        <ul>
            <li><a href="${createLink(controller:'flare')}" <g:if test="${params.controller=='flare' || params.controller=='product'}">class="active"</g:if> >Search</a></li>
            <li><a href="http://www.hedc.ethz.ch/data" target="_blank">HTTP data access</a></li>
            <li><a href="http://www.astro.phys.ethz.ch/staff/shilaire/hessi/daily_lc/" target="_blank">daily lightcurves</a></li>
            <li><a href="${createLink(controller:'about')}" <g:if test="${params.controller=='about'}">class="active"</g:if> >About</a></li>
        </ul>
    </div>
    <div class="content">
        <g:layoutBody/>
    </div>
    <div class="footer">
         &nbsp;
    </div>
</div>
</body>
</html>