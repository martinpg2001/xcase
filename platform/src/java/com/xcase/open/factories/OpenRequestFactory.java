/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.factories;

import com.xcase.open.transputs.*;
import java.lang.invoke.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class OpenRequestFactory extends BaseOpenFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * create request object.
     *
     * @return request object
     */
    public static AddClientUsersRequest createAddClientUsersRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.AddClientUsersRequest");
        return (AddClientUsersRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static AddGroupRolesRequest createAddGroupRolesRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.AddGroupRolesRequest");
        return (AddGroupRolesRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static AddGroupUsersRequest createAddGroupUsersRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.AddGroupUsersRequest");
        return (AddGroupUsersRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static AddMatterUsersRequest createAddMatterUsersRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.AddMatterUsersRequest");
        return (AddMatterUsersRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static AddRoleCapabilitiesRequest createAddRoleCapabilitiesRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.AddRoleCapabilitiesRequest");
        return (AddRoleCapabilitiesRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static AddUserRolesRequest createAddUserRolesRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.AddUserRolesRequest");
        return (AddUserRolesRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static ChangePartyTypeRequest createChangePartyTypeRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.ChangePartyTypeRequest");
        return (ChangePartyTypeRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static ChangePasswordRequest createChangePasswordRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.ChangePasswordRequest");
        return (ChangePasswordRequest) obj;
    }
    
    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateAuthorizationCodeFromApplicationRequest createCreateAuthorizationCodeFromApplicationRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreateAuthorizationCodeFromApplicationRequest");
        return (CreateAuthorizationCodeFromApplicationRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateClientAddressRequest createCreateClientAddressRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreateClientAddressRequest");
        return (CreateClientAddressRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateClientAliasRequest createCreateClientAliasRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreateClientAliasRequest");
        return (CreateClientAliasRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateClientAttachmentRequest createCreateClientAttachmentRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreateClientAttachmentRequest");
        return (CreateClientAttachmentRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateClientRequest createCreateClientRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreateClientRequest");
        return (CreateClientRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateClientsRequest createCreateClientsRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreateClientsRequest");
        return (CreateClientsRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateClientWarningRequest createCreateClientWarningRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreateClientWarningRequest");
        return (CreateClientWarningRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateGroupRequest createCreateGroupRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreateGroupRequest");
        return (CreateGroupRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateMatterRequest createCreateMatterRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreateMatterRequest");
        return (CreateMatterRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateMatterAddressRequest createCreateMatterAddressRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreateMatterAddressRequest");
        return (CreateMatterAddressRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateMatterAttachmentRequest createCreateMatterAttachmentRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreateMatterAttachmentRequest");
        return (CreateMatterAttachmentRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateMatterNoteRequest createCreateMatterNoteRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreateMatterNoteRequest");
        return (CreateMatterNoteRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateMattersRequest createCreateMattersRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreateMattersRequest");
        return (CreateMattersRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateMatterWarningRequest createCreateMatterWarningRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreateMatterWarningRequest");
        return (CreateMatterWarningRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateClientNoteRequest createCreateNoteRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreateNoteRequest");
        return (CreateClientNoteRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateOrReplaceGroupClientSecurityRequest createCreateOrReplaceGroupEntitySecurityRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreateOrReplaceGroupEntitySecurityRequest");
        return (CreateOrReplaceGroupClientSecurityRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateOrReplaceUserClientSecurityRequest createCreateOrReplaceUserEntitySecurityRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreateOrReplaceUserEntitySecurityRequest");
        return (CreateOrReplaceUserClientSecurityRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreatePartyAddressRequest createCreatePartyAddressRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreatePartyAddressRequest");
        return (CreatePartyAddressRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreatePartyAliasRequest createCreatePartyAliasRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreatePartyAliasRequest");
        return (CreatePartyAliasRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreatePartyAttachmentRequest createCreatePartyAttachmentRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreatePartyAttachmentRequest");
        return (CreatePartyAttachmentRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreatePartiesRequest createCreatePartiesRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreatePartiesRequest");
        return (CreatePartiesRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreatePartyNoteRequest createCreatePartyNoteRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreatePartyNoteRequest");
        return (CreatePartyNoteRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreatePartyRequest createCreatePartyRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreatePartyRequest");
        return (CreatePartyRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreatePartyWarningRequest createCreatePartyWarningRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreatePartyWarningRequest");
        return (CreatePartyWarningRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateRoleRequest createCreateRoleRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreateRoleRequest");
        return (CreateRoleRequest) obj;
    }
    
    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateTokensFromAuthorizationCodeRequest createCreateTokensFromAuthorizationCodeRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreateTokensFromAuthorizationCodeRequest");
        return (CreateTokensFromAuthorizationCodeRequest) obj;
    }
    
    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateTokensFromRefreshTokenRequest createCreateTokensFromRefreshTokenRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreateTokensFromRefreshTokenRequest");
        return (CreateTokensFromRefreshTokenRequest) obj;
    }
    
    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateTokensFromUsernamePasswordRequest createCreateTokensFromUsernamePasswordRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreateTokensFromUsernamePasswordRequest");
        return (CreateTokensFromUsernamePasswordRequest) obj;
    }    

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateUserRequest createCreateUserRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.CreateUserRequest");
        return (CreateUserRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static DeleteClientAddressRequest createDeleteAddressRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.DeleteAddressRequest");
        return (DeleteClientAddressRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static DeleteClientAliasRequest createDeleteAliasRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.DeleteAliasRequest");
        return (DeleteClientAliasRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static DeleteEntitySecurityRequest createDeleteEntitySecurityRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.DeleteEntitySecurityRequest");
        return (DeleteEntitySecurityRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static DeleteClientWarningRequest createDeleteEntityWarningRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.DeleteEntityWarningRequest");
        return (DeleteClientWarningRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static DeleteUserEntitySecurityRequest createDeleteUserEntitySecurityRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.DeleteUserEntitySecurityRequest");
        return (DeleteUserEntitySecurityRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static DisableUserLoginRequest createDisableUserLoginRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.DisableUserLoginRequest");
        return (DisableUserLoginRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static EnableUserLoginRequest createEnableUserLoginRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.EnableUserLoginRequest");
        return (EnableUserLoginRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetClientRequest createGetClientRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.GetClientRequest");
        return (GetClientRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetGroupRequest createGetGroupRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.GetGroupRequest");
        return (GetGroupRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetDocumentsRequest createGetDocumentsRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.GetDocumentsRequest");
        return (GetDocumentsRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetEntitySecurityRequest createGetEntitySecurityRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.GetEntitySecurityRequest");
        return (GetEntitySecurityRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetMatterRequest createGetMatterRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.GetMatterRequest");
        return (GetMatterRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetPartyRequest createGetPartyRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.GetPartyRequest");
        return (GetPartyRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetRoleRequest createGetRoleRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.GetRoleRequest");
        return (GetRoleRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetUserRequest createGetUserRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.GetUserRequest");
        return (GetUserRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetTermRequest createGetTermRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.GetTermRequest");
        return (GetTermRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetTermCategoriesRequest createGetTermCategoriesRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.GetTermCategoriesRequest");
        return (GetTermCategoriesRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetTermDocumentTypesRequest createGetTermDocumentTypesRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.GetTermDocumentTypesRequest");
        return (GetTermDocumentTypesRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetTermsRequest createGetTermsRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.GetTermsRequest");
        return (GetTermsRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetTermsForEntityRequest createGetTermsForEntityRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.GetTermsForEntityRequest");
        return (GetTermsForEntityRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static RemoveClientUsersRequest createRemoveClientUsersRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.RemoveClientUsersRequest");
        return (RemoveClientUsersRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static RemoveGroupRolesRequest createRemoveGroupRolesRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.RemoveGroupRolesRequest");
        return (RemoveGroupRolesRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static RemoveGroupUsersRequest createRemoveGroupUsersRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.RemoveGroupUsersRequest");
        return (RemoveGroupUsersRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static RemoveMatterUsersRequest createRemoveMatterUsersRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.RemoveMatterUsersRequest");
        return (RemoveMatterUsersRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static RemoveRoleCapabilitiesRequest createRemoveRoleCapabilitiesRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.RemoveRoleCapabilitiesRequest");
        return (RemoveRoleCapabilitiesRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static RemoveUserRolesRequest createRemoveUserRolesRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.RemoveUserRolesRequest");
        return (RemoveUserRolesRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static SetClientUsersRequest createSetClientUsersRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.SetClientUsersRequest");
        return (SetClientUsersRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static SetGroupRolesRequest createSetGroupRolesRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.SetGroupRolesRequest");
        return (SetGroupRolesRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static SetGroupUsersRequest createSetGroupUsersRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.SetGroupUsersRequest");
        return (SetGroupUsersRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static SetMatterUsersRequest createSetMatterUsersRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.SetMatterUsersRequest");
        return (SetMatterUsersRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static SetRoleCapabilitiesRequest createSetRoleCapabilitiesRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.SetRoleCapabilitiesRequest");
        return (SetRoleCapabilitiesRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static SetUserDepartmentsRequest createSetUserDepartmentsRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.SetUserDepartmentsRequest");
        return (SetUserDepartmentsRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static SetUserImageRequest createSetUserImageRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.SetUserImageRequest");
        return (SetUserImageRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static SetUserPracticeAreasRequest createSetUserPracticeAreasRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.SetUserPracticeAreasRequest");
        return (SetUserPracticeAreasRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static SetUserRolesRequest createSetUserRolesRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.SetUserRolesRequest");
        return (SetUserRolesRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UpdateClientAddressRequest createUpdateClientAddressRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.UpdateClientAddressRequest");
        return (UpdateClientAddressRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UpdateClientAliasRequest createUpdateClientAliasRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.UpdateClientAliasRequest");
        return (UpdateClientAliasRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UpdateClientRequest createUpdateClientRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.UpdateClientRequest");
        return (UpdateClientRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UpdateClientFinancialDataRequest createUpdateClientFinancialDataRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.UpdateClientFinancialDataRequest");
        return (UpdateClientFinancialDataRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UpdateClientsRequest createUpdateClientsRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.UpdateClientsRequest");
        return (UpdateClientsRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UpdateClientWarningRequest createUpdateClientWarningRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.UpdateClientWarningRequest");
        return (UpdateClientWarningRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UpdateGroupRequest createUpdateGroupRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.UpdateGroupRequest");
        return (UpdateGroupRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UpdateMatterAddressRequest createUpdateMatterAddressRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.UpdateMatterAddressRequest");
        return (UpdateMatterAddressRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UpdateMatterRequest createUpdateMatterRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.UpdateMatterRequest");
        return (UpdateMatterRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UpdateMatterFinancialDataRequest createUpdateMatterFinancialDataRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.UpdateMatterFinancialDataRequest");
        return (UpdateMatterFinancialDataRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UpdateMattersRequest createUpdateMattersRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.UpdateMattersRequest");
        return (UpdateMattersRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UpdateMatterWarningRequest createUpdateMatterWarningRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.UpdateMatterWarningRequest");
        return (UpdateMatterWarningRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UpdatePartyAliasRequest createUpdatePartyAliasRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.UpdatePartyAliasRequest");
        return (UpdatePartyAliasRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UpdatePartyAddressRequest createUpdatePartyAddressRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.UpdatePartyAddressRequest");
        return (UpdatePartyAddressRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UpdatePartyRequest createUpdatePartyRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.UpdatePartyRequest");
        return (UpdatePartyRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UpdatePartyWarningRequest createUpdatePartyWarningRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.UpdatePartyWarningRequest");
        return (UpdatePartyWarningRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UpdateRoleRequest createUpdateRoleRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.UpdateRoleRequest");
        return (UpdateRoleRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UpdateUserRequest createUpdateUserRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.UpdateUserRequest");
        return (UpdateUserRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UploadTermDocumentRequest createUploadTermDocumentRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.UploadTermDocumentRequest");
        return (UploadTermDocumentRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UploadTermDocumentsRequest createUploadTermDocumentsRequest() {
        Object obj = newInstanceOf("open.config.requestfactory.UploadTermDocumentsRequest");
        return (UploadTermDocumentsRequest) obj;
    }
}
