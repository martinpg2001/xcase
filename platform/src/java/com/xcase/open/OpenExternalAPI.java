/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open;

import com.xcase.open.transputs.*;

/**
 *
 * @author martin
 */
public interface OpenExternalAPI {

    AddClientUsersResponse addClientUsers(AddClientUsersRequest addClientUsersRequest);

    AddGroupRolesResponse addGroupRoles(AddGroupRolesRequest addGroupRolesRequest);

    AddGroupUsersResponse addGroupUsers(AddGroupUsersRequest addGroupUsersRequest);

    AddMatterUsersResponse addMatterUsers(AddMatterUsersRequest addMatterUsersRequest);

    AddRoleCapabilitiesResponse addRoleCapabilities(AddRoleCapabilitiesRequest addRoleCapabilitiesRequest);

    AddUserRolesResponse addUserRoles(AddUserRolesRequest addUserRolesRequest);

    ChangePartyTypeResponse changePartyType(ChangePartyTypeRequest changePartyTypeRequest);

    ChangePasswordResponse changePassword(ChangePasswordRequest changePasswordRequest);
    
    CreateAuthorizationCodeFromApplicationResponse createAuthorizationCodeFromApplication(CreateAuthorizationCodeFromApplicationRequest createAuthorizationCodeFromApplicationRequest);

    CreateClientAddressResponse createClientAddress(CreateClientAddressRequest createAddressRequest);

    CreateClientAliasResponse createClientAlias(CreateClientAliasRequest createAliasRequest);

    CreateClientAttachmentResponse createClientAttachment(CreateClientAttachmentRequest createAttachmentRequest);

    CreateClientResponse createClient(CreateClientRequest createClientRequest);

    CreateClientNoteResponse createClientNote(CreateClientNoteRequest createNoteRequest);

    CreateClientsResponse createClients(CreateClientsRequest createClientsRequest);

    CreateClientWarningResponse createClientWarning(CreateClientWarningRequest createClientWarningRequest);

    CreateGroupResponse createGroup(CreateGroupRequest createGroupRequest);

    CreateMatterResponse createMatter(CreateMatterRequest createMatterRequest);

    CreateMatterAddressResponse createMatterAddress(CreateMatterAddressRequest createMatterAddressRequest);

    CreateMatterAttachmentResponse createMatterAttachment(CreateMatterAttachmentRequest createMatterAttachmentRequest);

    CreateMatterNoteResponse createMatterNote(CreateMatterNoteRequest createMatterNoteRequest);

    CreateMattersResponse createMatters(CreateMattersRequest createMattersRequest);

    CreateMatterWarningResponse createMatterWarning(CreateMatterWarningRequest createMatterWarningRequest);

    CreateOrReplaceGroupClientSecurityResponse createOrReplaceGroupEntitySecurity(CreateOrReplaceGroupClientSecurityRequest createOrReplaceGroupEntitySecurityRequest);

    CreateOrReplaceUserClientSecurityResponse createOrReplaceUserEntitySecurity(CreateOrReplaceUserClientSecurityRequest createOrReplaceUserEntitySecurityRequest);

    CreatePartyAddressResponse createPartyAddress(CreatePartyAddressRequest createPartyAddressRequest);

    CreatePartiesResponse createParties(CreatePartiesRequest createPartiesRequest);

    CreatePartyResponse createParty(CreatePartyRequest createPartyRequest);

    CreatePartyAliasResponse createPartyAlias(CreatePartyAliasRequest createPartyAliasRequest);

    CreatePartyAttachmentResponse createPartyAttachment(CreatePartyAttachmentRequest createPartyAttachmentRequest);

    CreatePartyNoteResponse createPartyNote(CreatePartyNoteRequest createPartyNoteRequest);

    CreatePartyWarningResponse createPartyWarning(CreatePartyWarningRequest createPartyWarningRequest);

    CreateRoleResponse createRole(CreateRoleRequest createRoleRequest);
    
    CreateTokensFromUsernamePasswordResponse createTokensFromUsernamePassword(CreateTokensFromUsernamePasswordRequest createTokensFromUsernamePasswordRequest);

    CreateUserResponse createUser(CreateUserRequest createUserRequest);

    DeactivateUserResponse deactivateUser(DeactivateUserRequest deactivateUserRequest);

    DeleteClientAddressResponse deleteAddress(DeleteClientAddressRequest deleteAddressRequest);

    DeleteClientAliasResponse deleteAlias(DeleteClientAliasRequest deleteAliasRequest);

    DeleteEntitySecurityResponse deleteEntitySecurity(DeleteEntitySecurityRequest deleteEntitySecurityRequest);

    DeleteClientWarningResponse deleteClientWarning(DeleteClientWarningRequest deleteEntityWarningRequest);

    DeleteUserEntitySecurityResponse deleteUserEntitySecurity(DeleteUserEntitySecurityRequest deleteUserEntitySecurityRequest);

    DisableUserLoginResponse disableUserLogin(DisableUserLoginRequest disableUserLogin);

    EnableUserLoginResponse enableUserLogin(EnableUserLoginRequest enableUserLoginResponse);

