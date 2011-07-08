<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page import="ch.i4ds.helio.frontend.model.*" %>
<%@ page import="ch.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>HELIO FrontEnd</title>

    <link rel="stylesheet" href="${resource(dir:'css',file:'demo.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'jquery-ui-1.8.11.custom.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'navbar.css')}" />

    <link rel="stylesheet" href="${resource(dir:'css',file:'prototype.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'demo_table.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'demo_page.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'jquery.selectBox.css')}" />





  <g:javascript library="jquery" plugin="jquery"/>
  <g:javascript src="jquery-ui-1.8.11.custom.min.js"/>

  <g:javascript src="jquery.tools.min.js"/>
  <g:javascript src="/helio/helio-prototype.js"/>
  <g:javascript src="/helio/ActionViewer.js"/>
  <g:javascript src="/helio/HelioAjax.js"/>
  <g:javascript src="/helio/ActionViewerExtended.js"/>
  <g:javascript src="/helio/UploadViewer.js"/>
  <g:javascript src="/helio/ResultViewer.js"/>
  <g:javascript src="/helio/History.js"/>
  <g:javascript src="/helio/Workspace.js"/>
  <g:javascript src="/helio/HelioElement.js"/>
  <g:javascript src="/helio/cookies.js"/>
  <g:javascript src="jquery.form.js"/>

  <g:javascript src="jquery.dataTables.js" />
  <g:javascript src="/plugins/jquery.collapsible.js" />
  <g:javascript src="/plugins/jquery.selectBox.min.js" />



  <link rel="shortcut icon" href="${resource(dir:'images/helio',file:'sun.ico')}" type="image/x-icon" />


</head>

