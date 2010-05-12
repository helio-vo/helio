<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
     <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'flare.label', default: 'Flare')}" />

    <title><g:message code="default.list.label" args="[entityName]" /></title>
   
  
  <g:javascript library="count"/>
</head>
    <body>
        
            <div class="nav">
      <g:render template="/flare/menubar" />
    </div>
         
    <h1>Sample line</h1>
    Waiting for Results
    <span id="cd"></span>

  </body>
</html>
