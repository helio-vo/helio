class FlareController {
	def index = {
		def flareInstanceList = Flare.search([offset: 0, max: 10, order: "desc", sort: 'maxEndDate']) {
			gt("minStartDate", new GregorianCalendar(1900, 0, 1).getTime())
		}

		Date endDateTemp = flareInstanceList.results[0].maxEndDate
		Date latestFlareDate = new Date(endDateTemp.getYear(), endDateTemp.getMonth(), endDateTemp.getDate(), 23, 59)
		return ['flareInstanceList': flareInstanceList, 'latestFlareDate': latestFlareDate]
	}

	def show = {
		def flareInstance = Flare.get(params.id)

		if (!flareInstance) {
			flash.message = "Flare not found with id ${params.id}"
			redirect(action: list)
		}
		else { return [flareInstance: flareInstance] }
	}


	def search = {
		def flareInstanceList = Flare.search([offset: 0, max: 10, order: "desc", sort: 'maxEndDate']) {
			gt("minStartDate", new GregorianCalendar(1900, 0, 1).getTime())
		}
		Date endDateTemp = flareInstanceList.results[0].maxEndDate
		Date latestFlareDate = new Date(endDateTemp.getYear(), endDateTemp.getMonth(), endDateTemp.getDate(), 23, 59)

		if (!params.max) params.max = 50
		if (!params.offset) params.offset = 0
		if (!params.sort) params.sort = "minStartDate"
		if (!params.order) params.order = "desc"

		boolean singleDay = params.singleDaySearch=="on" ? true : false
        
		int sYear = Integer.valueOf(params.startDate_year).intValue()
		int sMonth = Integer.valueOf(params.startDate_month).intValue()
		int sDay = Integer.valueOf(params.startDate_day).intValue()
		int sHour = Integer.valueOf(params.startDateHour).intValue()
		int sMinute = Integer.valueOf(params.startDateMinutes).intValue()

		// check for single day search, read form fields only if period search
		int eYear = singleDay ? 0 : Integer.valueOf(params.endDate_year).intValue()
		int eMonth = singleDay ? 0 : Integer.valueOf(params.endDate_month).intValue()
		int eDay = singleDay ? 0 : Integer.valueOf(params.endDate_day).intValue()
		int eHour = Integer.valueOf(params.endDateHour).intValue()
		int eMinute = Integer.valueOf(params.endDateMinutes).intValue()

		if(params.id != ""){
			Flare f = Flare.get(params.id)

			if(f != null)
			redirect(action:show,id:f.id)
		}

		// do search
		def flareList = Flare.search([offset: params.offset, max: params.max, sort: params.sort, order: params.order]) {

			must{
				should{
					must(le("minStartDate", new GregorianCalendar(sYear, sMonth - 1, sDay, sHour, sMinute).getTime()))
					must(ge("maxEndDate", new GregorianCalendar(sYear, sMonth - 1, sDay, sHour, sMinute).getTime()))
				}
				should{
					must(ge("minStartDate", new GregorianCalendar(sYear, sMonth - 1, sDay, sHour, sMinute).getTime()))
					if(singleDay){
						// search only on a specified single day
						must(le("minStartDate", new GregorianCalendar(sYear, sMonth - 1, sDay, eHour, eMinute).getTime()))
					}else{
						// search period (default)
						must(le("minStartDate", new GregorianCalendar(eYear, eMonth - 1, eDay, eHour, eMinute).getTime()))
					}
				}
			}

			// Hack to eliminate invalid time periods (e.g. end before start)
			must(ge("maxEndDate", new GregorianCalendar(sYear, sMonth - 1, sDay, sHour, sMinute).getTime()))

			if (params.classSpecificationFrom != "")
			must{
				should{
					ge("classSpecification", params.classSpecificationFrom.toLowerCase())
					prefix("classSpecification", params.classSpecificationFrom.toLowerCase())
				}
			}
			if (params.classSpecificationTo != "")
			must{
				should{
					le("classSpecification", params.classSpecificationTo.toLowerCase())
					prefix("classSpecification", params.classSpecificationTo.toLowerCase())
				}
			}
			if (params.distanceSunFrom != "" && params.distanceSunFrom.isInteger())
			must(ge("distanceSun", params.distanceSunFrom))
			if (params.distanceSunTo != "" && params.distanceSunTo.isInteger())
			must(le("distanceSun", params.distanceSunTo))
			if (params.sourcesFrom != "" && params.sourcesFrom.isInteger())
			must(ge("sources", params.sourcesFrom))
			if (params.sourcesTo != "" && params.sourcesTo.isInteger())
			must(le("sources", params.sourcesTo))
		}

		[flareInstanceList: flareList, latestFlareDate:latestFlareDate]

	}
}
