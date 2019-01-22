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
public class OpenResponseFactory extends BaseOpenFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * create response object.
     *
     * @return response object
     */
    public static AddClientUsersResponse createAddClientUsersResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.AddClientUsersResponse");
        return (AddClientUsersResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static AddGroupUsersResponse createAddGroupUsersResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.AddGroupUsersResponse");
        return (AddGroupUsersResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static AddGroupRolesResponse createAddGroupRolesResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.AddGroupRolesResponse");
        return (AddGroupRolesResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static AddMatterUsersResponse createAddMatterUsersResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.AddMatterUsersResponse");
        return (AddMatterUsersResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static AddRoleCapabilitiesResponse createAddRoleCapabilitiesResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.AddRoleCapabilitiesResponse");
        return (AddRoleCapabilitiesResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static AddUserRolesResponse createAddUserRolesResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.AddUserRolesResponse");
        return (AddUserRolesResponse) obj;
    }
    
    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateAuthorizationCodeFromApplicationResponse createCreateAuthorizationCodeFromApplicationResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreateAuthorizationCodeFromApplicationResponse");
        return (CreateAuthorizationCodeFromApplicationResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static ChangePartyTypeResponse createChangePartyTypeResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.ChangePartyTypeResponse");
        return (ChangePartyTypeResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static ChangePasswordResponse createChangePasswordResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.ChangePasswordResponse");
        return (ChangePasswordResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateClientAddressResponse createCreateClientAddressResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreateClientAddressResponse");
        return (CreateClientAddressResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateClientAliasResponse createCreateClientAliasResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreateClientAliasResponse");
        return (CreateClientAliasResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateClientAttachmentResponse createCreateClientAttachmentResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreateClientAttachmentResponse");
        return (CreateClientAttachmentResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateClientResponse createCreateClientResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreateClientResponse");
        return (CreateClientResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateClientNoteResponse createCreateClientNoteResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreateClientNoteResponse");
        return (CreateClientNoteResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateClientsResponse createCreateClientsResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreateClientsResponse");
        return (CreateClientsResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateClientWarningResponse createCreateEntityWarningResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreateEntityWarningResponse");
        return (CreateClientWarningResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateGroupResponse createCreateGroupResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreateGroupResponse");
        return (CreateGroupResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateMatterResponse createCreateMatterResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreateMatterResponse");
        return (CreateMatterResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateMatterAddressResponse createCreateMatterAddressResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreateMatterAddressResponse");
        return (CreateMatterAddressResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateMatterAttachmentResponse createCreateMatterAttachmentResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreateMatterAttachmentResponse");
        return (CreateMatterAttachmentResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateMattersResponse createCreateMattersResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreateMattersResponse");
        return (CreateMattersResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateMatterNoteResponse createCreateMatterNoteResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreateMatterNoteResponse");
        return (CreateMatterNoteResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateMatterWarningResponse createCreateMatterWarningResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreateMatterWarningResponse");
        return (CreateMatterWarningResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateOrReplaceGroupClientSecurityResponse createCreateOrReplaceGroupEntitySecurityResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreateOrReplaceGroupEntitySecurityResponse");
        return (CreateOrReplaceGroupClientSecurityResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateOrReplaceUserClientSecurityResponse createCreateOrReplaceUserEntitySecurityResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreateOrReplaceUserEntitySecurityResponse");
        return (CreateOrReplaceUserClientSecurityResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreatePartiesResponse createCreatePartiesResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreatePartiesResponse");
        return (CreatePartiesResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreatePartyAddressResponse createCreatePartyAddressResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreatePartyAddressResponse");
        return (CreatePartyAddressResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreatePartyAliasResponse createCreatePartyAliasResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreatePartyAliasResponse");
        return (CreatePartyAliasResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreatePartyAttachmentResponse createCreatePartyAttachmentResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreatePartyAttachmentResponse");
        return (CreatePartyAttachmentResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreatePartyResponse createCreatePartyResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreatePartyResponse");
        return (CreatePartyResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreatePartyNoteResponse createCreatePartyNoteResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreatePartyNoteResponse");
        return (CreatePartyNoteResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreatePartyWarningResponse createCreatePartyWarningResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreatePartyWarningResponse");
        return (CreatePartyWarningResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateRoleResponse createCreateRoleResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreateRoleResponse");
        return (CreateRoleResponse) obj;
    }
    
    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateTokensFromAuthorizationCodeResponse createCreateTokensFromAuthorizationCodeResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreateTokensFromAuthorizationCodeResponse");
        return (CreateTokensFromAuthorizationCodeResponse) obj;
    }
    
    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateTokensFromRefreshTokenResponse createCreateTokensFromRefreshTokenResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreateTokensFromRefreshTokenResponse");
        return (CreateTokensFromRefreshTokenResponse) obj;
    }
    
    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateTokensFromUsernamePasswordResponse createCreateTokensFromUsernamePasswordResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreateTokensFromUsernamePasswordResponse");
        return (CreateTokensFromUsernamePasswordResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateUserResponse createCreateUserResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.CreateUserResponse");
        return (CreateUserResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static DeactivateUserResponse createDeactivateUserResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.DeactivateUserResponse");
        return (DeactivateUserResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static DeleteClientAddressResponse createDeleteAddressResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.DeleteAddressResponse");
        return (DeleteClientAddressResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static DeleteClientAliasResponse createDeleteAliasResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.DeleteAliasResponse");
        return (DeleteClientAliasResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static DeleteEntitySecurityResponse createDeleteEntitySecurityResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.DeleteEntitySecurityResponse");
        return (DeleteEntitySecurityResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static DeleteClientWarningResponse createDeleteEntityWarningResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.DeleteEntityWarningResponse");
        return (DeleteClientWarningResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static DeleteUserEntitySecurityResponse createDeleteUserEntitySecurityResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.DeleteUserEntitySecurityResponse");
        return (DeleteUserEntitySecurityResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static DisableUserLoginResponse createDisableUserLoginResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.DisableUserLoginResponse");
        return (DisableUserLoginResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static EnableUserLoginResponse createEnableUserLoginResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.EnableUserLoginResponse");
        return (EnableUserLoginResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetClientResponse createGetClientResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.GetClientResponse");
        return (GetClientResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetDocumentsResponse createGetDocumentsResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.GetDocumentsResponse");
        return (GetDocumentsResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetEntitySecurityResponse createGetEntitySecurityResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.GetEntitySecurityResponse");
        return (GetEntitySecurityResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetGroupResponse createGetGroupResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.GetGroupResponse");
        return (GetGroupResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetMatterResponse createGetMatterResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.GetMatterResponse");
        return (GetMatterResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetPartyResponse createGetPartyResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.GetPartyResponse");
        return (GetPartyResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetRoleResponse createGetRoleResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.GetRoleResponse");
        return (GetRoleResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetUserResponse createGetUserResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.GetUserResponse");
        return (GetUserResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetTermResponse createGetTermResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.GetTermResponse");
        return (GetTermResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetTermsForEntityResponse createGetTermsForEntityResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.GetTermsForEntityResponse");
        return (GetTermsForEntityResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetTermsResponse createGetTermsResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.GetTermsResponse");
        return (GetTermsResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetTermCategoriesResponse createGetTermCategoriesResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.GetTermCategoriesResponse");
        return (GetTermCategoriesResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetTermDocumentTypesResponse createGetTermDocumentTypesResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.GetTermDocumentTypesResponse");
        return (GetTermDocumentTypesResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static RemoveClientUsersResponse createRemoveClientUsersResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.RemoveClientUsersResponse");
        return (RemoveClientUsersResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static RemoveGroupRolesResponse createRemoveGroupRolesResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.RemoveGroupRolesResponse");
        return (RemoveGroupRolesResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static RemoveGroupUsersResponse createRemoveGroupUsersResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.RemoveGroupUsersResponse");
        return (RemoveGroupUsersResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static RemoveMatterUsersResponse createRemoveMatterUsersResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.RemoveMatterUsersResponse");
        return (RemoveMatterUsersResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static RemoveRoleCapabilitiesResponse createRemoveRoleCapabilitiesResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.RemoveRoleCapabilitiesResponse");
        return (RemoveRoleCapabilitiesResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static RemoveUserRolesResponse createRemoveUserRolesResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.RemoveUserRolesResponse");
        return (RemoveUserRolesResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static SetClientUsersResponse createSetClientUsersResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.SetClientUsersResponse");
        return (SetClientUsersResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static SetGroupRolesResponse createSetGroupRolesResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.SetGroupRolesResponse");
        return (SetGroupRolesResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static SetGroupUsersResponse createSetGroupUsersResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.SetGroupUsersResponse");
        return (SetGroupUsersResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static SetMatterUsersResponse createSetMatterUsersResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.SetMatterUsersResponse");
        return (SetMatterUsersResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static SetRoleCapabilitiesResponse createSetRoleCapabilitiesResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.SetRoleCapabilitiesResponse");
        return (SetRoleCapabilitiesResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static SetUserDepartmentsResponse createSetUserDepartmentsResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.SetUserDepartmentsResponse");
        return (SetUserDepartmentsResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static SetUserImageResponse createSetUserImageResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.SetUserImageResponse");
        return (SetUserImageResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static SetUserPracticeAreasResponse createSetUserPracticeAreasResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.SetUserPracticeAreasResponse");
        return (SetUserPracticeAreasResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static SetUserRolesResponse createSetUserRolesResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.SetUserRolesResponse");
        return (SetUserRolesResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdateClientAddressResponse createUpdateClientAddressResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.UpdateClientAddressResponse");
        return (UpdateClientAddressResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdateClientAliasResponse createUpdateClientAliasResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.UpdateClientAliasResponse");
        return (UpdateClientAliasResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdateClientResponse createUpdateClientResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.UpdateClientResponse");
        return (UpdateClientResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdateClientFinancialDataResponse createUpdateClientFinancialDataResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.UpdateClientFinancialDataResponse");
        return (UpdateClientFinancialDataResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdateClientsResponse createUpdateClientsResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.UpdateClientsResponse");
        return (UpdateClientsResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdateClientWarningResponse createUpdateClientWarningResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.UpdateClientWarningResponse");
        return (UpdateClientWarningResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdateGroupResponse createUpdateGroupResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.UpdateGroupResponse");
        return (UpdateGroupResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdateMatterAddressResponse createUpdateMatterAddressResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.UpdateMatterAddressResponse");
        return (UpdateMatterAddressResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdateMatterResponse createUpdateMatterResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.UpdateMatterResponse");
        return (UpdateMatterResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdateMatterFinancialDataResponse createUpdateMatterFinancialDataResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.UpdateMatterFinancialDataResponse");
        return (UpdateMatterFinancialDataResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdateMattersResponse createUpdateMattersResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.UpdateMattersResponse");
        return (UpdateMattersResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdateMatterWarningResponse createUpdateMatterWarningResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.UpdateMatterWarningResponse");
        return (UpdateMatterWarningResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdatePartyResponse createUpdatePartyResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.UpdatePartyResponse");
        return (UpdatePartyResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdatePartyAddressResponse createUpdatePartyAddressResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.UpdatePartyAddressResponse");
        return (UpdatePartyAddressResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdatePartyAliasResponse createUpdatePartyAliasResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.UpdatePartyAliasResponse");
        return (UpdatePartyAliasResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdatePartyWarningResponse createUpdatePartyWarningResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.UpdatePartyWarningResponse");
        return (UpdatePartyWarningResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdateRoleResponse createUpdateRoleResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.UpdateRoleResponse");
        return (UpdateRoleResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdateUserResponse createUpdateUserResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.UpdateUserResponse");
        return (UpdateUserResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UploadTermDocumentResponse createUploadTermDocumentResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.UploadTermDocumentResponse");
        return (UploadTermDocumentResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UploadTermDocumentsResponse createUploadTermDocumentsResponse() {
        Object obj = newInstanceOf("open.config.responsefactory.UploadTermDocumentsResponse");
        return (UploadTermDocumentsResponse) obj;
    }
}
