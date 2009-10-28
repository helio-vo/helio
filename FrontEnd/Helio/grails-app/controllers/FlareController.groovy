import newpackage.ToolBox;
import newpackage.FlareQuery;
import  data.WorkflowResult;


class FlareController {
    
    def index = { redirect(action:list,params:params) }

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
   def contact = {}//end contact
    def search = {
        Flare.executeUpdate("delete from Flare");

        if (request.method == 'POST') {

            FlareQuery query = new FlareQuery();
            bindData(query, params);
            Stack<Stack<String>> mystack = ToolBox.queryWebService(query.minDate,query.maxDate);

            for (Iterator<Stack<Stack<String>>> iter = mystack.iterator(); iter.hasNext();) {
                Stack<String> resultStack = iter.next();
                WorkflowResult wfr = new WorkflowResult(resultStack);
                new Flare(goes_id:wfr.getGoes_id(),
                    ntime_start:wfr.getNtime_start(),
                    time_start:wfr.getTime_start(),
                    time_peak:wfr.getTime_peak(),
                    time_end:wfr.getTime_end(),
                    ntime_end:wfr.getNtime_end(),
                    nar:wfr.getNar(),
                    latitude:wfr.getLatitude(),
                    longitude:wfr.getLongitude(),
                    long_carr:wfr.getLong_carr(),
                    xray_class:wfr.getXray_class(),
                    optical_class:wfr.getOptical_class(),
                    hessi_measurementStart:wfr.getHessi_measurementStart(),
                    hessi_measurementEnd:wfr.getHessi_measurementEnd(),
                    hessi_flareNr:wfr.getHessi_flareNr(),
                    hessi_measurementPeak:wfr.getHessi_measurementPeak(),
                    hessi_peakCS:wfr.getHessi_peakCS(),
                    hessi_totalCounts:wfr.getHessi_totalCounts(),
                    hessi_energyKeVFrom:wfr.getHessi_energyKeVFrom(),
                    hessi_energyKeVTo:wfr.getHessi_energyKeVTo(),
                    hessi_xPos:wfr.getHessi_xPos(),
                    hessi_yPos:wfr.getHessi_yPos(),
                    hessi_radial:wfr.getHessi_radial(),
                    hessi_AR:wfr.getHessi_AR(),
                    phoenix2_measurementStart:wfr.getPhoenix2_measurementStart(),
                    phoenix2_flareNr:wfr.getPhoenix2_flareNr(),
                    phoenix2_peakCS:wfr.getPhoenix2_peakCS(),
                    phoenix2_totalCounts:wfr.getPhoenix2_totalCounts(),
                    phoenix2_energyKeVFrom:wfr.getPhoenix2_energyKeVFrom(),
                    phoenix2_energyKeVTo:wfr.getPhoenix2_energyKeVTo(),
                    phoenix2_xPos:wfr.getPhoenix2_xPos(),
                    phoenix2_yPos:wfr.getPhoenix2_yPos(),
                    phoenix2_radial:wfr.getPhoenix2_radial(),
                    phoenix2_AR:wfr.getPhoenix2_AR(),
                    phoenix2_urlPhaseFITSGZ:wfr.getPhoenix2_urlPhaseFITSGZ(),
                    phoenix2_urlIntensityFITSGZ:wfr.getPhoenix2_urlIntensityFITSGZ(),
                ).save();


            }//end for
            redirect(action:list)
        }
    }
}
