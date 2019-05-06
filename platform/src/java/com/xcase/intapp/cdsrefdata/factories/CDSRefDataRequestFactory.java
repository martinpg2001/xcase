package com.xcase.intapp.cdsrefdata.factories;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xcase.intapp.cdscm.transputs.CreateClientRequest;
import com.xcase.intapp.cdsrefdata.transputs.GetClientStatusesRequest;

public class CDSRefDataRequestFactory extends BaseCDSRefDataFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public static GetClientStatusesRequest createGetClientStatusesRequest() {
        Object obj = newInstanceOf("cdsrefdata.config.requestfactory.GetClientStatusesRequest");
        return (GetClientStatusesRequest) obj;
    }

    public static GetClientStatusesRequest createGetClientStatusesRequest(String accessToken) {
        GetClientStatusesRequest request = createGetClientStatusesRequest();
        request.setAccessToken(accessToken);
        return request;
    }

}
