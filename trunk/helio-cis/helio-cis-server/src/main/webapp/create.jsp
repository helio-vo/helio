<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HELIO Community Interaction Service</title>
</head>
<BODY>
<img src="images/helio-logo.jpg"/> 
<BR>HELIO Community Interaction Service<BR> 
		<FORM METHOD=POST ACTION="createDone.jsp">
		<BR>
		Enter your name       : <INPUT TYPE=TEXT NAME=userName SIZE=20><BR>
		Enter your email      : <INPUT TYPE=TEXT NAME=userEmail SIZE=20><BR>
		Enter your password   : <INPUT TYPE=PASSWORD NAME=newPwd1 SIZE=20><BR>
		Re-enter your password: <INPUT TYPE=PASSWORD NAME=newPwd2 SIZE=20><BR>
		<P><INPUT TYPE=SUBMIT>
		</FORM>
</BODY>
</HTML>