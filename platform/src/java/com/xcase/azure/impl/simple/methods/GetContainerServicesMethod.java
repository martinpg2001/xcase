package com.xcase.azure.impl.simple.methods;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.containerservice.ContainerServices;
import com.microsoft.azure.management.containerservice.implementation.ContainerServicesInner;
import com.microsoft.azure.management.monitor.ActivityLogs;
import com.microsoft.azure.management.monitor.implementation.ActivityLogsInner;
import com.microsoft.azure.management.monitor.implementation.EventDataInner;
import com.xcase.azure.factories.AzureResponseFactory;
import com.xcase.azure.transputs.GetContainerServicesRequest;
import com.xcase.azure.transputs.GetContainerServicesResponse;
import com.xcase.azure.transputs.GetEventsResponse;

import java.lang.invoke.MethodHandles;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetContainerServicesMethod extends BaseAzureMethod {
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public GetContainerServicesResponse getContainerServices(GetContainerServicesRequest request) {
        LOGGER.debug("starting getContainerServices()");
        GetContainerServicesResponse response = AzureResponseFactory.createGetContainerServicesResponse();
        LOGGER.debug("created response");
        try {
            Azure azure = request.getAzure();
            /* TODO: complete method details */
            ContainerServices containerServices = azure.containerServices();
            ContainerServicesInner containerServicesInner = containerServices.inner();
        } catch (Exception e) {
            LOGGER.warn("exception getting container services: " + e.getMessage());
        }

        return response;
    }

}
