package com.xcase.intapp.cdsusers.factories;

import java.lang.invoke.MethodHandles;

import com.xcase.intapp.cdsusers.transputs.GetClientSecurityRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CDSUsersRequestFactory extends BaseCDSUsersFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static GetClientSecurityRequest createGetClientSecurityRequest() {
        Object obj = newInstanceOf("cdsusers.config.requestfactory.GetClientSecurityRequest");
        return (GetClientSecurityRequest) obj;
    }

    public static GetClientSecurityRequest createGetClientSecurityRequest(String accessToken) {
        GetClientSecurityRequest request = createGetClientSecurityRequest();
        request.setAccessToken(accessToken);
        return request;
    }

}
