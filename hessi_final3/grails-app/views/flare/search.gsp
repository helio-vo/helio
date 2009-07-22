<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <title>Flare Search Results</title>
    <g:javascript library="FancyZoom"/>
    <g:javascript library="FancyZoomHTML"/>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="javascript:history.back()">back</a></span>
</div>
<div class="body">
    <h1>Flare Search Results</h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
		<div class="editSearchParameters"><a href="#" onclick="new Effect.toggle('searchParameters', 'blind', { duration: 0.5 });return false;"><img src="${createLinkTo(dir: 'images', file: 'change.png')}" alt="change">edit search parameters</a></div>
		<div class="searchParameters" id="searchParameters" style="display:none;">
				<g:form action="search" method="get">
						<fieldset>
								<legend>Search parameters</legend>
								<p>
										<label>Start:</label>
										<g:datePicker name="startDate" value="${new GregorianCalendar(Integer.valueOf(params.startDate_year), Integer.valueOf(params.startDate_month) - 1, Integer.valueOf(params.startDate_day), 0, 0).getTime()}" precision="day" years="${2002..(latestFlareDate.getYear()+1900)}"/>
										<img src="${createLinkTo(dir: 'images', file: 'calendar.png')}" alt="calendar" id="startCalendarButton" class="calendarButton">
										<g:select name="startDateHour" from="${0..23}" optionValue="${{it<10?'0' + it.toString():it}}" value="${Integer.valueOf(params.startDateHour)}"/> :
										<g:select name="startDateMinutes" from="${0..59}" optionValue="${{it<10?'0' + it.toString():it}}" value="${Integer.valueOf(params.startDateMinutes)}"/>
										<g:checkBox name="singleDaySearch" id="singleDaySearch" style="margin-left:10px" onclick="toggleEndDateForm();"/><label for="singleDaySearch" style="float:none; display:inline">search single day</label>
										<input type="text" name="startDateValue" id="startDateValue" value="2002-1-1" style="display:none;"/>
								</p>

								<p>
										<label>End:</label>
										<g:datePicker name="endDate" value="${params.endDate_year == null ? latestFlareDate : (new GregorianCalendar(Integer.valueOf(params.endDate_year), Integer.valueOf(params.endDate_month) - 1, Integer.valueOf(params.endDate_day), 0, 0).getTime())}" precision="day" years="${2002..(latestFlareDate.getYear()+1900)}"/>
										<img src="${createLinkTo(dir: 'images', file: 'calendar.png')}" alt="calendar" id="endCalendarButton" class="calendarButton">
										<g:select name="endDateHour" from="${00..23}" optionValue="${{it<10?'0' + it.toString():it}}" value="${params.endDateHour != null ? Integer.valueOf(params.endDateHour) : 0}"/> :
										<g:select name="endDateMinutes" from="${0..59}" optionValue="${{it<10?'0' + it.toString():it}}" value="${params.endDateMinutes != null ? Integer.valueOf(params.endDateMinutes) : 0}"/>
										<input type="text" name="endDateValue" id="endDateValue" value="${params.endDate_year != null ? params.endDate_year : (latestFlareDate.getYear() + 1900)}-${params.endDate_month != null ? params.endDate_month: (latestFlareDate.getMonth() + 1)}-${params.endDate_day != null ? params.endDate_day : (latestFlareDate.getDate())}" style="display:none;"/>
								</p>
								<p>
										<label>GOES Class:</label>
										<input type="text" id="classSpecificationFrom" name="classSpecificationFrom" size="7" value="${params.classSpecificationFrom}"/> .. <input type="text" id="classSpecificationTo" name="classSpecificationTo" size="7" value="${params.classSpecificationTo}"/> <img src="${createLinkTo(dir: 'images', file: 'info.png')}" longdesc="<b>Supports lower limit, upper limit and range</b><br /><i>Examples:</i><br />C7.6 .. M1.8<br />B1.8 .. ____<br />____ .. C8.5" class="tooltip_icon" />
								</p>
								<p>
										<label>Distance To Sun Center: [arcsec]</label>
										<input type="text" id="distanceSunFrom" name="distanceSunFrom" size="7" value="${params.distanceSunFrom}"/> .. <input type="text" id="distanceSunTo" name="distanceSunTo" size="7" value="${params.distanceSunTo}"/> <img src="${createLinkTo(dir: 'images', file: 'info.png')}" longdesc="<b>Supports lower limit, upper limit and range</b><br /><i>Examples:</i><br />0 .. 500<br />500 .. ____<br />____ .. 500" class="tooltip_icon" />
								</p>
								<p>
										<label>Number Of Sources:</label>
										<input type="text" id="sourcesFrom" name="sourcesFrom" size="7" value="${params.sourcesFrom}"/> .. <input type="text" id="sourcesTo" name="sourcesTo" size="7" value="${params.sourcesTo}"/> <img src="${createLinkTo(dir: 'images', file: 'info.png')}" longdesc="<b>Supports lower limit, upper limit and range</b><br /><i>Examples:</i><br />0 .. 5<br />5 .. ____<br />____ .. 5" class="tooltip_icon" />
								</p>
								<p>
                    <label>Event-Key:</label>
                    <input type="text" id="id" name="id" size="15" value="${params.id}"/>
                </p>
						</fieldset>
						<span class="button"><input class="save" type="submit" value="Search"/></span>
				</g:form>
		</div>
    <g:if test="${flareInstanceList.total==0}">
        <div class="message">No flares containing all your search terms were found.</div>
    </g:if>
    <g:else>
        <div class="hitInfo">
            Showing flares ${params.offset} - ${(Integer.valueOf(params.offset).intValue() + Integer.valueOf(flareInstanceList.results.size()).intValue())} of total ${flareInstanceList.total} flares
        </div>
        <div class="list">
            <table cellspacing="0" cellpadding="0">
                <thead>
                <tr>
                    <th width="90">Image</th>
										<th>Start Date (min)</th>
                    <th>End Date (max)</th>
                    <th>GOES Class</th>
                    <th>X Pos [arcsec]</th>
                    <th>Y Pos [arcsec]</th>
                    <th>Radial Offset [arcsec]</th>

                </tr>
                </thead>
                <tbody>
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
                </tbody>
            </table>
        </div>

        <div class="paginateButtons">
            <g:paginate controller="flare" action="search" params="[params]" total="${flareInstanceList.total}" prev="&lt; previous" next="next &gt;"/>
        </div>

    </g:else>
</div>
<script type="text/javascript">
    document.observe("dom:loaded", function(e) {
        var baseurl = "${createLinkTo(dir: '')}";
        setupZoom();
				toggleEndDateForm();
    });

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

    function toggleEndDateForm(){
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
