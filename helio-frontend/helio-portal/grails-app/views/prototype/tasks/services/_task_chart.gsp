

<div id="actionViewer">
  <div class="viewerHeader">
    <table  style="height:30px;width: 100%;">
      <tr >
        <td>
           <!--img height="30px" src="${resource(dir:'images/icons/toolbar',file:'hec.png')}" /!-->
        </td>
        <td>
          <h1 >Chart Browsing</h1>

        </td>


      </tr>
    </table>
  </div>

  <div class="module ">
    <div class="header queryHeader viewerHeader">
      <h1>Query Form</h1>
    </div>
    <div class="content">
      <h2 style="margin:15px">The data in this chart is just sample data to test the functionality, not actual Helio Data</h2>
      <form id="actionViewerForm"  action="asyncQuery" method="POST">
        <input id="service_name" name="serviceName" type="hidden" value="chart"/>
        <input id="task_name" name="taskName" type="hidden" value="chart"/>
        <table width="100%" cellpadding="0" cellspacing="0">
          <col width="*" />
          <col width="250"/>
          <tbody>
<%-- date selection area --%>
          
            <%-- instrument selection area --%>
                                    <tr>
                                      <td><div id="cocontainer" style="width: 800px; height: 400px; margin: 0 auto"></div></td>
                                    </tr>
            <%-- result overview --%>
            <tr >
          <table id="result_overview" style="display:none" width="100%" cellpadding="0" cellspacing="0">
            <col width="*" />
          <col width="250"/>
          <tbody>
            <tr >
              <td colspan="2" style="border-top: solid 1px gray;">
                <b>Result Overview</b>

              </td>
            </tr>
            <tr>
              <td>
                <table style="margin-bottom: 10px;">
                  <tr>
                    <td style="vertical-align:middle;" >
                      <div   class="resultDroppable4" style="width: 70px; height: 70px; padding: 0; float: left; margin: 10px;">
                        <img id="result_drop" class ="drop_able" style="margin:0px" src="${resource(dir:'images/helio',file:'result.png')}" />
                      </div>
                    </td>

                    <td id="result_area">



                  </tr>
                  <tr align="center"><td><div class="custom_button" id="result_button">Search</div></td></tr>
                </table>
              </td>
              <td valign="top">
                <!--div class="message"><b>Step 3</b><br/>Click on the 'Search' button once you are ready to proceed</div-->
              </td>
            </tr>
          </tbody>
          </table>
          </tr>
          </tbody>
        </table>
      </form>
    </div>
  </div>
  <div id="displayableResult" class="displayable" style="display:block"></div>
</div>