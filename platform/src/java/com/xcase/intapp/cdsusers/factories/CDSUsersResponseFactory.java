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

    public static PartiallyUpdateUserResponse createPartiallyUpdateUserResponse() {
        Object obj = newInstanceOf("cdsusers.config.responsefactory.PartiallyUpdateUserResponse");
        return (PartiallyUpdateUserResponse) obj;
    }

}
