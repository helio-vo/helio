<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <title>Search Flare</title>
    <g:javascript library="FancyZoom"/>
    <g:javascript library="FancyZoomHTML"/>
</head>
<body>
<div class="body">
    <h1>HEDC Search</h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>

    <div class="flare_left">
        <g:form action="search" method="get">
            <fieldset>
                <legend>Search parameters</legend>
                <p>
                    <label>Start:</label>
                    <g:datePicker name="startDate" value="${new Date(102,0,1)}" precision="day" years="${2002..(latestFlareDate.getYear()+1900)}"/>
                    <img src="${createLinkTo(dir: 'images', file: 'calendar.png')}" alt="calendar" id="startCalendarButton" class="calendarButton">
                    <g:select name="startDateHour" from="${0..23}" optionValue="${{it<10?'0' + it.toString():it}}" value="0"/> :
                    <g:select name="startDateMinutes" from="${0..59}" optionValue="${{it<10?'0' + it.toString():it}}" value="0"/>
                    <g:checkBox name="singleDaySearch" id="singleDaySearch" style="margin-left:10px" onclick="toggleEndDateForm();"/><label for="singleDaySearch" style="float:none; display:inline">search single day</label>
                    <input type="text" name="startDateValue" id="startDateValue" value="2002-1-1" style="display:none;"/>
                </p>

                <p>
                    <label>End:</label>
                    <g:datePicker name="endDate" value="${latestFlareDate}" precision="day" years="${2002..(latestFlareDate.getYear()+1900)}"/>
                    <img src="${createLinkTo(dir: 'images', file: 'calendar.png')}" alt="calendar" id="endCalendarButton" class="calendarButton">
                    <g:select name="endDateHour" from="${00..23}" optionValue="${{it<10?'0' + it.toString():it}}" value="23"/> :
                    <g:select name="endDateMinutes" from="${0..59}" optionValue="${{it<10?'0' + it.toString():it}}" value="59"/>
                    <input type="text" name="endDateValue" id="endDateValue" value="${latestFlareDate.getYear() + 1900}-${latestFlareDate.getMonth() + 1}-${latestFlareDate.getDate()}" style="display:none;"/>
                </p>
                <p>
                    <label>GOES Class:</label>
                    <input type="text" id="classSpecificationFrom" name="classSpecificationFrom" size="7"/> .. <input type="text" id="classSpecificationTo" name="classSpecificationTo" size="7"/> <img src="${createLinkTo(dir: 'images', file: 'info.png')}" longdesc="<b>Supports lower limit, upper limit and range</b><br /><i>Examples:</i><br />C7.6 .. M1.8<br />B1.8 .. ____<br />____ .. C8.5" class="tooltip_icon" />
                </p>
                <p>
                    <label>Distance To Sun Center: [arcsec]</label>
                    <input type="text" id="distanceSunFrom" name="distanceSunFrom" size="7"/> .. <input type="text" id="distanceSunTo" name="distanceSunTo" size="7"/> <img src="${createLinkTo(dir: 'images', file: 'info.png')}" longdesc="<b>Supports lower limit, upper limit and range</b><br /><i>Examples:</i><br />0 .. 500<br />500 .. ____<br />____ .. 500" class="tooltip_icon" />
                </p>
                <p>
                    <label>Number Of Sources:</label>
                    <input type="text" id="sourcesFrom" name="sourcesFrom" size="7"/> .. <input type="text" id="sourcesTo" name="sourcesTo" size="7"/> <img src="${createLinkTo(dir: 'images', file: 'info.png')}" longdesc="<b>Supports lower limit, upper limit and range</b><br /><i>Examples:</i><br />0 .. 5<br />5 .. ____<br />____ .. 5" class="tooltip_icon" />
                </p>
								<p>
                    <label>Event-Key:</label>
                    <input type="text" id="id" name="id" size="15"/>
                </p>
            </fieldset>
            <span class="button"><input class="save" type="submit" value="Search"/></span>
        </g:form>
    </div>
    <div class="flare_right">
        <fieldset>
            <legend>Latest 10 Flares</legend>
            <table cellpadding="0" cellspacing="0">
                <thead>
                <tr>
                    <th width="90">Image</th>
                    <th width="200">Start Date (min)</th>
                    <th width="200">End Date (max)</th>
                    <th>GOES Class</th>
                    <th>X Pos [arcsec]</th>
                    <th>Y Pos [arcsec]</th>
                    <th>Radial Offset [arcsec]</th>
                </tr>
                </thead>
                <g:each in="${flareInstanceList.results}" status="i" var="flareInstance">
                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        <td>
                            <g:if test="${flareInstance.thumbImage == 'noImg'}">
                                <g:link action="show" id="${flareInstance.id}">
                                    <img src="${createLinkTo(dir: 'images', file: 'no-image.png')}" width="50px" height="50px" alt="show details"/>
                                </g:link>
                            </g:if>
                            <g:else>
                                <g:link action="show" id="${flareInstance.id}" style="text-decoration:none">
                                    <img src="http://hercules.ethz.ch:8081/archive/${flareInstance.thumbImage}" width="50px" height="50px" alt="show details"/>
                                </g:link>
                                &nbsp;
                                <a href="http://hercules.ethz.ch:8081/archive/${flareInstance.thumbImage}">
                                    <img class="zoom" src="${createLinkTo(dir: 'images', file: 'zoom_bg.png')}" alt="quick look"/>
                                </a>
                            </g:else>
                        </td>

                        <td>${fieldValue(bean: flareInstance, field: 'minStartDate')}</td>

                        <td>${fieldValue(bean: flareInstance, field: 'maxEndDate')}</td>

                        <td>${fieldValue(bean: flareInstance, field: 'classSpecification')}</td>

                        <td>${fieldValue(bean: flareInstance, field: 'posX')}</td>

                        <td>${fieldValue(bean: flareInstance, field: 'posY')}</td>

                        <td>${fieldValue(bean: flareInstance, field: 'distanceSun')}</td>

                    </tr>
                </g:each>
            </table>
        </fieldset>
    </div>
</div>
<script type="text/javascript">
    document.observe("dom:loaded", function(e) {
        Calendar.setup({
            dateField     : 'startDateValue',
            triggerElement : 'startCalendarButton',
            selectHandler: function(e) {
                var year = e.date.getYear();
                if (year < 999)
                    year += 1900
                $('startDate_year').value = year;
                $('startDate_month').value = e.date.getMonth() + 1;
                $('startDate_day').value = e.date.getDate();
                $('startDateValue').value = year + "-" + (e.date.getMonth() + 1) + '-' + e.date.getDate();
            }
        });
        Calendar.setup({
            dateField     : 'endDateValue',
            triggerElement : 'endCalendarButton',
            selectHandler: function(e) {
                var year = e.date.getYear();
                if (year < 999)
                    year += 1900
                $('endDate_year').value = year;
                $('endDate_month').value = e.date.getMonth() + 1;
                $('endDate_day').value = e.date.getDate();
                $('endDateValue').value = year + "-" + (e.date.getMonth() + 1) + '-' + e.date.getDate();
            }
        });
    });

    document.observe("dom:loaded", function(e) {
        var baseurl = "${createLinkTo(dir: '')}";
        setupZoom();
        
        toggleEndDateForm()
    });

    function toggleEndDateForm() {
        yearForm = $('endDate_year');
        dayForm = $('endDate_day');
        monthForm = $('endDate_month');
        singleDay = $('singleDaySearch');
        yearForm.disabled = singleDay.checked;
        dayForm.disabled = singleDay.checked;
        monthForm.disabled = singleDay.checked;
    }

</script>
</body>
</html>
