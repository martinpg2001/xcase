package com.xcase.intapp.cdsusers.factories;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xcase.intapp.advanced.transputs.InvokeOperationRequest;
import com.xcase.intapp.cdscm.transputs.CreateClientRequest;
import com.xcase.intapp.cdsusers.transputs.CreatePersonRequest;
import com.xcase.intapp.cdsusers.transputs.CreateUserRequest;
import com.xcase.intapp.cdsusers.transputs.GetPersonsRequest;

public class CDSUsersRequestFactory extends BaseCDSUsersFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static CreatePersonRequest createCreatePersonRequest() {
        Object obj = newInstanceOf("cdsusers.config.requestfactory.CreatePersonRequest");
        return (CreatePersonRequest) obj;
    }

    public static CreatePersonRequest createCreatePersonRequest(String accessToken) {
        CreatePersonRequest request = createCreatePersonRequest();
        request.setAccessToken(accessToken);
        return request;
    }

    public static GetPersonsRequest createGetPersonsRequest() {
        Object obj = newInstanceOf("cdsusers.config.requestfactory.GetPersonsRequest");
        return (GetPersonsRequest) obj;
    }

    public static GetPersonsRequest createGetPersonsRequest(String accessToken) {
        GetPersonsRequest request = createGetPersonsRequest();
        request.setAccessToken(accessToken);
        return request;
    }
    
    public static CreateUserRequest createCreateUserRequest() {
        Object obj = newInstanceOf("cdsusers.config.requestfactory.CreateUserRequest");
        return (CreateUserRequest) obj;
    }

    public static CreateUserRequest createCreateUserRequest(String accessToken) {
        CreateUserRequest request = createCreateUserRequest();
        request.setAccessToken(accessToken);
        return request;
    }

}
