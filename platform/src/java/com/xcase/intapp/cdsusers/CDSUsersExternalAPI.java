package com.xcase.intapp.cdsusers;

import com.xcase.intapp.cdsusers.transputs.CreatePersonRequest;
import com.xcase.intapp.cdsusers.transputs.CreatePersonResponse;

public interface CDSUsersExternalAPI {

	CreatePersonResponse createPerson(CreatePersonRequest createPersonRequest);

}
