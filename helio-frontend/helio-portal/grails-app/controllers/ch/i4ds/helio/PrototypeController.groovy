package ch.i4ds.helio;

import ch.i4ds.*;
import java.text.DateFormat
import java.text.SimpleDateFormat

import javax.xml.bind.JAXBContext
import javax.xml.bind.Unmarshaller
import javax.xml.transform.stream.StreamSource

import net.ivoa.xml.votable.v1.*
import ch.i4ds.helio.frontend.parser.*
import ch.i4ds.helio.frontend.query.*
import eu.heliovo.clientapi.frontend.*
import eu.heliovo.clientapi.frontend.ResultVT;
import eu.heliovo.clientapi.model.catalog.HelioCatalogDao;
import eu.heliovo.clientapi.model.catalog.impl.DpasDao;
import eu.heliovo.clientapi.model.catalog.impl.HecDao
import eu.heliovo.clientapi.model.catalog.impl.HelioCatalogDaoFactory;
import eu.heliovo.clientapi.model.field.DomainValueDescriptor
import eu.heliovo.clientapi.model.field.HelioField
import eu.heliovo.registryclient.HelioServiceName;
import org.springframework.web.context.request.RequestContextHolder


class PrototypeController {

    def DataQueryService;
    def TableInfoService;
    def ResultVTManagerService;


    def getTaskContent = {
        

        
        
        
    }


   

    /**
     * Action to asynchronously get advanced columns of a service.
     * Expects parameter: serviceName=SERVICE_NAME, catalog=CATALOG_NAME.
     */
    def getAdvancedParams = {
        //log.info("getAdvancedParams =>" +params);

        if(params.serviceName == null)
        throw new java.lang.IllegalArgumentException("Parameter 'service' must be set.");
        if(params.catalog == null)
        throw new java.lang.IllegalArgumentException("Parameter 'catalog' must be set.");

        def template;  // name of the template to use
        if(params.serviceName == "ics")	{
            def hash = TableInfoService.serviceMethod("files/tablesics.xml");
            def catalog = hash.get(params.catalog);
            template = "ics_" + params.catalog;
            render template:'templates/' + template, bean:catalog, var:'catalog';
        } else if(params.serviceName == "ils")	{
            def hash = TableInfoService.serviceMethod("files/tablesils.xml");
            def catalog = hash.get(params.catalog);
            template = "ils_" + params.catalog;
            render template:'templates/' + template, bean:catalog, var:'catalog';
        } else {
            throw new java.lang.IllegalArgumentException("Service " + params.serviceName + " is not supported through this method.");
        }
    }
	
    /**
     * Action to asynchronously get the HEC columns.
     * Expects parameter: catalog=CATALOG_NAME.
     */
    def getHecColumns = {
        log.info("getHecColumns =>" +params);
			
        if(params.catalog == null)
        throw new java.lang.IllegalArgumentException("Parameter 'catalog' must be set.");

        HelioCatalogDao hecDao = HelioCatalogDaoFactory.getInstance().getHelioCatalogDao(HelioServiceName.HEC.getName());
        def catalog = hecDao.getCatalogById(params.catalog);
                
        if (catalog != null) {
            render template:'templates/columns_extended', bean:catalog, var:'catalog';
        } else {
            render "<p>Unable to load catalog defintion with id '" + params.catalog + "'</p>";
        }
    }

    /**
     * Send a query to the HEC and render the result in an jquery datatables object.
     */
    def asyncHecQuery ={
        log.info("asyncHecQuery =>" +params);
        
        
        try {
            // prepare query
                    
            ArrayList<String> startTime= new ArrayList<String>(); // initialize lists for web service request
            ArrayList<String> endTime= new ArrayList<String>();
		
            // use ingested parameter list
            if(params.maxDateList != null && params.minDateList != null) {
                //startTime = [params.minDateList.split(",")].flatten();
                startTime = [params.minDateList].flatten();
                endTime = [params.maxDateList].flatten();
                //endTime = [params.maxDateList.split(",")].flatten();
            } else 	{
                // use user specified date range, if provided
                if(params.maxDate == null) {
                    throw new RuntimeException("Parameter 'maxDate' must be set.");
                }
                if (params.minDate == null) {
                    throw new RuntimeException("Parameter 'minDate' must be set.");
                }
		
                Date minDate = Date.parse("yyyy-MM-dd/HH:mm",params.minDate+"/"+params.minTime);
                Date maxDate = Date.parse("yyyy-MM-dd/HH:mm",params.maxDate+"/"+params.maxTime);
                startTime.add(minDate.format("yyyy-MM-dd'T'HH:mm:ss"));
                endTime.add(maxDate.format("yyyy-MM-dd'T'HH:mm:ss"));
            }
            
            def extraList = [params.extra].flatten();
            
            String where ="";
		
            if(params.where != null) {
                where = params.where;
            }
			
            HelioServiceName serviceName = HelioServiceName.valueOf(params.serviceName.toUpperCase());			
            ResultVT result = DataQueryService.queryService(serviceName.getName(), startTime, endTime, extraList, where);
            int resultId= ResultVTManagerService.addResult(result,params.serviceName);
            

       
            
            
            
            def responseObject = [result:result,resultId:resultId ];

            render template:'templates/response', bean:responseObject, var:'responseObject'
        }catch(Exception e){
            println e.printStackTrace();

            def responseObject = [error:e.getMessage() ];
            render template:'templates/response', bean:responseObject, var:'responseObject'


                        
        }

        
      
        
    }


