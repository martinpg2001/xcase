/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open;

import com.xcase.open.impl.simple.methods.*;
import com.xcase.open.impl.simple.core.OpenConfigurationManager;
import com.xcase.open.transputs.*;
import java.lang.invoke.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class SimpleOpenImpl implements OpenExternalAPI {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * configuration manager
     */
    public OpenConfigurationManager localConfigurationManager = OpenConfigurationManager.getConfigurationManager();

    /**
     * Open action implementation.
     */
    private AddClientUsersMethod addClientUsersMethod = new AddClientUsersMethod();

    /**
     * Open action implementation.
     */
    private AddGroupRolesMethod addGroupRolesMethod = new AddGroupRolesMethod();

    /**
     * Open action implementation.
     */
    private AddGroupUsersMethod addGroupUsersMethod = new AddGroupUsersMethod();

    /**
     * Open action implementation.
     */
    private AddMatterUsersMethod addMatterUsersMethod = new AddMatterUsersMethod();

    /**
     * Open action implementation.
     */
    private AddRoleCapabilitiesMethod addRoleCapabilitiesMethod = new AddRoleCapabilitiesMethod();

    /**
     * Open action implementation.
     */
    private AddUserRolesMethod addUserRolesMethod = new AddUserRolesMethod();

    /**
     * Open action implementation.
     */
    private ChangePartyTypeMethod changePartyTypeMethod = new ChangePartyTypeMethod();

    /**
     * Open action implementation.
     */
    private ChangePasswordMethod changePasswordMethod = new ChangePasswordMethod();
    
    /**
     * Open action implementation.
     */
    private CreateAuthorizationCodeFromApplicationMethod createAuthorizationCodeFromApplicationMethod = new CreateAuthorizationCodeFromApplicationMethod();

    /**
     * Open action implementation.
     */
    private CreateClientAddressMethod createAddressMethod = new CreateClientAddressMethod();

    /**
     * Open action implementation.
     */
    private CreateClientAliasMethod createAliasMethod = new CreateClientAliasMethod();

    /**
     * Open action implementation.
     */
    private CreateClientAttachmentMethod createAttachmentMethod = new CreateClientAttachmentMethod();

    /**
     * Open action implementation.
     */
    private CreateClientMethod createClientMethod = new CreateClientMethod();

    /**
     * Open action implementation.
     */
    private CreateClientNoteMethod createClientNoteMethod = new CreateClientNoteMethod();

    /**
     * Open action implementation.
     */
    private CreateClientsMethod createClientsMethod = new CreateClientsMethod();

    /**
     * Open action implementation.
     */
    private CreateClientWarningMethod createClientWarningMethod = new CreateClientWarningMethod();

    /**
     * Open action implementation.
     */
    private CreateGroupMethod createGroupMethod = new CreateGroupMethod();

    /**
     * Open action implementation.
     */
    private CreateMatterMethod createMatterMethod = new CreateMatterMethod();

    /**
     * Open action implementation.
     */
    private CreateMatterAddressMethod createMatterAddressMethod = new CreateMatterAddressMethod();

    /**
     * Open action implementation.
     */
    private CreateMatterAttachmentMethod createMatterAttachmentMethod = new CreateMatterAttachmentMethod();

    /**
     * Open action implementation.
     */
    private CreateMatterNoteMethod createMatterNoteMethod = new CreateMatterNoteMethod();

    /**
     * Open action implementation.
     */
    private CreateMattersMethod createMattersMethod = new CreateMattersMethod();

    /**
     * Open action implementation.
     */
    private CreateMatterWarningMethod createMatterWarningMethod = new CreateMatterWarningMethod();

    /**
     * Open action implementation.
     */
    private CreateOrReplaceGroupClientSecurityMethod createOrReplaceGroupEntitySecurityMethod = new CreateOrReplaceGroupClientSecurityMethod();

    /**
     * Open action implementation.
     */
    private CreateOrReplaceUserClientSecurityMethod createOrReplaceUserEntitySecurityMethod = new CreateOrReplaceUserClientSecurityMethod();

    /**
     * Open action implementation.
     */
    private CreatePartyAddressMethod createPartyAddressMethod = new CreatePartyAddressMethod();

    /**
     * Open action implementation.
     */
    private CreatePartyAliasMethod createPartyAliasMethod = new CreatePartyAliasMethod();

    /**
     * Open action implementation.
     */
    private CreatePartyAttachmentMethod createPartyAttachmentMethod = new CreatePartyAttachmentMethod();

    /**
     * Open action implementation.
     */
    private CreatePartiesMethod createPartiesMethod = new CreatePartiesMethod();

    /**
     * Open action implementation.
     */
    private CreatePartyMethod createPartyMethod = new CreatePartyMethod();

    /**
     * Open action implementation.
     */
    private CreatePartyNoteMethod createPartyNoteMethod = new CreatePartyNoteMethod();

    /**
     * Open action implementation.
     */
    private CreatePartyWarningMethod createPartyWarningMethod = new CreatePartyWarningMethod();

    /**
     * Open action implementation.
     */
    private CreateRoleMethod createRoleMethod = new CreateRoleMethod();
    
    /**
     * Open action implementation.
     */
    private CreateTokensFromAuthorizationCodeMethod createTokensFromAuthorizationCodeMethod = new CreateTokensFromAuthorizationCodeMethod();
    
    /**
     * Open action implementation.
     */
    private CreateTokensFromRefreshTokenMethod createTokensFromRefreshTokenMethod = new CreateTokensFromRefreshTokenMethod();
    
    /**
     * Open action implementation.
     */
    private CreateTokensFromUsernamePasswordMethod createTokensFromUsernamePasswordMethod = new CreateTokensFromUsernamePasswordMethod();

    /**
     * Open action implementation.
     */
    private CreateUserMethod createUserMethod = new CreateUserMethod();

    /**
     * Open action implementation.
     */
    private DeactivateUserMethod deactivateUserMethod = new DeactivateUserMethod();

    /**
     * Open action implementation.
     */
    private DeleteClientAddressMethod deleteAddressMethod = new DeleteClientAddressMethod();

    /**
     * Open action implementation.
     */
    private DeleteClientAliasMethod deleteAliasMethod = new DeleteClientAliasMethod();

    /**
     * Open action implementation.
     */
    private DeleteEntitySecurityMethod deleteEntitySecurityMethod = new DeleteEntitySecurityMethod();

    /**
     * Open action implementation.
     */
    private DeleteClientWarningMethod deleteEntityWarningMethod = new DeleteClientWarningMethod();

    /**
     * Open action implementation.
     */
    private DeleteUserEntitySecurityMethod deleteUserEntitySecurityMethod = new DeleteUserEntitySecurityMethod();

    /**
     * Open action implementation.
     */
    private DisableUserLoginMethod disableUserLoginMethod = new DisableUserLoginMethod();

    /**
     * Open action implementation.
     */
    private EnableUserLoginMethod enableUserLoginMethod = new EnableUserLoginMethod();

    /**
     * Open action implementation.
     */
    private GetClientMethod getClientMethod = new GetClientMethod();

    /**
     * Open action implementation.
     */
    private GetDocumentsMethod getDocumentsMethod = new GetDocumentsMethod();

    /**
     * Open action implementation.
     */
    private GetEntitySecurityMethod getEntitySecurityMethod = new GetEntitySecurityMethod();

    /**
     * Open action implementation.
     */
    private GetGroupMethod getGroupMethod = new GetGroupMethod();

    /**
     * Open action implementation.
     */
    private GetMatterMethod getMatterMethod = new GetMatterMethod();

    /**
     * Open action implementation.
     */
    private GetPartyMethod getPartyMethod = new GetPartyMethod();

    /**
     * Open action implementation.
     */
    private GetRoleMethod getRoleMethod = new GetRoleMethod();

    /**
     * Open action implementation.
     */
    private GetUserMethod getUserMethod = new GetUserMethod();

    /**
     * Open action implementation.
     */
    private GetTermMethod getTermMethod = new GetTermMethod();

    /**
     * Open action implementation.
     */
    private GetTermCategoriesMethod getTermCategoriesMethod = new GetTermCategoriesMethod();

    /**
     * Open action implementation.
     */
    private GetTermDocumentTypesMethod getTermDocumentTypesMethod = new GetTermDocumentTypesMethod();

    /**
     * Open action implementation.
     */
    private GetTermsMethod getTermsMethod = new GetTermsMethod();

    /**
     * Open action implementation.
     */
    private GetTermsForEntityMethod getTermsForEntityMethod = new GetTermsForEntityMethod();

    /**
     * Open action implementation.
     */
    private RemoveClientUsersMethod removeClientUsersMethod = new RemoveClientUsersMethod();

    /**
     * Open action implementation.
     */
    private RemoveGroupRolesMethod removeGroupRolesMethod = new RemoveGroupRolesMethod();

    /**
     * Open action implementation.
     */
    private RemoveGroupUsersMethod removeGroupUsersMethod = new RemoveGroupUsersMethod();

    /**
     * Open action implementation.
     */
    private RemoveMatterUsersMethod removeMatterUsersMethod = new RemoveMatterUsersMethod();

    /**
     * Open action implementation.
     */
    private RemoveRoleCapabilitiesMethod removeRoleCapabilitiesMethod = new RemoveRoleCapabilitiesMethod();

    /**
     * Open action implementation.
     */
    private RemoveUserRolesMethod removeUserRolesMethod = new RemoveUserRolesMethod();

    /**
     * Open action implementation.
     */
    private SetClientUsersMethod setClientUsersMethod = new SetClientUsersMethod();

    /**
     * Open action implementation.
     */
    private SetGroupRolesMethod setGroupRolesMethod = new SetGroupRolesMethod();

    /**
     * Open action implementation.
     */
    private SetGroupUsersMethod setGroupUsersMethod = new SetGroupUsersMethod();

    /**
     * Open action implementation.
     */
    private SetMatterUsersMethod setMatterUsersMethod = new SetMatterUsersMethod();

    /**
     * Open action implementation.
     */
    private SetRoleCapabilitiesMethod setRoleCapabilitiesMethod = new SetRoleCapabilitiesMethod();

    /**
     * Open action implementation.
     */
    private SetUserDepartmentsMethod setUserDepartmentsMethod = new SetUserDepartmentsMethod();

    /**
     * Open action implementation.
     */
    private SetUserImageMethod setUserImageMethod = new SetUserImageMethod();

    /**
     * Open action implementation.
     */
    private SetUserPracticeAreasMethod setUserPracticeAreasMethod = new SetUserPracticeAreasMethod();

    /**
     * Open action implementation.
     */
    private SetUserRolesMethod setUserRolesMethod = new SetUserRolesMethod();

    /**
     * Open action implementation.
     */
    private UpdateClientAddressMethod updateClientAddressMethod = new UpdateClientAddressMethod();

    /**
     * Open action implementation.
     */
    private UpdateClientAliasMethod updateClientAliasMethod = new UpdateClientAliasMethod();

    /**
     * Open action implementation.
     */
    private UpdateClientMethod updateClientMethod = new UpdateClientMethod();

    /**
     * Open action implementation.
     */
    private UpdateClientFinancialDataMethod updateClientFinancialDataMethod = new UpdateClientFinancialDataMethod();

    /**
     * Open action implementation.
     */
    private UpdateClientsMethod updateClientsMethod = new UpdateClientsMethod();

    /**
     * Open action implementation.
     */
    private UpdateClientWarningMethod updateClientWarningMethod = new UpdateClientWarningMethod();

    /**
     * Open action implementation.
     */
    private UpdateGroupMethod updateGroupMethod = new UpdateGroupMethod();

    /**
     * Open action implementation.
     */
    private UpdateMatterAddressMethod updateMatterAddressMethod = new UpdateMatterAddressMethod();

    /**
     * Open action implementation.
     */
    private UpdateMatterMethod updateMatterMethod = new UpdateMatterMethod();

    /**
     * Open action implementation.
     */
    private UpdateMatterFinancialDataMethod updateMatterFinancialDataMethod = new UpdateMatterFinancialDataMethod();

    /**
     * Open action implementation.
     */
    private UpdateMattersMethod updateMattersMethod = new UpdateMattersMethod();

    /**
     * Open action implementation.
     */
    private UpdateMatterWarningMethod updateMatterWarningMethod = new UpdateMatterWarningMethod();

    /**
     * Open action implementation.
     */
    private UpdatePartyMethod updatePartyMethod = new UpdatePartyMethod();

    /**
     * Open action implementation.
     */
    private UpdatePartyAddressMethod updatePartyAddressMethod = new UpdatePartyAddressMethod();

    /**
     * Open action implementation.
     */
    private UpdatePartyAliasMethod updatePartyAliasMethod = new UpdatePartyAliasMethod();

    /**
     * Open action implementation.
     */
    private UpdatePartyWarningMethod updatePartyWarningMethod = new UpdatePartyWarningMethod();

    /**
     * Open action implementation.
     */
    private UpdateRoleMethod updateRoleMethod = new UpdateRoleMethod();

    /**
     * Open action implementation.
     */
    private UpdateUserMethod updateUserMethod = new UpdateUserMethod();

    /**
     * Open action implementation.
     */
    private UploadTermDocumentMethod uploadTermDocumentMethod = new UploadTermDocumentMethod();

    /**
     * Open action implementation.
     */
    private UploadTermDocumentsMethod uploadTermDocumentsMethod = new UploadTermDocumentsMethod();

    public AddClientUsersResponse addClientUsers(AddClientUsersRequest addClientUsersRequest) {
        return addClientUsersMethod.addClientUsers(addClientUsersRequest);
    }

    public AddGroupRolesResponse addGroupRoles(AddGroupRolesRequest addGroupRolesRequest) {
        return addGroupRolesMethod.addGroupRoles(addGroupRolesRequest);
    }

    public AddGroupUsersResponse addGroupUsers(AddGroupUsersRequest addGroupUsersRequest) {
        return addGroupUsersMethod.addGroupUsers(addGroupUsersRequest);
    }

    public AddMatterUsersResponse addMatterUsers(AddMatterUsersRequest addMatterUsersRequest) {
        return addMatterUsersMethod.addMatterUsers(addMatterUsersRequest);
    }

    public AddRoleCapabilitiesResponse addRoleCapabilities(AddRoleCapabilitiesRequest addRoleCapabilitiesRequest) {
        return addRoleCapabilitiesMethod.addRoleCapabilities(addRoleCapabilitiesRequest);
    }

    public AddUserRolesResponse addUserRoles(AddUserRolesRequest addUserRolesRequest) {
        return addUserRolesMethod.addUserRoles(addUserRolesRequest);
    }

    public ChangePartyTypeResponse changePartyType(ChangePartyTypeRequest changePartyTypeRequest) {
        return changePartyTypeMethod.changePartyType(changePartyTypeRequest);
    }

    public ChangePasswordResponse changePassword(ChangePasswordRequest changePasswordRequest) {
        return changePasswordMethod.changePassword(changePasswordRequest);
    }
    
    public CreateAuthorizationCodeFromApplicationResponse createAuthorizationCodeFromApplication(CreateAuthorizationCodeFromApplicationRequest createAuthorizationCodeFromApplicationRequest) {
        return createAuthorizationCodeFromApplicationMethod.createAuthorizationCodeFromApplication(createAuthorizationCodeFromApplicationRequest);
    }

    public CreateClientAddressResponse createClientAddress(CreateClientAddressRequest createAddressRequest) {
        return createAddressMethod.createClientAddress(createAddressRequest);
    }

    public CreateClientAliasResponse createClientAlias(CreateClientAliasRequest createAliasRequest) {
        return createAliasMethod.createClientAlias(createAliasRequest);
    }

    public CreateClientAttachmentResponse createClientAttachment(CreateClientAttachmentRequest createAttachmentRequest) {
        return createAttachmentMethod.createClientAttachment(createAttachmentRequest);
    }

    public CreateClientResponse createClient(CreateClientRequest createClientRequest) {
        return createClientMethod.createClient(createClientRequest);
    }

    public CreateClientNoteResponse createClientNote(CreateClientNoteRequest createNoteRequest) {
        return createClientNoteMethod.createClientNote(createNoteRequest);
    }

    public CreateClientsResponse createClients(CreateClientsRequest createClientsRequest) {
        return createClientsMethod.createClients(createClientsRequest);
    }

    public CreateClientWarningResponse createClientWarning(CreateClientWarningRequest createClientWarningRequest) {
        return createClientWarningMethod.createClientWarning(createClientWarningRequest);
    }

    public CreateGroupResponse createGroup(CreateGroupRequest createGroupRequest) {
        return createGroupMethod.createGroup(createGroupRequest);
    }

    public CreateMatterResponse createMatter(CreateMatterRequest createMatterRequest) {
        return createMatterMethod.createMatter(createMatterRequest);
    }

    public CreateMatterAddressResponse createMatterAddress(CreateMatterAddressRequest createMatterAddressRequest) {
        return createMatterAddressMethod.createMatterAddress(createMatterAddressRequest);
    }

    public CreateMatterAttachmentResponse createMatterAttachment(CreateMatterAttachmentRequest createMatterAttachmentRequest) {
        return createMatterAttachmentMethod.createMatterAttachment(createMatterAttachmentRequest);
    }

    public CreateMatterNoteResponse createMatterNote(CreateMatterNoteRequest createMatterNoteRequest) {
        return createMatterNoteMethod.createMatterNote(createMatterNoteRequest);
    }

    public CreateMattersResponse createMatters(CreateMattersRequest createMattersRequest) {
        return createMattersMethod.createMatters(createMattersRequest);
    }

    public CreateMatterWarningResponse createMatterWarning(CreateMatterWarningRequest createMatterWarningRequest) {
        return createMatterWarningMethod.createMatterWarning(createMatterWarningRequest);
    }

    public CreateOrReplaceGroupClientSecurityResponse createOrReplaceGroupEntitySecurity(CreateOrReplaceGroupClientSecurityRequest createOrReplaceGroupEntitySecurityRequest) {
        return createOrReplaceGroupEntitySecurityMethod.createOrReplaceGroupEntitySecurity(createOrReplaceGroupEntitySecurityRequest);
    }

    public CreateOrReplaceUserClientSecurityResponse createOrReplaceUserEntitySecurity(CreateOrReplaceUserClientSecurityRequest createOrReplaceUserEntitySecurityRequest) {
        return createOrReplaceUserEntitySecurityMethod.createOrReplaceUserEntitySecurity(createOrReplaceUserEntitySecurityRequest);
    }

    public CreatePartiesResponse createParties(CreatePartiesRequest createPartiesRequest) {
        return createPartiesMethod.createParties(createPartiesRequest);
    }

    public CreatePartyResponse createParty(CreatePartyRequest createPartyRequest) {
        return createPartyMethod.createParty(createPartyRequest);
    }

    public CreatePartyAddressResponse createPartyAddress(CreatePartyAddressRequest createAddressRequest) {
        return createPartyAddressMethod.createPartyAddress(createAddressRequest);
    }

    public CreatePartyAliasResponse createPartyAlias(CreatePartyAliasRequest createPartyAliasRequest) {
        return createPartyAliasMethod.createPartyAlias(createPartyAliasRequest);
    }

    public CreatePartyAttachmentResponse createPartyAttachment(CreatePartyAttachmentRequest createPartyAttachmentRequest) {
        return createPartyAttachmentMethod.createPartyAttachment(createPartyAttachmentRequest);
    }

    public CreatePartyNoteResponse createPartyNote(CreatePartyNoteRequest createPartyNoteRequest) {
        return createPartyNoteMethod.createPartyNote(createPartyNoteRequest);
    }

    public CreatePartyWarningResponse createPartyWarning(CreatePartyWarningRequest createPartyWarningRequest) {
        return createPartyWarningMethod.createPartyWarning(createPartyWarningRequest);
    }

    public CreateRoleResponse createRole(CreateRoleRequest createRoleRequest) {
        return createRoleMethod.createRole(createRoleRequest);
    }
    
    public CreateTokensFromAuthorizationCodeResponse createTokensFromAuthorizationCode(CreateTokensFromAuthorizationCodeRequest createTokensFromAuthorizationCodeRequest) {
        return createTokensFromAuthorizationCodeMethod.createTokensFromAuthorizationCode(createTokensFromAuthorizationCodeRequest);
    }
    
    public CreateTokensFromRefreshTokenResponse createTokensFromRefreshToken(CreateTokensFromRefreshTokenRequest createTokensFromRefreshTokenRequest) {
        return createTokensFromRefreshTokenMethod.createTokensFromRefreshToken(createTokensFromRefreshTokenRequest);
    }
    
    public CreateTokensFromUsernamePasswordResponse createTokensFromUsernamePassword(CreateTokensFromUsernamePasswordRequest createTokensFromUsernamePasswordRequest) {
        return createTokensFromUsernamePasswordMethod.createTokensFromUsernamePassword(createTokensFromUsernamePasswordRequest);
    }

    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        return createUserMethod.createUser(createUserRequest);
    }

    public DeactivateUserResponse deactivateUser(DeactivateUserRequest deactivateUserRequest) {
        return deactivateUserMethod.deactivateUser(deactivateUserRequest);
    }

    public DeleteClientAddressResponse deleteAddress(DeleteClientAddressRequest deleteAddressRequest) {
        return deleteAddressMethod.deleteAddress(deleteAddressRequest);
    }

    public DeleteClientAliasResponse deleteAlias(DeleteClientAliasRequest deleteAliasRequest) {
        return deleteAliasMethod.deleteAlias(deleteAliasRequest);
    }

    public DeleteEntitySecurityResponse deleteEntitySecurity(DeleteEntitySecurityRequest deleteEntitySecurityRequest) {
        return deleteEntitySecurityMethod.deleteEntitySecurity(deleteEntitySecurityRequest);
    }

    public DeleteClientWarningResponse deleteClientWarning(DeleteClientWarningRequest deleteEntityWarningRequest) {
        return deleteEntityWarningMethod.deleteClientWarning(deleteEntityWarningRequest);
    }

    public DeleteUserEntitySecurityResponse deleteUserEntitySecurity(DeleteUserEntitySecurityRequest deleteUserEntitySecurityRequest) {
        return deleteUserEntitySecurityMethod.deleteUserEntitySecurity(deleteUserEntitySecurityRequest);
    }

    public DisableUserLoginResponse disableUserLogin(DisableUserLoginRequest disableUserLoginRequest) {
        return disableUserLoginMethod.disableUserLogin(disableUserLoginRequest);
    }

    public EnableUserLoginResponse enableUserLogin(EnableUserLoginRequest enableUserLoginRequest) {
        return enableUserLoginMethod.enableUserLogin(enableUserLoginRequest);
    }

    public GetClientResponse getClient(GetClientRequest getClientRequest) {
        return getClientMethod.getClient(getClientRequest);
    }

    public GetDocumentsResponse getDocuments(GetDocumentsRequest getDocumentsRequest) {
        return getDocumentsMethod.getDocuments(getDocumentsRequest);
    }

    public GetEntitySecurityResponse getEntitySecurity(GetEntitySecurityRequest getEntitySecurityRequest) {
        return getEntitySecurityMethod.getEntitySecurity(getEntitySecurityRequest);
    }

    public GetGroupResponse getGroup(GetGroupRequest getGroupRequest) {
        return getGroupMethod.getGroup(getGroupRequest);
    }

    public GetMatterResponse getMatter(GetMatterRequest getMatterRequest) {
        return getMatterMethod.getMatter(getMatterRequest);
    }

    public GetPartyResponse getParty(GetPartyRequest getPartyRequest) {
        return getPartyMethod.getParty(getPartyRequest);
    }

    public GetRoleResponse getRole(GetRoleRequest getRoleRequest) {
        return getRoleMethod.getRole(getRoleRequest);
    }

    public GetUserResponse getUser(GetUserRequest getUserRequest) {
        return getUserMethod.getUser(getUserRequest);
    }

    public GetTermResponse getTerm(GetTermRequest getTermRequest) {
        return getTermMethod.getTerm(getTermRequest);
    }

    public GetTermCategoriesResponse getTermCategories(GetTermCategoriesRequest getTermCategoriesRequest) {
        return getTermCategoriesMethod.getTermCategories(getTermCategoriesRequest);
    }

    public GetTermDocumentTypesResponse getTermDocumentTypes(GetTermDocumentTypesRequest getTermDocumentTypesRequest) {
        return getTermDocumentTypesMethod.getTermDocumentTypes(getTermDocumentTypesRequest);
    }

    public GetTermsResponse getTerms(GetTermsRequest getTermsRequest) {
        return getTermsMethod.getTerms(getTermsRequest);
    }

    public GetTermsForEntityResponse getTermsForEntity(GetTermsForEntityRequest getTermsForEntityRequest) {
        return getTermsForEntityMethod.getTermsForEntity(getTermsForEntityRequest);
    }

    public RemoveClientUsersResponse removeClientUsers(RemoveClientUsersRequest removeClientUsersRequest) {
        return removeClientUsersMethod.removeClientUsers(removeClientUsersRequest);
    }

    public RemoveGroupRolesResponse removeGroupRoles(RemoveGroupRolesRequest removeGroupRolesRequest) {
        return removeGroupRolesMethod.removeGroupRoles(removeGroupRolesRequest);
    }

    public RemoveGroupUsersResponse removeGroupUsers(RemoveGroupUsersRequest removeGroupUsersRequest) {
        return removeGroupUsersMethod.removeGroupUsers(removeGroupUsersRequest);
    }

    public RemoveMatterUsersResponse removeMatterUsers(RemoveMatterUsersRequest removeMatterUsersRequest) {
        return removeMatterUsersMethod.removeMatterUsers(removeMatterUsersRequest);
    }

    public RemoveRoleCapabilitiesResponse removeRoleCapabilities(RemoveRoleCapabilitiesRequest removeRoleCapabilitiesRequest) {
        return removeRoleCapabilitiesMethod.removeRoleCapabilities(removeRoleCapabilitiesRequest);
    }

    public RemoveUserRolesResponse removeUserRoles(RemoveUserRolesRequest removeUserRolesRequest) {
        return removeUserRolesMethod.removeUserRoles(removeUserRolesRequest);
    }

    public SetClientUsersResponse setClientUsers(SetClientUsersRequest setClientUsersRequest) {
        return setClientUsersMethod.setClientUsers(setClientUsersRequest);
    }

    public SetGroupRolesResponse setGroupRoles(SetGroupRolesRequest setGroupRolesRequest) {
        return setGroupRolesMethod.setGroupRoles(setGroupRolesRequest);
    }

    public SetGroupUsersResponse setGroupUsers(SetGroupUsersRequest setGroupUsersRequest) {
        return setGroupUsersMethod.setGroupUsers(setGroupUsersRequest);
    }

    public SetMatterUsersResponse setMatterUsers(SetMatterUsersRequest setMatterUsersRequest) {
        return setMatterUsersMethod.setMatterUsers(setMatterUsersRequest);
    }

    public SetRoleCapabilitiesResponse setRoleCapabilities(SetRoleCapabilitiesRequest setRoleCapabilitiesRequest) {
        return setRoleCapabilitiesMethod.setRoleCapabilities(setRoleCapabilitiesRequest);
    }

    public SetUserDepartmentsResponse setUserDepartments(SetUserDepartmentsRequest setUserDepartmentsRequest) {
        return setUserDepartmentsMethod.setUserDepartments(setUserDepartmentsRequest);
    }

    public SetUserImageResponse setUserImage(SetUserImageRequest setUserImageRequest) {
        return setUserImageMethod.setUserImage(setUserImageRequest);
    }

    public SetUserPracticeAreasResponse setUserPracticeAreas(SetUserPracticeAreasRequest setUserPracticeAreasRequest) {
        return setUserPracticeAreasMethod.setUserPracticeAreas(setUserPracticeAreasRequest);
    }

    public SetUserRolesResponse setUserRoles(SetUserRolesRequest setUserRolesRequest) {
        return setUserRolesMethod.setUserRoles(setUserRolesRequest);
    }

    public UpdateClientAddressResponse updateClientAddress(UpdateClientAddressRequest updateAddressRequest) {
        return updateClientAddressMethod.updateClientAddress(updateAddressRequest);
    }

    public UpdateClientAliasResponse updateClientAlias(UpdateClientAliasRequest updateAliasRequest) {
        return updateClientAliasMethod.updateClientAlias(updateAliasRequest);
    }

    public UpdateClientResponse updateClient(UpdateClientRequest updateClientRequest) {
        return updateClientMethod.updateClient(updateClientRequest);
    }

    public UpdateClientFinancialDataResponse updateClientFinancialData(UpdateClientFinancialDataRequest updateClientFinancialDataRequest) {
        return updateClientFinancialDataMethod.updateClientFinancialData(updateClientFinancialDataRequest);
    }

    public UpdateClientsResponse updateClients(UpdateClientsRequest updateClientsRequest) {
        return updateClientsMethod.updateClients(updateClientsRequest);
    }

    public UpdateClientWarningResponse updateClientWarning(UpdateClientWarningRequest updateClientWarningRequest) {
        return updateClientWarningMethod.updateClientWarning(updateClientWarningRequest);
    }

    public UpdateGroupResponse updateGroup(UpdateGroupRequest updateGroupRequest) {
        return updateGroupMethod.updateGroup(updateGroupRequest);
    }

    public UpdateMatterResponse updateMatter(UpdateMatterRequest updateMatterRequest) {
        return updateMatterMethod.updateMatter(updateMatterRequest);
    }

    public UpdateMatterAddressResponse updateMatterAddress(UpdateMatterAddressRequest updateMatterAddressRequest) {
        return updateMatterAddressMethod.updateMatterAddress(updateMatterAddressRequest);
    }

    public UpdateMatterFinancialDataResponse updateMatterFinancialData(UpdateMatterFinancialDataRequest updateMatterFinancialDataRequest) {
        return updateMatterFinancialDataMethod.updateMatterFinancialData(updateMatterFinancialDataRequest);
    }

    public UpdateMattersResponse updateMatters(UpdateMattersRequest updateMattersRequest) {
        return updateMattersMethod.updateMatters(updateMattersRequest);
    }

    public UpdateMatterWarningResponse updateMatterWarning(UpdateMatterWarningRequest updateMatterWarningRequest) {
        return updateMatterWarningMethod.updateMatterWarning(updateMatterWarningRequest);
    }

    public UpdatePartyResponse updateParty(UpdatePartyRequest updatePartyRequest) {
        return updatePartyMethod.updateParty(updatePartyRequest);
    }

    public UpdatePartyAddressResponse updatePartyAddress(UpdatePartyAddressRequest updateAddressRequest) {
        return updatePartyAddressMethod.updatePartyAddress(updateAddressRequest);
    }

    public UpdatePartyAliasResponse updatePartyAlias(UpdatePartyAliasRequest updateAliasRequest) {
        return updatePartyAliasMethod.updatePartyAlias(updateAliasRequest);
    }

    public UpdatePartyWarningResponse updatePartyWarning(UpdatePartyWarningRequest updateWarningRequest) {
        return updatePartyWarningMethod.updatePartyWarning(updateWarningRequest);
    }

    public UpdateRoleResponse updateRole(UpdateRoleRequest updateRoleRequest) {
        return updateRoleMethod.updateRole(updateRoleRequest);
    }

    public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) {
        return updateUserMethod.updateUser(updateUserRequest);
    }

    public UploadTermDocumentResponse uploadTermDocument(UploadTermDocumentRequest uploadTermDocumentRequest) {
        return uploadTermDocumentMethod.uploadTermDocument(uploadTermDocumentRequest);
    }

    public UploadTermDocumentsResponse uploadTermDocuments(UploadTermDocumentsRequest uploadTermDocumentsRequest) {
        return uploadTermDocumentsMethod.uploadTermDocuments(uploadTermDocumentsRequest);
    }
}
