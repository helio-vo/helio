function SwitchMenu(obj,obj1,obj2){
	if(document.getElementById){
	var el = document.getElementById(obj);
	var e2 = document.getElementById(obj1);
	var e3 = document.getElementById(obj2);
	var ar = document.getElementById("masterdiv").getElementsByTagName("span");
	//DynamicDrive.com change
	var ar2 = document.getElementById("masterdiv").getElementsByTagName("td");

	var e=document.getElementById("div");


	//DynamicDrive.com change
		if(el.style.display != "block"){ //DynamicDrive.com change
			el.style.display = "block";
			e2.style.display = "none";
			e3.style.display = "block";
			//e.style.height = '390px';	
		}else{
			el.style.display = "none";
			e2.style.display = "block";
			e3.style.display = "none";
			//e.style.height = '600px';	

		}
	}

}

function toolTipObj(id,description){
	this.id=id
	this.description=description
	
}

function commonAjaxOpen(request, url) {
	
	var s = "&"
	if (url.indexOf("?") == -1) s = "?";
	request.open("POST",url + s + (new Date()).getTime(),false);
}

function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}
function ltrim(stringToTrim) {
	return stringToTrim.replace(/^\s+/,"");
}
function rtrim(stringToTrim) {
	return stringToTrim.replace(/\s+$/,"");
}



function confirmYesNo(str)
{
    execScript('n = msgbox("'+str+'","4132","Display/Export")', "vbscript");
    return(n == 6);
}



 function confirmDel(ver,sessionKey){
			var inputtype;
			inputtype=confirmYesNo("The size of the file is greater than threshold value. Click Yes to show first 2000 rows and NO to export to excel?")
	    	if(inputtype)
	    	{		    
		   		document.forms[0].action ="displayReportDetails.action?bLoad=true&sReqSessionKey="+sessionKey;
  				document.forms[0].method = "post";
  				document.forms[0].submit();
  			}
			else{
				document.forms[0].action ="getAdhocExcelReport.action?status="+inputtype;
  				document.forms[0].method = "post";
  				document.forms[0].submit();
				
			}
		
	}
 

var dtCh= "/";
var minYear=1900;
var maxYear=2100;

function isInteger(s){
	var i;
    for (i = 0; i < s.length; i++){   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}

function stripCharsInBag(s, bag){
	var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++){   
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}

function daysInFebruary (year){
	// February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}
function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31
		if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
		if (i==2) {this[i] = 29}
   } 
   return this
}

function isDate(dtStr){
	var daysInMonth = DaysArray(12)
	
	var pos1=dtStr.indexOf(dtCh)
	var pos2=dtStr.indexOf(dtCh,pos1+1)
	var strMonth=dtStr.substring(0,pos1)
	var strDay=dtStr.substring(pos1+1,pos2)
	var strYear=dtStr.substring(pos2+1)
	strYr=strYear
	if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1)
	if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1)
	for (var i = 1; i <= 3; i++) {
		if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
	}
	month=parseInt(strMonth)
	day=parseInt(strDay)
	year=parseInt(strYr)
	if (pos1==-1 || pos2==-1){
		return false
	}
	if (strMonth.length<1 || month<1 || month>12){
		return false
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		return false
	}
	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		return false
	}
	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		return false
	}
	return true
}


function validateFormat(dtStr)
{
	var newStr="";
	dtStr = replaceAll(dtStr,"-","/");
		
	var pos1=dtStr.indexOf(dtCh);
	if(pos1==-1)
	{
			var strMonth=dtStr.substring(0,2);
			var strDay=dtStr.substring(2,4);
			var strYear=dtStr.substring(4);
			newStr=strMonth+"/"+strDay+"/"+strYear;
	}
	else
	{
		newStr=dtStr;
	}
	return newStr;
}

function replaceAll( str, from, to ) {

    var idx = str.indexOf( from );


    while ( idx > -1 ) {
        str = str.replace( from, to ); 
        idx = str.indexOf( from );
    }

    return str;
}

