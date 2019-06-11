package com.xcase.azure.impl.simple.methods;

import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.compute.Galleries;
import com.microsoft.azure.management.compute.implementation.GalleriesInner;
import com.microsoft.azure.management.sql.SqlServers;
import com.microsoft.azure.management.sql.implementation.ServersInner;
import com.xcase.azure.factories.AzureResponseFactory;
import com.xcase.azure.transputs.GetContainerServicesResponse;
import com.xcase.azure.transputs.GetSqlServersRequest;
import com.xcase.azure.transputs.GetSqlServersResponse;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetSqlServersMethod extends BaseAzureMethod {
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public GetSqlServersResponse getSqlServers(GetSqlServersRequest request) {
        LOGGER.debug("starting getSqlServers()");
        GetSqlServersResponse response = AzureResponseFactory.createGetSqlServersResponse();
        LOGGER.debug("created response");
        try {
            Azure azure = request.getAzure();
            /* TODO: complete method details */
            SqlServers sqlServers = azure.sqlServers();
            ServersInner serversInner = sqlServers.inner();
        } catch (Exception e) {
            LOGGER.warn("exception getting SQL servers: " + e.getMessage());
        }

        return response;
    }

}
