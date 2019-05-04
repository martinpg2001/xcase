package com.xcase.intapp.cdscm.factories;

import java.lang.invoke.MethodHandles;

import com.xcase.intapp.cdscm.transputs.GetClientSecurityRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CDSCMRequestFactory extends BaseCDSCMFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static GetClientSecurityRequest createGetClientSecurityRequest() {
        Object obj = newInstanceOf("cdscm.config.requestfactory.GetClientSecurityRequest");
        return (GetClientSecurityRequest) obj;
    }

    public static GetClientSecurityRequest createGetClientSecurityRequest(String accessToken) {
        GetClientSecurityRequest request = createGetClientSecurityRequest();
        request.setAccessToken(accessToken);
        return request;
    }

}
