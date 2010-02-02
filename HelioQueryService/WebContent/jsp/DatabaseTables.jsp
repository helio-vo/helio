<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">


<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java"%>
<%
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache");  
	response.setDateHeader ("Expires", 0); 
%>
 
<HTML lang=en xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
  <HEAD>
    <TITLE> 
    		HelioSystem - Instruments 
    </TITLE>
    <META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

    <%!String contextPath="n";%>
    <%contextPath=request.getContextPath();%>
    <LINK rel="stylesheet" href="<%=contextPath%>/Style/Style.css" type="text/css">
      <script src="<%=contextPath%>/Scripts/common.js"></script>
      <script src="<%=contextPath%>/Scripts/toolTip.js"></script>
      <script src="<%=contextPath%>/Scripts/menu.js"></script>
  </HEAD>
   <script type="text/javascript">
			var CalendarToolTip=new Array();
			CalendarToolTip[0]=new toolTipObj('1','Date Picker')
		</script>
  <BODY leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  onLoad="MM_preloadImages('<%=contextPath%>/Images/but_save_click.gif','<%=contextPath%>Images/but_cancel_click.gif');">
    <DIV id="ToolTip">
    </DIV>
    
    <form name="frmAdmin" id="frmAdmin" method="post" action="">
     <s:hidden name="insId" theme="simple"/>
    <TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
        <TR>
          <TD align="left" valign="top">
            <%@ include file="./includes/MenuHeader.jsp" %>
          </TD>
        </TR>
		<TR>
          <TD align="left" valign="top">
            <!-- Body starts here -->
            <TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
              <TR>
                <TD  align="left" valign="middle" class="txtError"> 
                	<!--Error message can be displayed here....-->
                	<s:actionerror/>
                </TD>
              </TR>
            </TABLE>
           </TD>
        </TR>
        <TR>
          <TD align="left" valign="top">
          &nbsp;
          </TD>
         </TR>
		<TR>
			<TD align="left" valign="top">
				<!-- Saved Report section  start-->
				<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
					<TR class="txtHeading">
						<TD height="20">
							List of database tables
						</TD>
						</TR>
				</TABLE>
			</TD>
		</TR>
		
    </table>
  	
		<TABLE width="100%" border="0" cellspacing="1" cellpadding="0" >
			
			<TR height=20 class="PopupAltDataRow">
				<TD nowrap class="txtblackBP" width="15%"> Database tables : </TD>
				<TD nowrap class="txtblackBP" >		
					<s:select name="cmbDatabaseTableList" id="cmbDatabaseTableList" cssClass="stylecombo" list="hmbDatabaseTableList" onchange="javascript:getTableColumns();" listKey="key" listValue="value" theme="simple"  />	
		   		</TD>
			</TR>
			
			
	     </TABLE>
	    <TABLE width="100%" border="0" cellspacing="1" cellpadding="0" >
	    <tr>
	       <td>
               <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tableborderLBlue">
              	 	<tr class="txtPopupHead">
      					  <td height="20" width="100%">  
 								List of column from table <s:property value="#tableName"/>						 
      					  </td>
    				   </tr>
    			   </table>	 
			 </td>
			</tr>
			<tr>
				<td >
					<div id="columnTableDiv" >
					</div>
			    </td>
			</tr>	     
	     </table>
	     
     	<table width="100%">
		<tr>
			 <td align="left" valign="top">
			   <!-- Footer starts here --> 
			 		 <%@ include file="./includes/footer.jsp" %>
			   <!-- Footer ends here -->
			 </td>
		 </tr> 
		</table>
</form>
</body></html>







