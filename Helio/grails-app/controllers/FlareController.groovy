class FlareController {
    
    def index = { redirect(action:list,params:params) }

   def test = {  }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {

        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ flareInstanceList: Flare.list( params ), flareInstanceTotal: Flare.count() ]
    }

    def show = {
        def flareInstance = Flare.get( params.id )

        if(!flareInstance) {
            flash.message = "Flare not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ flareInstance : flareInstance ] }
    }

    def delete = {
        def flareInstance = Flare.get( params.id )
        if(flareInstance) {
            try {
                flareInstance.delete(flush:true)
                flash.message = "Flare ${params.id} deleted"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Flare ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "Flare not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def flareInstance = Flare.get( params.id )

        if(!flareInstance) {
            flash.message = "Flare not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ flareInstance : flareInstance ]
        }
    }

    def update = {
        def flareInstance = Flare.get( params.id )
        if(flareInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(flareInstance.version > version) {
                    
                    flareInstance.errors.rejectValue("version", "flare.optimistic.locking.failure", "Another user has updated this Flare while you were editing.")
                    render(view:'edit',model:[flareInstance:flareInstance])
                    return
                }
            }
            flareInstance.properties = params
            if(!flareInstance.hasErrors() && flareInstance.save()) {
                flash.message = "Flare ${params.id} updated"
                redirect(action:show,id:flareInstance.id)
            }
            else {
                render(view:'edit',model:[flareInstance:flareInstance])
            }
        }
        else {
            flash.message = "Flare not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def flareInstance = new Flare()
        flareInstance.properties = params
        return ['flareInstance':flareInstance]
    }

    def save = {
        def flareInstance = new Flare(params)
        if(!flareInstance.hasErrors() && flareInstance.save()) {
            flash.message = "Flare ${flareInstance.id} created"
            redirect(action:show,id:flareInstance.id)
        }
        else {
            render(view:'create',model:[flareInstance:flareInstance])
        }
    }


    
    def search = {
        if (request.method == 'POST') {
            FlareQuery query = new FlareQuery()
            bindData(query, params)
            def criteria = Flare.createCriteria()
            def results = criteria {
              and{
                  like('goes', '%' + query.goes + '%')
                  between('startDate',query.minDate,query.maxDate + 1)
              }
            }
            render(view:'list', model:[ flareInstanceList: results, flareInstanceTotal: results.count() ])
        }
    }

}


