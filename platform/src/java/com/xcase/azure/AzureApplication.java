package com.xcase.azure;

import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.containerregistry.implementation.EventInner;
import com.microsoft.azure.management.monitor.ActivityLogs;
import com.microsoft.azure.management.monitor.implementation.ActivityLogsInner;
import com.microsoft.azure.management.monitor.implementation.EventDataInner;
import com.microsoft.azure.management.resources.Subscription;
import com.microsoft.rest.LogLevel;
import com.xcase.azure.constant.AzureConstant;
import com.xcase.azure.factories.AzureRequestFactory;
import com.xcase.azure.impl.simple.core.AzureConfigurationManager;
import com.xcase.azure.transputs.*;
import java.lang.invoke.MethodHandles;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AzureApplication {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public static void main(String[] args) {
        try {
            LOGGER.debug("starting main()");
            AzureExternalAPI azureExternalAPI = new SimpleAzureImpl();
            LOGGER.debug("created boxExternalAPI");
            String client = AzureConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(AzureConstant.LOCAL_AZURE_CLIENT);
            LOGGER.debug("client is " + client);
            String subscription = AzureConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(AzureConstant.LOCAL_AZURE_SUBSCRIPTION);
            LOGGER.debug("subscription is " + subscription);            
            String tenant = AzureConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(AzureConstant.LOCAL_AZURE_TENANT);
            LOGGER.debug("tenant is " + tenant);
            String key = AzureConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(AzureConstant.LOCAL_AZURE_KEY);
            LOGGER.debug("key is " + key);
            ApplicationTokenCredentials applicationTokenCredentials = new ApplicationTokenCredentials(client, tenant, key, AzureEnvironment.AZURE);
            Azure azure = Azure.configure().withLogLevel(LogLevel.BASIC).authenticate(applicationTokenCredentials).withSubscription(subscription);
            LOGGER.debug("authenticated Azure");
            String subscriptionId = azure.subscriptionId();
            LOGGER.debug("subscriptionId is " + subscriptionId);
            GetEventsRequest getEventsRequest = AzureRequestFactory.createGetEventsRequest();
            getEventsRequest.setAzure(azure);
            getEventsRequest.setFilter("eventTimestamp ge '2019-06-01T00:00:00Z'");
            getEventsRequest.setSelect("eventName,id");
            GetEventsResponse getEventsResponse = azureExternalAPI.getEvents(getEventsRequest);
            GetContainerServicesRequest getContainerServicesRequest = AzureRequestFactory.createGetContainerServicesRequest();
            getContainerServicesRequest.setAzure(azure);
            /* TODO: Set necessary parameters */
            GetContainerServicesResponse getContainerServicesResponse = azureExternalAPI.getContainerServices(getContainerServicesRequest);
            GetDNSZonesRequest getDNSZonesRequest = AzureRequestFactory.createGetDNSZonesRequest();
            getDNSZonesRequest.setAzure(azure);
            /* TODO: Set necessary parameters */
            GetDNSZonesResponse getDNSZonesResponse = azureExternalAPI.getDNSZones(getDNSZonesRequest);
            GetGalleriesRequest getGalleriesRequest = AzureRequestFactory.createGetGalleriesRequest();
            getGalleriesRequest.setAzure(azure);
            /* TODO: Set necessary parameters */
            GetGalleriesResponse getGalleriesResponse = azureExternalAPI.getGalleries(getGalleriesRequest);
            GetSqlServersRequest getSqlServersRequest = AzureRequestFactory.createGetSqlServersRequest();
            getSqlServersRequest.setAzure(azure);
            /* TODO: Set necessary parameters */
            GetSqlServersResponse getSqlServersResponse = azureExternalAPI.getSqlServers(getSqlServersRequest);
            LOGGER.debug("completed Azure operations");
        } catch (Exception e) {
            LOGGER.warn("exception executing Azure operations: " + e.getMessage());
        }

    }
}
