package com.xcase.intapp.cdsusers;

import com.xcase.intapp.cdsusers.transputs.*;

public interface CDSUsersExternalAPI {

	CreatePersonResponse createPerson(CreatePersonRequest createPersonRequest);
	
    CreateServiceUserResponse createServiceUser(CreateServiceUserRequest createServiceUserRequest);

    CreateRoleResponse createRole(CreateRoleRequest createRoleRequest);

    CreateUserResponse createUser(CreateUserRequest createUserRequest);
    
    DeleteRoleResponse deleteRole(DeleteRoleRequest deleteRoleRequest);
    
    FindCapabilitiesResponse findCapabilities(FindCapabilitiesRequest findCapabilitiesRequest);
    
    FindRolesResponse findRoles(FindRolesRequest findRolesRequest);
    
	FindServiceUsersResponse findServiceUsers(FindServiceUsersRequest findServiceUsersRequest);

    FindUsersResponse findUsers(FindUsersRequest findUsersRequest);
    
    GetCapabilityResponse getCapability(GetCapabilityRequest getCapabilityRequest);
    
    GetPersonsResponse getPersons(GetPersonsRequest getPersonsRequest);
    
	GetServiceUserResponse getServiceUser(GetServiceUserRequest getServiceUserRequest);
    
    GetUserResponse getUser(GetUserRequest getUserRequest);
    
    GetUserRolesResponse getUserRoles(GetUserRolesRequest getUserRolesRequest);
    
    PartiallyUpdateUserResponse partiallyUpdateUser(PartiallyUpdateUserRequest partiallyUpdateUserRequest);

	PublishEntitiesResponse publishEntities(PublishEntitiesRequest publishEntitiesRequest);
	
    PutUserResponse putUser(PutUserRequest putUserRequest);

    SetRoleUsersResponse setRoleUsers(SetRoleUsersRequest setRoleUsersRequest);

	UploadEntitiesResponse uploadEntities(UploadEntitiesRequest uploadEntitiesRequest);

}
