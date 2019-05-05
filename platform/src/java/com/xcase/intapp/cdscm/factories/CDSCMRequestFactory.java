package com.xcase.intapp.cdscm.factories;

import java.lang.invoke.MethodHandles;

import com.xcase.intapp.cdscm.transputs.CreateClientRequest;
import com.xcase.intapp.cdscm.transputs.DeleteClientRequest;
import com.xcase.intapp.cdscm.transputs.GetClientSecurityRequest;
import com.xcase.intapp.cdscm.transputs.PutClientSecurityRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CDSCMRequestFactory extends BaseCDSCMFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public static CreateClientRequest createCreateClientRequest() {
        Object obj = newInstanceOf("cdscm.config.requestfactory.CreateClientRequest");
        return (CreateClientRequest) obj;
    }

    public static CreateClientRequest createCreateClientRequest(String accessToken) {
        CreateClientRequest request = createCreateClientRequest();
        request.setAccessToken(accessToken);
        return request;
    }
    
    public static DeleteClientRequest createDeleteClientRequest() {
        Object obj = newInstanceOf("cdscm.config.requestfactory.DeleteClientRequest");
        return (DeleteClientRequest) obj;
    }

    public static DeleteClientRequest createDeleteClientRequest(String accessToken) {
        DeleteClientRequest request = createDeleteClientRequest();
        request.setAccessToken(accessToken);
        return request;
    }
    
    public static GetClientSecurityRequest createGetClientSecurityRequest() {
        Object obj = newInstanceOf("cdscm.config.requestfactory.GetClientSecurityRequest");
        return (GetClientSecurityRequest) obj;
    }

    public static GetClientSecurityRequest createGetClientSecurityRequest(String accessToken) {
        GetClientSecurityRequest request = createGetClientSecurityRequest();
        request.setAccessToken(accessToken);
        return request;
    }
    
    public static PutClientSecurityRequest createPutClientSecurityRequest() {
        Object obj = newInstanceOf("cdscm.config.requestfactory.PutClientSecurityRequest");
        return (PutClientSecurityRequest) obj;
    }

    public static PutClientSecurityRequest createPutClientSecurityRequest(String accessToken) {
        PutClientSecurityRequest request = createPutClientSecurityRequest();
        request.setAccessToken(accessToken);
        return request;
    }

}
