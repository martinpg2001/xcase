package com.xcase.azure.factories;

import com.xcase.azure.transputs.*;

public class AzureResponseFactory extends BaseAzureFactory {

    public static GetEventsResponse createGetEventsResponse() {
        Object obj = newInstanceOf("azure.config.responsefactory.GetEventsResponse");
        return (GetEventsResponse) obj;
    }

    public static GetContainerServicesResponse createGetContainerServicesResponse() {
        Object obj = newInstanceOf("azure.config.responsefactory.GetContainerServicesResponse");
        return (GetContainerServicesResponse) obj;
    }

    public static GetSqlServersResponse createGetSqlServersResponse() {
        Object obj = newInstanceOf("azure.config.responsefactory.GetSqlServersResponse");
        return (GetSqlServersResponse) obj;
    }

    public static GetGalleriesResponse createGetGalleriesResponse() {
        Object obj = newInstanceOf("azure.config.responsefactory.GetGalleriesResponse");
        return (GetGalleriesResponse) obj;
    }

    public static GetDNSZonesResponse createGetDNSZonesResponse() {
        Object obj = newInstanceOf("azure.config.responsefactory.GetDNSZonesResponse");
        return (GetDNSZonesResponse) obj;
    }

}
