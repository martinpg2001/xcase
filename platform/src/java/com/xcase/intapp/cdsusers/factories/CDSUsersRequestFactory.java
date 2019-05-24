package com.xcase.intapp.cdsusers.factories;

import com.xcase.intapp.cdsusers.transputs.*;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    
    public static FindUsersRequest createFindUsersRequest() {
        Object obj = newInstanceOf("cdsusers.config.requestfactory.FindUsersRequest");
        return (FindUsersRequest) obj;
    }

    public static FindUsersRequest createFindUsersRequest(String accessToken) {
        FindUsersRequest request = createFindUsersRequest();
        request.setAccessToken(accessToken);
        return request;
    }
    
    public static PartiallyUpdateUserRequest createPartiallyUpdateUserRequest() {
        Object obj = newInstanceOf("cdsusers.config.requestfactory.PartiallyUpdateUserRequest");
        return (PartiallyUpdateUserRequest) obj;
    }

    public static PartiallyUpdateUserRequest createPartiallyUpdateUserRequest(String accessToken) {
        PartiallyUpdateUserRequest request = createPartiallyUpdateUserRequest();
        request.setAccessToken(accessToken);
        return request;
    }
    
    public static PutUserRequest createPutUserRequest() {
        Object obj = newInstanceOf("cdsusers.config.requestfactory.PutUserRequest");
        return (PutUserRequest) obj;
    }

	public static PutUserRequest createPutUserRequest(String accessToken) {
		PutUserRequest request = createPutUserRequest();
        request.setAccessToken(accessToken);
        return request;
	}
	
    public static GetUserRequest createGetUserRequest() {
        Object obj = newInstanceOf("cdsusers.config.requestfactory.GetUserRequest");
        return (GetUserRequest) obj;
    }

	public static GetUserRequest createGetUserRequest(String accessToken) {
		GetUserRequest request = createGetUserRequest();
        request.setAccessToken(accessToken);
        return request;
	}
	
    public static FindRolesRequest createFindRolesRequest() {
        Object obj = newInstanceOf("cdsusers.config.requestfactory.FindRolesRequest");
        return (FindRolesRequest) obj;
    }

    public static FindRolesRequest createFindRolesRequest(String accessToken) {
        FindRolesRequest request = createFindRolesRequest();
        request.setAccessToken(accessToken);
        return request;
    }
    
    public static FindCapabilitiesRequest createFindCapabilitiesRequest() {
        Object obj = newInstanceOf("cdsusers.config.requestfactory.FindCapabilitiesRequest");
        return (FindCapabilitiesRequest) obj;
    }

    public static FindCapabilitiesRequest createFindCapabilitiesRequest(String accessToken) {
        FindCapabilitiesRequest request = createFindCapabilitiesRequest();
        request.setAccessToken(accessToken);
        return request;
    }
    
    public static GetCapabilityRequest createGetCapabilityRequest() {
        Object obj = newInstanceOf("cdsusers.config.requestfactory.GetCapabilityRequest");
        return (GetCapabilityRequest) obj;
    }

    public static GetCapabilityRequest createGetCapabilityRequest(String accessToken) {
        GetCapabilityRequest request = createGetCapabilityRequest();
        request.setAccessToken(accessToken);
        return request;
    }
    
    public static PublishEntitiesRequest createPublishEntitiesRequest() {
        Object obj = newInstanceOf("cdsusers.config.requestfactory.PublishEntitiesRequest");
        return (PublishEntitiesRequest) obj;
    }

	public static PublishEntitiesRequest createPublishEntitiesRequest(String accessToken) {
		PublishEntitiesRequest request = createPublishEntitiesRequest();
        request.setAccessToken(accessToken);
        return request;
	}
	
    public static FindServiceUsersRequest createFindServiceUsersRequest() {
        Object obj = newInstanceOf("cdsusers.config.requestfactory.FindServiceUsersRequest");
        return (FindServiceUsersRequest) obj;
    }

	public static FindServiceUsersRequest createFindServiceUsersRequest(String accessToken) {
		FindServiceUsersRequest request = createFindServiceUsersRequest();
        request.setAccessToken(accessToken);
        return request;
	}
	
    public static GetServiceUserRequest createGetServiceUserRequest() {
        Object obj = newInstanceOf("cdsusers.config.requestfactory.GetServiceUserRequest");
        return (GetServiceUserRequest) obj;
    }

	public static GetServiceUserRequest createGetServiceUserRequest(String accessToken) {
		GetServiceUserRequest request = createGetServiceUserRequest();
        request.setAccessToken(accessToken);
        return request;
	}
	
    public static CreateServiceUserRequest createCreateServiceUserRequest() {
        Object obj = newInstanceOf("cdsusers.config.requestfactory.CreateServiceUserRequest");
        return (CreateServiceUserRequest) obj;
    }

    public static CreateServiceUserRequest createCreateServiceUserRequest(String accessToken) {
        CreateServiceUserRequest request = createCreateServiceUserRequest();
        request.setAccessToken(accessToken);
        return request;
    }

}