//Added by Khadija for acceptin different date formats & checkin it 
function checkInputDateFormat(txtObject) 
{ 
        var flag = true; 
        var txtDate = txtObject.value; 
        var temp = ""; 
        var txtDate_len = txtObject.value.length; 
       // alert(txtDate_len); 
        var st=""; 
        
        //get todays date 
        var now  = new Date(); 
        var month = now.getMonth()+1; 
        var day = now.getDate(); 
        var year = now.getYear(); 
        
        if(day<10) 
                day = "0"+day; 
        if(month<10) 
                month = "0"+month; 
        
        if(txtDate_len ==0)      
	{
			return;
	}
        
        
        //if the entered date is mm/dd/yyyy the length will be 10 
        if(txtDate_len ==10) 
                { 
                        
                         temp=validateFormat(txtDate); 
                        
                         if (isDate(temp)==false) 
                                { 
                                        alert("Please Enter valid Date in MM/DD/YYYY format "); 
                                        flag=false; 
                                        
                                        
                                } 
                } 
        else if(txtDate_len == 8) //in mmddyyyy 
                {         
                        
                        
                        temp=validateFormat(txtDate); 
                        
                        if (isDate(temp)==false) 
                                { 
                                        alert("Please Enter valid Date in MMDDYYYY format"); 
                                        flag=false; 
                                        
                                } 
                        else //date entered is valid but has to be converted in  mm/dd/yyyy format 
                                { 
                                        txtObject.value=temp; 
                                }         
                        
                 } 
        else if(txtDate_len == 6) //in mmyyyy         
                { 
                        var month1 = txtDate.substring(0,2); 
                        var year1 = txtDate.substring(2); 
                        var temp_date = month1+"/01/"+year1; 
                        
                        st = validateFormat(temp_date); 
                        
                        if (isDate(st)==false) 
                                { 
                                        alert("Please Enter valid Date in MMYYYY format"); 
                                        flag=false; 
                                        
                                } 
                        else 
                                { 
                                        txtObject.value = temp_date; 
                                } 
                 } 
        else //Does not support any of the formats 
                { 
                        //alert("Not supported format"); 
                        flag=false; 
                }         
        
                if(!flag)//if false set to current date 
                        { 
                                //alert("i set") 
                                txtObject.value = month+"/"+day+"/"+year;         
                        } 
                
        return ;         
}         

function getDetails(){
	   document.frName.target="_self";
	   document.frName.action="commonaction.action";
	   document.frName.method="post";
	   document.frName.submit();
}





function functionDoRowSel(rowID)
	{
		var row = document.getElementById("colRowId"+rowID);
		var cBox = document.getElementsByName("sColumnName");
		//alert(row+" rowID :"+rowID+"cBox :"+cBox);
		if(row.className=="SelectionRow")
		{
			cBox[rowID-1].checked=false;
			if(rowID%2==0)
			{
				row.className = "PopupAltDataRow" ;
			}
			else
			{
				row.className  = "PopupDataRow";
			}
		}
		else
		{
			cBox[rowID-1].checked=true;
			row.className="SelectionRow";
		}
	}


var columnAjaxRequest=null;


function getTableColumns(){
	//var filterValues = document.forms[0].eventFilterNameValue;
	document.getElementById("columnTableDiv").style.display = "block";		
			
	commonColumnAJAXRequest();
}

//common AJAX call
function commonColumnAJAXRequest()
{
	var tableName = document.getElementById("cmbDatabaseTableList").value;
	
	var param = "tableName="+tableName;
	
	var url="getTableColumnList.action";
	
	try{
		columnAjaxRequest=new ActiveXObject("Microsoft.XMLHTTP");
	}catch(other){
	  try{
			columnAjaxRequest=new XMLHttpRequest();
	   }catch(browser){alert("Ajax Not Supported")}
	}
	columnAjaxRequest.open("POST",url,true);
	columnAjaxRequest.onreadystatechange=function(){
				var ready=columnAjaxRequest.readyState;
				if(ready == 4)
				{
					var data=columnAjaxRequest.responseText;
					document.getElementById("columnTableDiv").innerHTML=data;
				}	
	}
	
	columnAjaxRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	columnAjaxRequest.send(param);	
	
}

