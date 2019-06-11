package com.xcase.azure.impl.simple.methods;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.containerservice.ContainerServices;
import com.microsoft.azure.management.containerservice.implementation.ContainerServicesInner;
import com.microsoft.azure.management.dns.DnsZones;
import com.microsoft.azure.management.dns.implementation.ZonesInner;
import com.microsoft.azure.management.monitor.ActivityLogs;
import com.microsoft.azure.management.monitor.implementation.ActivityLogsInner;
import com.microsoft.azure.management.monitor.implementation.EventDataInner;
import com.xcase.azure.factories.AzureResponseFactory;
import com.xcase.azure.transputs.GetDNSZonesRequest;
import com.xcase.azure.transputs.GetDNSZonesResponse;
import com.xcase.azure.transputs.GetEventsRequest;
import com.xcase.azure.transputs.GetEventsResponse;
import com.xcase.azure.transputs.GetGalleriesResponse;

import java.lang.invoke.MethodHandles;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetDNSZonesMethod extends BaseAzureMethod {
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetDNSZonesResponse getDNSZones(GetDNSZonesRequest request) {
        LOGGER.debug("starting getDNSZones()");
        GetDNSZonesResponse response = AzureResponseFactory.createGetDNSZonesResponse();
        LOGGER.debug("created response");
        try {
            Azure azure = request.getAzure();
            /* TODO: complete method details */
            DnsZones dnsZones = azure.dnsZones();
            ZonesInner zonesInner = dnsZones.inner();
        } catch (Exception e) {
            LOGGER.warn("exception getting DNS zones: " + e.getMessage());
        }

        return response;
    }

}
