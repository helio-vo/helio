<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;
          charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Search for GOES</title>
  </head>
  <body>
    <div class="body">
      <h1>Search for GOES</h1>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <g:form action="search" method="post" >
        <div class="dialog">
          <table>
            <tr class='prop'>
              <td valign='top' class='name'>
                <label for='goes'>goes:</label>
              </td>
              <td valign='top' class='value'>
                <input type="text" maxlength='30' name='goes'>
                </input>
              </td>

            </tr>
            <tr class='prop'>
              <td valign='top' class='name'>
                <label for='date'>Date:</label>
              </td>
              <td valign='top' class='value'>
            between
            <g:datePicker name='minDate' precision='day' />
            and
            <g:datePicker name='maxDate' precision='day'
                          value='${new Date().plus(365*2)}'/>
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