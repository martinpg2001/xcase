package com.xcase.intapp.cdsusers.factories;

import com.xcase.intapp.cdsusers.transputs.*;

public class CDSUsersResponseFactory extends BaseCDSUsersFactory {

	public static CreatePersonResponse createCreatePersonResponse() {
        Object obj = newInstanceOf("cdsusers.config.responsefactory.CreatePersonResponse");
        return (CreatePersonResponse) obj;
	}

    public static GetPersonsResponse createGetPersonsResponse() {
        Object obj = newInstanceOf("cdsusers.config.responsefactory.GetPersonsResponse");
        return (GetPersonsResponse) obj;
    }

    public static CreateUserResponse createCreateUserResponse() {
        Object obj = newInstanceOf("cdsusers.config.responsefactory.CreateUserResponse");
        return (CreateUserResponse) obj;
    }

    public static FindUsersResponse createFindUsersResponse() {
        Object obj = newInstanceOf("cdsusers.config.responsefactory.FindUsersResponse");
        return (FindUsersResponse) obj;
    }

	public static GetUserResponse createGetUserResponse() {
        Object obj = newInstanceOf("cdsusers.config.responsefactory.GetUserResponse");
        return (GetUserResponse) obj;
	}

    public static FindRolesResponse createFindRolesResponse() {
        Object obj = newInstanceOf("cdsusers.config.responsefactory.FindRolesResponse");
        return (FindRolesResponse) obj;
    }

    public static FindCapabilitiesResponse createFindCapabilitiesResponse() {
        Object obj = newInstanceOf("cdsusers.config.responsefactory.FindCapabilitiesResponse");
        return (FindCapabilitiesResponse) obj;
    }

    public static GetCapabilityResponse createGetCapabilityResponse() {
        Object obj = newInstanceOf("cdsusers.config.responsefactory.GetCapabilityResponse");
        return (GetCapabilityResponse) obj;
    }

    public static PartiallyUpdateUserResponse createPartiallyUpdateUserResponse() {
        Object obj = newInstanceOf("cdsusers.config.responsefactory.PartiallyUpdateUserResponse");
        return (PartiallyUpdateUserResponse) obj;
    }

    public static PutUserResponse createPutUserResponse() {
        Object obj = newInstanceOf("cdsusers.config.responsefactory.PutUserResponse");
        return (PutUserResponse) obj;
    }

	public static PublishEntitiesResponse createPublishEntitiesResponse() {
        Object obj = newInstanceOf("cdsusers.config.responsefactory.PublishEntitiesResponse");
        return (PublishEntitiesResponse) obj;
	}
}
