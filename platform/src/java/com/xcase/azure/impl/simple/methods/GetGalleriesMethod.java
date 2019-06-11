package com.xcase.azure.impl.simple.methods;

import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.compute.Galleries;
import com.microsoft.azure.management.compute.implementation.GalleriesInner;
import com.xcase.azure.factories.AzureResponseFactory;
import com.xcase.azure.transputs.GetGalleriesRequest;
import com.xcase.azure.transputs.GetGalleriesResponse;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetGalleriesMethod extends BaseAzureMethod {
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public GetGalleriesResponse getGalleries(GetGalleriesRequest request) {
        LOGGER.debug("starting getGalleries()");
        GetGalleriesResponse response = AzureResponseFactory.createGetGalleriesResponse();
        LOGGER.debug("created response");
        try {
            Azure azure = request.getAzure();
            /* TODO: complete method details */
            Galleries galleries = azure.galleries();
            GalleriesInner galleriesInner = galleries.inner();
        } catch (Exception e) {
            LOGGER.warn("exception getting galleries: " + e.getMessage());
        }

        return response;
    }

}