    GetClientResponse getClient(GetClientRequest getClientRequest);

    GetDocumentsResponse getDocuments(GetDocumentsRequest getDocumentsRequest);

    GetEntitySecurityResponse getEntitySecurity(GetEntitySecurityRequest getEntitySecurityRequest);

    GetGroupResponse getGroup(GetGroupRequest getGroupRequest);

    GetMatterResponse getMatter(GetMatterRequest getMatterRequest);

    GetPartyResponse getParty(GetPartyRequest getPartyRequest);

    GetRoleResponse getRole(GetRoleRequest getRoleRequest);

    GetUserResponse getUser(GetUserRequest getUserRequest);

    GetTermResponse getTerm(GetTermRequest getTermRequest);

    GetTermCategoriesResponse getTermCategories(GetTermCategoriesRequest getTermCategoriesRequest);

    GetTermDocumentTypesResponse getTermDocumentTypes(GetTermDocumentTypesRequest getTermDocumentTypesRequest);

    GetTermsResponse getTerms(GetTermsRequest getTermsRequest);

    GetTermsForEntityResponse getTermsForEntity(GetTermsForEntityRequest getTermsForEntityRequest);

    RemoveClientUsersResponse removeClientUsers(RemoveClientUsersRequest removeClientUsersRequest);

    RemoveGroupRolesResponse removeGroupRoles(RemoveGroupRolesRequest removeGroupRolesRequest);

    RemoveGroupUsersResponse removeGroupUsers(RemoveGroupUsersRequest removeGroupUsersRequest);

    RemoveMatterUsersResponse removeMatterUsers(RemoveMatterUsersRequest removeMatterUsersRequest);

    RemoveRoleCapabilitiesResponse removeRoleCapabilities(RemoveRoleCapabilitiesRequest removeRoleCapabilitiesRequest);

    RemoveUserRolesResponse removeUserRoles(RemoveUserRolesRequest removeUserRolesRequest);

    SetClientUsersResponse setClientUsers(SetClientUsersRequest setClientUsersRequest);

    SetGroupRolesResponse setGroupRoles(SetGroupRolesRequest setGroupRolesRequest);

    SetGroupUsersResponse setGroupUsers(SetGroupUsersRequest setGroupUsersRequest);

    SetMatterUsersResponse setMatterUsers(SetMatterUsersRequest setMatterUsersRequest);

    SetRoleCapabilitiesResponse setRoleCapabilities(SetRoleCapabilitiesRequest setRoleCapabilitiesRequest);

    SetUserDepartmentsResponse setUserDepartments(SetUserDepartmentsRequest setUserDepartmentsRequest);

    SetUserImageResponse setUserImage(SetUserImageRequest setUserImageRequest);

    SetUserPracticeAreasResponse setUserPracticeAreas(SetUserPracticeAreasRequest setUserPracticeAreasRequest);

    SetUserRolesResponse setUserRoles(SetUserRolesRequest setUserRolesRequest);

    UpdateClientAddressResponse updateClientAddress(UpdateClientAddressRequest updateClientAddressRequest);

    UpdateClientAliasResponse updateClientAlias(UpdateClientAliasRequest updateClientAliasRequest);

    UpdateClientResponse updateClient(UpdateClientRequest updateClientRequest);

    UpdateClientFinancialDataResponse updateClientFinancialData(UpdateClientFinancialDataRequest updateClientFinancialDataRequest);

    UpdateClientsResponse updateClients(UpdateClientsRequest updateClientsRequest);

    UpdateClientWarningResponse updateClientWarning(UpdateClientWarningRequest updateClientWarningRequest);

    UpdateGroupResponse updateGroup(UpdateGroupRequest updateGroupRequest);

    UpdateMatterAddressResponse updateMatterAddress(UpdateMatterAddressRequest updateMatterAddressRequest);

    UpdateMatterResponse updateMatter(UpdateMatterRequest updateMatterRequest);

    UpdateMatterFinancialDataResponse updateMatterFinancialData(UpdateMatterFinancialDataRequest updateMatterFinancialDataRequest);

    UpdateMattersResponse updateMatters(UpdateMattersRequest updateMattersRequest);

    UpdateMatterWarningResponse updateMatterWarning(UpdateMatterWarningRequest updateMatterWarningRequest);

    UpdatePartyResponse updateParty(UpdatePartyRequest updatePartyRequest);

    UpdatePartyAddressResponse updatePartyAddress(UpdatePartyAddressRequest updatePartyAddressRequest);

    UpdatePartyAliasResponse updatePartyAlias(UpdatePartyAliasRequest updatePartyAliasRequest);

    UpdatePartyWarningResponse updatePartyWarning(UpdatePartyWarningRequest updatePartyWarningRequest);

    UpdateRoleResponse updateRole(UpdateRoleRequest updateRoleRequest);

    UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest);

    UploadTermDocumentResponse uploadTermDocument(UploadTermDocumentRequest uploadTermDocumentRequest);

    UploadTermDocumentsResponse uploadTermDocuments(UploadTermDocumentsRequest uploadTermDocumentsRequest);
}
