package com.xcase.intapp.cdsusers;

import com.xcase.intapp.cdsusers.transputs.*;

public interface CDSUsersExternalAPI {

	CreatePersonResponse createPerson(CreatePersonRequest createPersonRequest);

    CreateUserResponse createUser(CreateUserRequest createUserRequest);
    
    FindRolesResponse findRoles(FindRolesRequest findRolesRequest);

    FindCapabilitiesResponse findCapabilities(FindCapabilitiesRequest findCapabilitiesRequest);
    
	FindServiceUsersResponse findServiceUsers(FindServiceUsersRequest findServiceUsersRequest);

    FindUsersResponse findUsers(FindUsersRequest findUsersRequest);
    
    GetCapabilityResponse getCapability(GetCapabilityRequest getCapabilityRequest);
    
    GetPersonsResponse getPersons(GetPersonsRequest getPersonsRequest);
    
	GetServiceUserResponse getServiceUser(GetServiceUserRequest getServiceUserRequest);
    
    GetUserResponse getUser(GetUserRequest getUserRequest);
    
    PartiallyUpdateUserResponse partiallyUpdateUser(PartiallyUpdateUserRequest partiallyUpdateUserRequest);

	PublishEntitiesResponse publishEntities(PublishEntitiesRequest publishEntitiesRequest);
	
    PutUserResponse putUser(PutUserRequest putUserRequest);

    CreateServiceUserResponse createServiceUser(CreateServiceUserRequest createServiceUserRequest);

}
