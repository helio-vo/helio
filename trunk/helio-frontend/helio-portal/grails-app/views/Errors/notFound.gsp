<html>
  <head>
	  <title>Oops</title>
<link rel="stylesheet" href="${resource(dir:'css',file:'demo.css')}" />

<link rel="stylesheet" href="${resource(dir:'css',file:'navbar.css')}" />
<link rel="stylesheet" href="${resource(dir:'css',file:'prototype.css')}" />
<link rel="stylesheet" href="${resource(dir:'css',file:'demo_table.css')}" />
<link rel="stylesheet" href="${resource(dir:'css',file:'demo_page.css')}" />

  </head>

  <body style="margin:10px">
    <h1 >Oops, Marco just broke the server</h1>
    
    <h4 style="margin-top:15px;margin-bottom: 30px"> We are sorry, perhaps you are just experiencing a hiccup in the network, try refreshing the page in a few sec.</h4>
  	<div class="message">
          Error Trace:<br/>
		<strong>Error ${request.'javax.servlet.error.status_code'}:</strong> ${request.'javax.servlet.error.message'.encodeAsHTML()}<br/>
		<strong>Servlet:</strong> ${request.'javax.servlet.error.servlet_name'}<br/>
		<strong>URI:</strong> ${request.'javax.servlet.error.request_uri'}<br/>
		<g:if test="${exception}">
	  		<strong>Exception Message:</strong> ${exception.message?.encodeAsHTML()} <br />
	  		<strong>Caused by:</strong> ${exception.cause?.message?.encodeAsHTML()} <br />
	  		<strong>Class:</strong> ${exception.className} <br />
	  		<strong>At Line:</strong> [${exception.lineNumber}] <br />
	  		<strong>Code Snippet:</strong><br />
	  		<div class="snippet">
	  			<g:each var="cs" in="${exception.codeSnippet}">
	  				${cs?.encodeAsHTML()}<br />
	  			</g:each>
	  		</div>
		</g:if>
  	</div>
	
  </body>
</html>