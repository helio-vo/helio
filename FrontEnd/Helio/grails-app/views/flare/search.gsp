<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <gui:resources components="['tooltip','datePicker']"/>
    <meta http-equiv="Content-Type" content="text/html;
          charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Helio Initial Workflow</title>
  </head>
  <body class ="yui-skin-sam">
    
    <div class="nav">
      <g:render template="/flare/menubar" />
    </div>
    <div class="body">
      
      <!--gui:datePicker id='postOn' value="${new Date()}" includeTime="true"/-->

     
      <h1>Radio and X-ray Flare Search</h1>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <g:form action="search" method="post" >
        <div class="dialog">
          <table>

            <tr class='prop'>
              <td valign='top' class='name'>
                <label for='date'>Date Start:</label>
              </td>
              <td valign='top' class='value'>

            <g:datePicker name='minDate' precision='day' value='${new Date(1100000000000L)}' years="${2000..2009}" />
            </tr>
            
           <!--tr class='prop'>
              <td valign='top' class='name'>
                <label for='date'>Date End:</label>
              </td>
              <td valign='top' class='value'>
            <g:datePicker name='maxDate' precision='day'  value='${new Date(1200000000000L)}' years="${2000..2009}" />

            </td>
            </tr-->
         

            <tr class='prop'>
              <td valign='top' class='name'>
                <label for='goes'>Goes Class:</label>
              </td>
              <td valign='top' class='value'>
                <input type="text" maxlength='4' name='goes' size="4">
                </input>
                -
                <input type="text" maxlength='4' name='goesMax' size="4">
                </input>
              </td>

            </tr>
          
               
            <tr>
              <td>
                <input type="submit" value="Search" class="formbutton"> </input>
              </td>
            </tr>
            
          </table>

      </g:form>
    </div>
  </div>

		
		<!--<div class="searchParameters" id="searchParameters" style="display:true;">
				<g:form action="search" method="get">
						<fieldset>
								<legend>Search parameters</legend>
								<p>
										<label>Start:</label>
										<g:datePicker name="startDate"  precision="day" />
							

								<p>
										<label>End:</label>
										<g:datePicker name="endDate"  precision="day" />
								
								
                    <label>Event-Key:</label>
                    
                </p>
						</fieldset>
						<span class="button"><input class="save" type="submit" value="Search"/></span>
				</g:form>
		</div>-->
</body>
</html>