package com.xcase.azure;

import com.xcase.azure.transputs.*;

public interface AzureExternalAPI {
    GetContainerServicesResponse getContainerServices(GetContainerServicesRequest getContainerServicesRequest);
    
    GetDNSZonesResponse getDNSZones(GetDNSZonesRequest getDNSZonesRequest);
    
    GetEventsResponse getEvents(GetEventsRequest getEventsRequest);

    GetGalleriesResponse getGalleries(GetGalleriesRequest getGalleriesRequest);

    GetSqlServersResponse getSqlServers(GetSqlServersRequest getSqlServersRequest);
}
