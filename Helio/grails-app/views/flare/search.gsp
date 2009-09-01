<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;
          charset=UTF-8"/>
    <meta name="layout" content="public" />
    <title>Search for Flares</title>
  </head>
  <body>
    <div class="nav">
      <g:render template="/flare/menubar" />
    </div>
    <div class="body">
      <h1>Search for Flares</h1>
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
               
            <g:datePicker name='minDate' precision='day' />
            </tr>
             <tr class='prop'>
              <td valign='top' class='name'>
                <label for='date'>Date End:</label>
              </td>
            <td valign='top' class='value'>
            <g:datePicker name='maxDate' precision='day'
                          value='${new Date().plus(365*2)}'/>
            </td>
            </tr>

<tr class='prop'>
              <td valign='top' class='name'>
                <label for='goes'>Goes Class:</label>
              </td>
              <td valign='top' class='value'>
                <input type="text" maxlength='30' name='goes'>
                </input>
              </td>

            </tr>

            <tr class='prop'>
              <td valign='top' class='name'>
                <label for='goes'>Number Of Sources:</label>
              </td>
              <td valign='top' class='value'>
                <input type="text" maxlength='30' name='none'>
                </input>
              </td>

            </tr>


            <tr class='prop'>
              <td valign='top' class='name'>
                <label for='goes'>Distance To Sun Center:</label>
              </td>
              <td valign='top' class='value'>
                <input type="text" maxlength='30' name='none3'>
                </input>
              </td>

            </tr>
           
          </table>
        </div>
        <div class="buttons">
          <input type="submit" value="Search"
                 class="formbutton">
          </input>
        </div>
      </g:form>
    </div>
  </body>
</html>