<body>
  <input type="hidden" value="${HUID}" id="HUID"/>
  <!-- Background Elements -->
  <div id="page-background">
    <!--img src="${resource(dir:'images/background',file:'bg_flat.jpg')}"   style="width:100%;height:150px"  alt="background"-->
    <!--img src="${resource(dir:'images/background',file:'iconbg.png')}"   style="width:100%;height:125px"  alt="background"-->


    <!--div style="width:100%;height:150px;background-color:orange"></div-->
  </div>

  <!-- Logo Elements -->
  <div id="logo">
    <!--img src="${resource(dir:'images/background',file:'header_lines.png')}"  width="350px" height="120px" />
    <img id="line" src="${resource(dir:'images/background',file:'line_transp.png')}" height="120px"  /-->
    <!--img src="${resource(dir:'images/helio',file:'helio_logo.jpg')}" width="200px" height="100px"  /-->
    <img style="float:left;z-index:100;" src="${resource(dir:'images/background',file:'header_logo.png')}"   />
    <img style="float:left;z-index:100;" src="${resource(dir:'images/background',file:'header_text.png')}"   />
    <img style="position:relative;top:-10px;float:right;height: 120px" src="${resource(dir:'images/background',file:'glowlogo.png')}"  />
  </div>

  <!-- Navigation Bar -->
  <div >
    <!-- elements with tooltips -->
    <g:render template="navbar" />

  </div>

  <!-- Body Container -->
  <div id="container" >

    <!-- Hidden division holding selection results :: need to rework -->
    <div id="testdiv" class="displayable" style="display:none">
      Selection
      <div style="margin-top:4px;margin-bottom:4px;cursor:pointer;padding:4px;background-color:black;color:white;border:1px solid #464693;" id="saveButton">Save Results</div>
    </div>

    <!-- Content container -->

    <div id="tabs">
      <ul>
        <li><a href="#tabs-2">Services</a></li>
        
        
        <!--li><a href="#tabs-3">Advanced</a></li-->
        <li><a href="#tabs-1">Internal(currently disabled)</a></li>

      </ul>
      <div id="tabs-1">
        <table>
          <tr>
            <td>
              <div style="display:block" class="menu_ite2m custom_button" id="task_hec" >HEC</div>
            </td>
            <td>
              <div style="display:block" class="menu_ite2m custom_button" id="task_ics" >ICS</div>
            </td>
            <td>
              <div style="display:block" class="menu_it2em custom_button" id="task_ils" >ILS</div>
            </td>
            <td>
              <div style="display:block" class="menu_i2tem custom_button" id="task_dpas" >DPAS</div>
            </td>
          </tr>
        </table>
      </div>
      <div id="tabs-2">
        <table>
          <tr>
            <td>
              <div style="display:block" class="menu_item custom_button"  id="task_searchEvents">Search Events</div>
            </td>
            <td>
              <div style="display:block" class="menu_item custom_button"  id="task_searchInstruments">Search Instruments</div>
            </td>
            <td>
              <div style="display:block" class="menu_item custom_button"  id="task_searchData">Search Data</div>
            </td>
          </tr>
        </table>
      </div>
      <!--div id="tabs-3">
        <table>
          <tr>
            <td>
              <div style="display:block" class="menu_item custom_button"  id="task_findObsData">In-situ Data Mining</div>
            </td>
            <td>
              <div style="display:block" class="menu_item custom_button"  id="task_uploadVOTable">Propagation Model</div>
            </td>
          </tr>
        </table>
      </div-->

    </div>

    <div id="content-container">


      <!-- ToolBar -->

      <div style="display:none;clear:both" id="section-navigation">
        <div class="draggable unselectable"><img title="<b>HELIO Event Catalog</b><br/>Search for events in several lists." src="${resource(dir:'images/icons/toolbar/square',file:'hec40.png')}" alt="hec" /></div>
        <div class="draggable unselectable"><img title="<b>Instrument Capability Serivce</b><br/>Search for instruments with certain capabilities." src="${resource(dir:'images/icons/toolbar/square',file:'ics40.png')}" alt="Instrument Capabilties Service" /></div>
        <div class="draggable unselectable"><img title="<b>Instrument Location Service</b><br/>Search if an instrument has been in the<br/> right place for a certain observation." src="${resource(dir:'images/icons/toolbar/square',file:'ils40.png')}" alt="Instrument Location Service" /></div>
        <div class="draggable unselectable"><img title="<b>Data Provider Access Service</b><br/>Get access to the actual observations" src="${resource(dir:'images/icons/toolbar/square',file:'dpas40.png')}" alt="Data Provide Access Service" /></div>
        <div class="draggable unselectable"><img title="<b>Upload external data as VOTable</b>" src="${resource(dir:'images/icons/toolbar/square',file:'vot40.png')}" alt="Upload a VOTable" /></div>
        <div class="draggable unselectable"><img title="<b>Upload external data as VOTable</b>" src="${resource(dir:'images/icons/toolbar/square',file:'image40.png')}" alt="Upload a VOTable" /></div>

        <div id="clearSystem" style="float:right"><img title="Reset System" src="${resource(dir:'images/icons/toolbar',file:'delet40.png')}" alt="Reset System" /></div>
      </div>

      <div class="unselectable"style="color:white">Helio</div>

      <!-- History  -->
      <!--div><img id="scroller_left" style="float:left;display:inline;" height="60px" src="${resource(dir:'images/icons/toolbar',file:'scroller_l.png')}" alt="Angry face" /></div-->
       <!--select onchange="fnOnChangeHistoryFilterSelect(this);" style="margin-top:15px;float:right"><option selected="yes" >all</option><option>results</option><option>selections</option><option>actions</option></select-->
        <!--div><img id="scroller_right" style="float:right;display:inline;"height="60px" src="${resource(dir:'images/icons/toolbar',file:'scroller_r.png')}" alt="Angry face" /></div-->
        <!--div><img style="float:right;display:inline;margin-top:15px"height="30px" id="clearButton" src="${resource(dir:'images/icons/toolbar',file:'delet40.png')}" alt="Angry face" /></div-->
      <div style="display:block;" id="history">
        
        <div id="historyContent"></div>

       


      </div> <!-- History -->


      <!-- Content -->
      <div style="display:block;" id="content">
        <!-- 1st level droppable -->
        <div style="border:none" id="droppable-inner" >
          <g:render template="templates/displayable_content" />
        </div>
      </div>

      <div id="responseDivision" style="width:858px;display:block"></div>


    </div>
  </div>
</body>
</html>