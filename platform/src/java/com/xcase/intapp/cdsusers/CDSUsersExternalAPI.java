package com.xcase.intapp.cdsusers;

import com.xcase.intapp.cdsusers.transputs.*;

public interface CDSUsersExternalAPI {

	CreatePersonResponse createPerson(CreatePersonRequest createPersonRequest);

    CreateUserResponse createUser(CreateUserRequest createUserRequest);

    FindUsersResponse findUsers(FindUsersRequest findUsersRequest);

    FindRolesResponse findRoles(FindRolesRequest findRolesRequest);

    FindCapabilitiesResponse findCapabilities(FindCapabilitiesRequest findCapabilitiesRequest);
    
    GetCapabilityResponse getCapability(GetCapabilityRequest getCapabilityRequest);
    
    GetPersonsResponse getPersons(GetPersonsRequest getPersonsRequest);
    
    GetUserResponse getUser(GetUserRequest getUserRequest);
    
    PartiallyUpdateUserResponse partiallyUpdateUser(PartiallyUpdateUserRequest partiallyUpdateUserRequest);

    PutUserResponse putUser(PutUserRequest putUserRequest);

}
