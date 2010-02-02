
<%@ taglib prefix="s" uri="/struts-tags" %>
  
  
    				 <table id="colTable" width="100%" border="0" cellspacing="0" cellpadding="0">
					 		<%
						        int i=0;
						    %>
				        	<s:iterator value="columnTO" status="rowstatus" id="colTO">
				        	 <%
						        i++;
						     %>
					           <s:if test="#rowstatus.odd == true">
									<s:set name="classType" value="'PopupDataRow'"/>
								</s:if>
								<s:else>
									<s:set name="classType" value="'PopupAltDataRow'"/>
								</s:else>
								<tr class='<s:property value="#classType"/>' width="100%" id="colRowId<%=i%>" onclick="javascript: functionDoRowSel('<%=i%>');">
							   		<td height="20" align="center" valign="middle">										
										<s:checkbox name="sColumnName" fieldValue='%{#colTO.getColumnName()}'   theme="simple"/>
									</td>
									<td align="left" valign="middle"><s:property value="#colTO.getColumnName()"/>  </td>							
							    </tr>	
							</s:iterator>
						
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr class="bgline">
    					<td height="2"><img src="../Images/spacer.gif" width="1" height="1"></td>
				    </tr>      
				    <tr>
   					 <td>
   					  <table width="100%" border="0" cellspacing="0" cellpadding="0">
						    <tr align="right" valign="middle">
								<td width="63%" height="25" align="center">&nbsp;
									<a href="javascript:doneColumnAdd();" onMouseOver="MM_swapImage('Done','','../Images/but_done_click.gif',1)" onMouseOut="MM_swapImgRestore()"><img src="../Images/but_done_normal.gif" name="Done"  border="0" align="absmiddle" id="done"></a>&nbsp;
									<a href="javascript:closeColumnSearchDiv();" onMouseOver="MM_swapImage('addcol','','../Images/but_addColumn_click.gif',1)" onMouseOut="MM_swapImgRestore()"><img src="../Images/but_addColumn_normal.gif" name="Close"  border="0" align="absmiddle" id="addcol"></a>
								</td>
					        </tr>
					     </table>
					</td>
				  </tr>			
					</table>
 

