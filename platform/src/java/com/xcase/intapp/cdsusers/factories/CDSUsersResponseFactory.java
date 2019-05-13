package com.xcase.intapp.cdsusers.factories;

import com.xcase.intapp.cdsusers.transputs.CreatePersonResponse;

public class CDSUsersResponseFactory extends BaseCDSUsersFactory {

	public static CreatePersonResponse createCreatePersonResponse() {
        Object obj = newInstanceOf("cdsusers.config.responsefactory.CreatePersonResponse");
        return (CreatePersonResponse) obj;
	}

}
