package com.xcase.intapp.time.factories;

import com.xcase.intapp.time.transputs.*;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimeRequestFactory extends BaseTimeFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public static GetRestrictedTextsRequest createGetRestrictedTextsRequest() {
        Object obj = newInstanceOf("time.config.requestfactory.GetRestrictedTextsRequest");
        return (GetRestrictedTextsRequest) obj;
    }

    public static GetRestrictedTextsRequest createGetRestrictedTextsRequest(String accessToken) {
    	GetRestrictedTextsRequest request = createGetRestrictedTextsRequest();
        request.setAccessToken(accessToken);
        return request;
    }

}
