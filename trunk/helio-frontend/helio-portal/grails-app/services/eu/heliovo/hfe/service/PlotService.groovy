package eu.heliovo.hfe.service

import eu.heliovo.registryclient.ServiceCapability;

import java.util.concurrent.TimeUnit

import org.springframework.beans.BeanWrapper
import org.springframework.beans.BeanWrapperImpl

import eu.heliovo.clientapi.HelioClient
import eu.heliovo.clientapi.processing.ProcessingServiceFactory
import eu.heliovo.clientapi.workerservice.HelioWorkerServiceHandler
import eu.heliovo.hfe.model.result.RemotePlotResult
import eu.heliovo.hfe.model.task.Task
import eu.heliovo.registryclient.AccessInterface
import eu.heliovo.registryclient.AccessInterfaceType
import eu.heliovo.registryclient.ServiceCapability
import eu.heliovo.registryclient.impl.AccessInterfaceImpl
import eu.heliovo.shared.props.HelioFileUtil

class PlotService {

    HelioClient helioClient = new HelioClient()

    static transactional = true

    /**
     * Execute the plotting service and get back the results.
     * @param task the task to execute. The results will be stored to the task.
     * @return a map containing the results, ready to be passed to the view.
     */
    def plot(Task task) {
        //get the task descriptor
        def taskDescriptor = task.findTaskDescriptor()
        
//        def plotService = helioClient.getServiceInstance(
//            taskDescriptor.serviceName,
//            ServiceCapability.COMMON_EXECUTION_ARCHITECTURE_SERVICE, 
//            taskDescriptor.serviceVariant)
        
        def plotService
        
        ProcessingServiceFactory factory = ProcessingServiceFactory.getInstance();
        if (taskDescriptor.serviceCapability == ServiceCapability.COMMON_EXECUTION_ARCHITECTURE_SERVICE) {
            // temporary workaround until the registry is switched.
            AccessInterface ai = new AccessInterfaceImpl(AccessInterfaceType.SOAP_SERVICE, taskDescriptor.serviceCapability, HelioFileUtil.asURL("http://msslkz.mssl.ucl.ac.uk/cxs/services/CommonExecutionConnectorService"));        
            plotService = factory.getHelioService(taskDescriptor.serviceName, taskDescriptor.serviceVariant, ai) 
        } else {
            plotService = factory.getHelioService(taskDescriptor.serviceName, taskDescriptor.serviceVariant, null)         
        }
        
        def timeRanges = task.inputParams.timeRanges.timeRanges

        // populate the plot service
        BeanWrapper beanWrapper = new BeanWrapperImpl(plotService)
        if (beanWrapper.isWritableProperty("startTime")) {
            beanWrapper.setPropertyValue("startTime", timeRanges[0].start)
        }
        if (beanWrapper.isWritableProperty("date")) {
            beanWrapper.setPropertyValue("date", timeRanges[0].start)
        }
        if (beanWrapper.isWritableProperty("endTime")) {
            beanWrapper.setPropertyValue("endTime", timeRanges[0].end)
        }
        if (taskDescriptor.inputParams.paramSet) {
            def paramSet = task.inputParams.paramSet.params
            beanWrapper.setPropertyValues(paramSet)
        }
        
        def result = plotService.execute();
        def resultObject = result.asResultObject(taskDescriptor.timeout, TimeUnit.SECONDS);
        
        // wrap the resultObject ...
        beanWrapper = new BeanWrapperImpl(resultObject)
        
        def model = [:]
        
        // create the models for the template
        model.plotResults = []
        model.userLogs = result.userLogs
        
        // ... and add the results to the task
        taskDescriptor.outputParams.each {
//            if (it.value.type == "votable") {   // does not apply for plot. keeping it for the one and only super service.
//                // get the url and wrap into votable object
//                def url = beanWrapper.getPropertyValue(it.value.id)
//                def votable = new RemoteVOTableResult(url: url);
//                votable.save()
//                task.outputParams.put(it.value.id, votable)
//                model.votableResults.push ([id: it.value.id, label: it.value.label, value : votable])
//            }  else 
            if (it.value.type == "url"){
                def url = beanWrapper.getPropertyValue(it.value.id)
                def plot = new RemotePlotResult(url : url.toString())
                plot.save()
                task.outputParams.put(it.value.id, plot)
                model.plotResults.push ([id: it.value.id, label: it.value.label, value : plot])
            }
        }
        // update task status
        task.lastKnownStatus = HelioWorkerServiceHandler.Phase.COMPLETED
        task.save()
        
        // return the model
        model
    }
}