function addColumnsOfSelectedTable()
{
	var selectedColumnValues="";
	var sColNameDt=document.forms[0].sColumnName;
	for (i=0;i<sColNameDt.length;i++){
		if(sColNameDt[i].checked){										 
			selectedColumnValues+= sColNameDt[i].value+'::';										
		}
	}
	
	var timeConstraint=document.forms[0].timeConstraint.value;
	var instrumentConstraint=document.forms[0].instrumentConstraint.value;
	var coordinateConstraint=document.forms[0].coordinateConstraint.value;
	var tableName=document.forms[0].cmbDatabaseTableList.value;
	//alert(" selectedColumnValues : "+selectedColumnValues +"timeConstraint "+timeConstraint+"instrumentConstraint "+instrumentConstraint+"coordinateConstraint "+coordinateConstraint );
	
	if(selectedColumnValues==null || selectedColumnValues==""){
		alert("Please select atleast one column name.");
		return true;
	}else{
		selectedColumnValues=selectedColumnValues.substring(0,selectedColumnValues.length-2);
	}
	

	//create the Filter Row
	var table = document.getElementById("addedColumns");
	var rows = table.rows;
	var lastRow = rows.length;

	//alert("rows "+rows);
	var rowsCount=0;
	if(rows.length>1){
		rowsCount = parseInt(rows.length-1);
	}
	
	//alert(" rowsCount : "+rowsCount);
	var hiddenValue=tableName+"^$$^"+selectedColumnValues+"^$$^"+timeConstraint+"^$$^"+instrumentConstraint+"^$$^"+coordinateConstraint;
	var columnHidValue= '<input type="hidden" name="addedTableDetails" id="addedTableDetails'+rowsCount+'" value="'+hiddenValue+'">';
	
	var previousRowClassName="";
	for(var i=0;i<rows.length;i++){
		if(rows[i].className!=null && rows[i].className!=""){
			previousRowClassName=rows[i].className;
		}
	}
	
	//alert("previousRowClassName "+previousRowClassName);
	
	//get new Row Class Name
	var newRowClassName;
	if(previousRowClassName=="PopupAltDataRow"){
		newRowClassName="PopupDataRow";
	}else{
		newRowClassName="PopupAltDataRow";
	}
	
	//alert("table "+table);
	
	var newRow= table.insertRow(lastRow);
	newRow.id="columnRow"+rowsCount;
	newRow.className = newRowClassName;
	
	
	var oCell = newRow.insertCell(0);
	oCell.innerHTML = '<a href="#"><img src="Images/delete.gif"  alt="" border="0" title="Remove Criteria" onClick="removeColumn(this);"></a>';
	oCell.align="left";
	oCell.width=20;
	
	oCell = newRow.insertCell(1);
	oCell.innerHTML =trim(tableName);
	oCell.align="left";
	oCell.style.paddingLeft="30px";
	oCell.width=300;
   	
	oCell = newRow.insertCell(2);
	oCell.innerHTML =trim(replaceAll(selectedColumnValues,"::", ","));
	oCell.align="left";
	oCell.style.paddingLeft="30px";
	oCell.width=300;
	
	oCell = newRow.insertCell(3);
	oCell.innerHTML =trim(timeConstraint);
	oCell.align="left";
	oCell.style.paddingLeft="30px";
	oCell.width=300;
	
	oCell = newRow.insertCell(4);
	oCell.innerHTML =trim(instrumentConstraint);
	oCell.align="left";
	oCell.style.paddingLeft="30px";
	oCell.width=300;
	
	oCell = newRow.insertCell(5);
	oCell.innerHTML =trim(coordinateConstraint)+columnHidValue;
	oCell.align="left";
	oCell.style.paddingLeft="30px";
	oCell.width=300;
	
	var cmbFilter = document.getElementById("cmbDatabaseTableList");
	cmbFilter.remove(cmbFilter.selectedIndex);
	
	document.getElementById("columnTableDiv").style.display = "none";
	
}

function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}
function ltrim(stringToTrim) {
	return stringToTrim.replace(/^\s+/,"");
}
function rtrim(stringToTrim) {
	return stringToTrim.replace(/\s+$/,"");
}
function replaceAll( str, from, to ) {

    var idx = str.indexOf( from );


    while ( idx > -1 ) {
        str = str.replace( from, to ); 
        idx = str.indexOf( from );
    }

    return str;
}
