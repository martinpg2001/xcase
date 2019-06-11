package com.xcase.azure.factories;

import com.xcase.azure.transputs.*;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AzureRequestFactory extends BaseAzureFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * create request object.
     *
     * @return request object
     */

    public static GetContainerServicesRequest createGetContainerServicesRequest() {
        Object obj = newInstanceOf("azure.config.requestfactory.GetContainerServicesRequest");
        return (GetContainerServicesRequest) obj;
    }

    public static GetDNSZonesRequest createGetDNSZonesRequest() {
        Object obj = newInstanceOf("azure.config.requestfactory.GetDNSZonesRequest");
        return (GetDNSZonesRequest) obj;
    }
    
    public static GetEventsRequest createGetEventsRequest() {
        Object obj = newInstanceOf("azure.config.requestfactory.GetEventsRequest");
        return (GetEventsRequest) obj;
    }

    public static GetGalleriesRequest createGetGalleriesRequest() {
        Object obj = newInstanceOf("azure.config.requestfactory.GetGalleriesRequest");
        return (GetGalleriesRequest) obj;
    }

    public static GetSqlServersRequest createGetSqlServersRequest() {
        Object obj = newInstanceOf("azure.config.requestfactory.GetSqlServersRequest");
        return (GetSqlServersRequest) obj;
    }
}
