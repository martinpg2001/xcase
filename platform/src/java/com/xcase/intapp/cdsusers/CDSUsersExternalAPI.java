package com.xcase.intapp.cdsusers;

import com.xcase.intapp.cdsusers.transputs.*;

public interface CDSUsersExternalAPI {

	CreatePersonResponse createPerson(CreatePersonRequest createPersonRequest);

    GetPersonsResponse getPersons(GetPersonsRequest getPersonsRequest);

    CreateUserResponse createUser(CreateUserRequest createUserRequest);

    FindUsersResponse findUsers(FindUsersRequest findUsersRequest);

    PartiallyUpdateUserResponse partiallyUpdateUser(PartiallyUpdateUserRequest partiallyUpdateUserRequest);

}
