package com.xcase.intapp.cdsrefdata.factories;

import com.xcase.intapp.cdsrefdata.transputs.*;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    
	public static GetMatterStatusesRequest createGetMatterStatusesRequest() {
        Object obj = newInstanceOf("cdsrefdata.config.requestfactory.GetMatterStatusesRequest");
        return (GetMatterStatusesRequest) obj;
	}

	public static GetMatterStatusesRequest createGetMatterStatusesRequest(String accessToken) {
		GetMatterStatusesRequest request = createGetMatterStatusesRequest();
        request.setAccessToken(accessToken);
        return request;
	}

}
