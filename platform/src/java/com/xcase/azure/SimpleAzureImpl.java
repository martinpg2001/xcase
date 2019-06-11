package com.xcase.azure;

import com.xcase.azure.impl.simple.core.AzureConfigurationManager;
import com.xcase.azure.impl.simple.methods.*;
import com.xcase.azure.transputs.*;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleAzureImpl implements AzureExternalAPI {
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * configuration manager
     */
    public AzureConfigurationManager LocalConfigurationManager = AzureConfigurationManager.getConfigurationManager();
    
    private GetContainerServicesMethod getContainerServicesMethod = new GetContainerServicesMethod();
    
    private GetDNSZonesMethod getDNSZonesMethod = new GetDNSZonesMethod();
    
    private GetEventsMethod getEventsMethod = new GetEventsMethod();
    
    private GetGalleriesMethod getGalleriesMethod = new GetGalleriesMethod();
    
    private GetSqlServersMethod getSqlServersMethod = new GetSqlServersMethod();
   

    @Override
    public GetContainerServicesResponse getContainerServices(GetContainerServicesRequest getContainerServicesRequest) {
        return this.getContainerServicesMethod.getContainerServices(getContainerServicesRequest);
    }

    @Override
    public GetDNSZonesResponse getDNSZones(GetDNSZonesRequest getDNSZonesRequest) {
        return this.getDNSZonesMethod.getDNSZones(getDNSZonesRequest);
    }
    
    @Override
    public GetEventsResponse getEvents(GetEventsRequest getEventsRequest) {
        return this.getEventsMethod.getEvents(getEventsRequest);
    }

    @Override
    public GetGalleriesResponse getGalleries(GetGalleriesRequest getGalleriesRequest) {
        return this.getGalleriesMethod.getGalleries(getGalleriesRequest);
    }

    @Override
    public GetSqlServersResponse getSqlServers(GetSqlServersRequest getSqlServersRequest) {
        return this.getSqlServersMethod.getSqlServers(getSqlServersRequest);
    }

}