    def asyncUpload ={
        log.info("asyncUpload =>" +params);

        try{
    
            if(request.getFile("fileInput").getOriginalFilename()=="")throw new RuntimeException("A valid xml VO-table file must be selected to continue.");
            if(!request.getFile("fileInput").getOriginalFilename().endsWith(".xml"))throw new RuntimeException("Not a valid xml file.");
            JAXBContext context = JAXBContext.newInstance(VOTABLE.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            VOTABLE votable = (VOTABLE) unmarshaller.unmarshal(new StreamSource( new StringReader( request.getFile("fileInput").inputStream.text ) ));
            def serviceName = 'upload';
            ResultVT result = new ResultVT(votable);
            int resultId= ResultVTManagerService.addResult(result,serviceName);
            def uploadId =request.getFile("fileInput").getOriginalFilename();
            
            def responseObject = [result:result,resultId:resultId,uploadId:uploadId];
            render template:'templates/response', bean:responseObject, var:'responseObject'

        }catch(Exception e){

            println e.printStackTrace();
            def responseObject = [error:e.getMessage() ];
            render template:'templates/response', bean:responseObject, var:'responseObject'
        }

    }
    
   


    def asyncQuery ={
        log.info("asyncQuery =>" +params);
        String sessionId = RequestContextHolder.getRequestAttributes()?.getSessionId()

        if(params.maxDate != null){
            try{
                
                HelioParameters helioparameters = new HelioParameters();
                helioparameters.minDate = [params.minDate].flatten();
                helioparameters.maxDate = [params.maxDate].flatten();
                helioparameters.minTime = [params.minTime].flatten();
                helioparameters.maxTime = [params.maxTime].flatten();
                helioparameters.extra = [params.extra].flatten();
                helioparameters.save();
                HelioQuery helioquery = new HelioQuery(session:sessionId,helioparameters:helioparameters,service:params.serviceName);
                helioquery.save();

                
                ResultVT  result = search(params);
                String serviceName = params.serviceName;
                
                int resultId= ResultVTManagerService.addResult(result,serviceName);
                
                
                    
                

                def responseObject = [result:result,resultId:resultId ];
                //helioquery.result = result.getStringTable();
                //helioquery.save();




                render template:'templates/response', bean:responseObject, var:'responseObject'
            }catch(Exception e){
                
                println e.printStackTrace();
                
                /**
                def responseObject = [error:e.getMessage() ];
                render template:'templates/response', bean:responseObject, var:'responseObject'
                 **/
            }

        }
        else {
            //TODO: need to send java stacktrace back
            
        }

    }

    def index = {
        redirect(action:"explorer");
    }

    def explorer={
        log.info("Explorer =>" +params)

        println (HelioParameters.list());
        println (HelioQuery.list());

        for(HelioQuery temp :HelioQuery.list()){
            println temp.service;
        }
        

        String sessionId = RequestContextHolder.getRequestAttributes()?.getSessionId()
        // init calalog list for HEC GUI
        HelioCatalogDao hecDao = HelioCatalogDaoFactory.getInstance().getHelioCatalogDao(HelioServiceName.HEC.getName());
        HelioField<String> catalogField = hecDao.getCatalogField();
        DomainValueDescriptor<String>[] valueDomain = catalogField.getValueDomain();
    	
        // init catalog list for DPAS GUI
        HelioCatalogDao dpasDao = HelioCatalogDaoFactory.getInstance().getHelioCatalogDao(HelioServiceName.DPAS.getName());
        if (dpasDao == null) {
            throw new NullPointerException("Unable to find service DPAS");
        }
        HelioField<String> dpasInstrumentsField = dpasDao.getCatalogById('dpas').getFieldById('instrument');
        DomainValueDescriptor<String>[] dpasInstruments = dpasInstrumentsField.getValueDomain();
        
        def initParams = [hecCatalogs:valueDomain, dpasInstruments: dpasInstruments, HUID:sessionId];
        render view:'explorer', model:initParams
    }


    def search = {
        log.info("Search =>" +params);
        ArrayList<String> maxDateList= new ArrayList<String>(); // initialize lists for webservice request
        ArrayList<String> minDateList= new ArrayList<String>();

        if(params.maxDate.contains(",")) {
            ArrayList<String> tempMinTimeList = params.minTime.split(",");
            ArrayList<String> tempMaxTimeList = params.maxTime.split(",");
            ArrayList<String> tempMaxDateList = params.maxDate.split(",");
            ArrayList<String> tempMinDateList = params.minDate.split(",");
            for(int i = 0; i<tempMinTimeList.size();i++){
                Date minDate = Date.parse("yyyy-MM-dd/HH:mm",tempMinDateList.get(i)+"/"+tempMinTimeList.get(i));
                Date maxDate = Date.parse("yyyy-MM-dd/HH:mm",tempMaxDateList.get(i)+"/"+tempMaxTimeList.get(i));
                maxDateList.add(maxDate.format("yyyy-MM-dd'T'HH:mm:ss"));
                minDateList.add(minDate.format("yyyy-MM-dd'T'HH:mm:ss"));
                
            }


            
            

        }else{
                 Date minDate = Date.parse("yyyy-MM-dd/HH:mm",params.minDate+"/"+params.minTime);
            Date maxDate = Date.parse("yyyy-MM-dd/HH:mm",params.maxDate+"/"+params.maxTime);
            maxDateList.add(maxDate.format("yyyy-MM-dd'T'HH:mm:ss"));
            minDateList.add(minDate.format("yyyy-MM-dd'T'HH:mm:ss"));
        

            
        }
        ArrayList<String> extraList = new ArrayList<String>();
        if(params.extra.contains(",")){
            extraList = params.extra.split(",")
        }else{
            extraList.push(params.extra);
        }
          
        String where ="";

	if(params.where != null)where = params.where;
	HelioServiceName serviceName = HelioServiceName.valueOf(params.serviceName.toUpperCase());
	ResultVT result = DataQueryService.queryService(serviceName.getName(), minDateList, maxDateList, extraList, where);

        return result;


    }

    def downloadVOTable = {
        log.info("downloadVOTable =>" + params);
        ResultVT result = ResultVTManagerService.getResult(Integer.parseInt(params.resultId));
        if(result !=null){
            
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            Date date = new Date();
            def name= formatter.format(date);
            name = ResultVTManagerService.getResultServiceReference(Integer.parseInt(params.resultId)) +"-"+name;
            response.setContentType("application/xml")
            response.setHeader("Content-disposition", "attachment;filename="+name+".xml");
            response.outputStream << result.getStringTable()
        }

    }


    def downloadPartialVOTable = {
        log.info("downloadPartialVOTable =>" + params  + session)
        ResultVT result = ResultVTManagerService.getResult(Integer.parseInt(params.resultId));
        if(result !=null){

            String indexes =params.indexes;
            String[] fields = indexes.split(",");
            def rowIndexSelection = [];
            def tableIndexSelection = [];
            for(String field:fields){
                String[] holder = field.split("resultTable");
                int row = Integer.parseInt(holder[0]);
                int table = Integer.parseInt(holder[1]);
                rowIndexSelection.add(table+"."+row);
                if(!tableIndexSelection.contains(table))tableIndexSelection.add(table);
            }
            VOTABLE res = result.getVOTABLE();
            for(int resourceIndex =0;resourceIndex < res.getRESOURCE().size();resourceIndex++)
            {
                RESOURCE resource = res.getRESOURCE().get(resourceIndex);
                if(!tableIndexSelection.contains(resourceIndex)){
                    res.getRESOURCE().set(resourceIndex,null);
                    continue;
                }
                for(int tableIndex =0;tableIndex < resource.getTABLE().size();tableIndex++)
                {
                    TABLE table= resource.getTABLE().get(tableIndex);
                    for(int trIndex=0;trIndex < table.getDATA().getTABLEDATA().getTR().size();trIndex++)
                    {
                        if(!rowIndexSelection.contains(resourceIndex+"."+trIndex)){

                            table.getDATA().getTABLEDATA().getTR().set(trIndex,null);
                        }
                    }
                }
            }
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            Date date = new Date();
            def name= formatter.format(date);
            name = ResultVTManagerService.getResultServiceReference(Integer.parseInt(params.resultId)) +"-reduced-"+name;
            
            response.setContentType("application/xml")
            response.setHeader("Content-disposition", "attachment;filename="+name+".xml");
            response.outputStream << result.getStringTable()

        }

    }
    def asyncSaveHistoryBar = {
        log.info("asyncSaveHistoryBar =>" + params);

        
        HelioMemoryBar item = HelioMemoryBar.findByHUID(params.HUID);
        
        if(item == null)item = new HelioMemoryBar(hUID:params.HUID,html:params.html);

        item.html = params.html;
        item.save();
        
        for(HelioMemoryBar temp :HelioMemoryBar.list()){
            println temp.hUID;
        }

        render "listo"
        
    }
     def asyncGetHistoryBar = {
        log.info("downloadVOTable =>" + params);


        HelioMemoryBar item = HelioMemoryBar.findByHUID(params.HUID);

        if(item == null)return "no encontrado"

        
        

        
            
        

        render item.html;

    }
}
