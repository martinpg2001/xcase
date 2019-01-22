/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.factories;

import com.xcase.box.transputs.AddToMyBoxResponse;
import com.xcase.box.transputs.AddToTagResponse;
import com.xcase.box.transputs.CreateAuthorizationCodeResponse;
import com.xcase.box.transputs.CreateCollaborationResponse;
import com.xcase.box.transputs.CreateFileResponse;
import com.xcase.box.transputs.CreateFolderResponse;
import com.xcase.box.transputs.CreateGroupResponse;
import com.xcase.box.transputs.CreateMembershipResponse;
import com.xcase.box.transputs.CreateUserResponse;
import com.xcase.box.transputs.DeleteCollaborationResponse;
import com.xcase.box.transputs.DeleteGroupResponse;
import com.xcase.box.transputs.DeleteMembershipResponse;
import com.xcase.box.transputs.DeleteResponse;
import com.xcase.box.transputs.DeleteUserResponse;
import com.xcase.box.transputs.DownloadResponse;
import com.xcase.box.transputs.ExportTagsResponse;
import com.xcase.box.transputs.GetAccessTokenResponse;
import com.xcase.box.transputs.GetAccountTreeResponse;
import com.xcase.box.transputs.GetAuthTokenResponse;
import com.xcase.box.transputs.GetAuthorizationResponse;
import com.xcase.box.transputs.GetCollaborationResponse;
import com.xcase.box.transputs.GetCollaborationsForGroupResponse;
import com.xcase.box.transputs.GetCollaborationsResponse;
import com.xcase.box.transputs.GetFileIdResponse;
import com.xcase.box.transputs.GetFileInfoResponse;
import com.xcase.box.transputs.GetFolderIdResponse;
import com.xcase.box.transputs.GetFolderInfoResponse;
import com.xcase.box.transputs.GetFolderItemsResponse;
import com.xcase.box.transputs.GetFriendsResponse;
import com.xcase.box.transputs.GetGroupResponse;
import com.xcase.box.transputs.GetGroupsForUserResponse;
import com.xcase.box.transputs.GetMembershipResponse;
import com.xcase.box.transputs.GetMembershipsForGroupResponse;
import com.xcase.box.transputs.GetMembershipsForUserResponse;
import com.xcase.box.transputs.GetPendingCollaborationsResponse;
import com.xcase.box.transputs.GetTicketResponse;
import com.xcase.box.transputs.GetUserInfoResponse;
import com.xcase.box.transputs.GetUsersResponse;
import com.xcase.box.transputs.LogoutResponse;
import com.xcase.box.transputs.MoveResponse;
import com.xcase.box.transputs.PrivateShareResponse;
import com.xcase.box.transputs.PublicShareResponse;
import com.xcase.box.transputs.PublicUnshareResponse;
import com.xcase.box.transputs.RefreshAccessTokenResponse;
import com.xcase.box.transputs.RegisterNewUserResponse;
import com.xcase.box.transputs.RenameResponse;
import com.xcase.box.transputs.RequestFriendsResponse;
import com.xcase.box.transputs.SearchResponse;
import com.xcase.box.transputs.SetDescriptionResponse;
import com.xcase.box.transputs.UpdateCollaborationResponse;
import com.xcase.box.transputs.UpdateFileInfoResponse;
import com.xcase.box.transputs.UpdateFolderInfoResponse;
import com.xcase.box.transputs.UpdateGroupResponse;
import com.xcase.box.transputs.UpdateMembershipResponse;
import com.xcase.box.transputs.UpdateUserResponse;
import com.xcase.box.transputs.UploadResponse;
import com.xcase.box.transputs.VerifyRegistrationEmailResponse;
import java.lang.invoke.*;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class BoxResponseFactory extends BaseBoxFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * create response object.
     *
     * @return response object
     */
    public static AddToMyBoxResponse createAddToMyBoxResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.AddToMyBoxResponse");
        return (AddToMyBoxResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static AddToTagResponse createAddToTagResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.AddToTagResponse");
        return (AddToTagResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateAuthorizationCodeResponse createCreateAuthorizationCodeResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.CreateAuthorizationCodeResponse");
        return (CreateAuthorizationCodeResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateCollaborationResponse createCreateCollaborationResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.CreateCollaborationResponse");
        return (CreateCollaborationResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateFileResponse createCreateFileResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.CreateFileResponse");
        return (CreateFileResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateFolderResponse createCreateFolderResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.CreateFolderResponse");
        return (CreateFolderResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateGroupResponse createCreateGroupResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.CreateGroupResponse");
        return (CreateGroupResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateMembershipResponse createCreateMembershipResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.CreateMembershipResponse");
        return (CreateMembershipResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateUserResponse createCreateUserResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.CreateUserResponse");
        return (CreateUserResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static DeleteCollaborationResponse createDeleteCollaborationResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.DeleteCollaborationResponse");
        return (DeleteCollaborationResponse) obj;
    }

    /**
     * delete group response object.
     *
     * @return response object
     */
    public static DeleteGroupResponse createDeleteGroupResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.DeleteGroupResponse");
        return (DeleteGroupResponse) obj;
    }

    /**
     * delete group response object.
     *
     * @return response object
     */
    public static DeleteMembershipResponse createDeleteMembershipResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.DeleteMembershipResponse");
        return (DeleteMembershipResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static DeleteResponse createDeleteResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.DeleteResponse");
        return (DeleteResponse) obj;
    }

    /**
     * delete user response object.
     *
     * @return response object
     */
    public static DeleteUserResponse createDeleteUserResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.DeleteUserResponse");
        return (DeleteUserResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static DownloadResponse createDownloadResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.DownloadResponse");
        return (DownloadResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static ExportTagsResponse createExportTagsResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.ExportTagsResponse");
        return (ExportTagsResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetAccessTokenResponse createGetAccessTokenResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.GetAccessTokenResponse");
        return (GetAccessTokenResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetAccountTreeResponse createGetAccountTreeResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.GetAccountTreeResponse");
        return (GetAccountTreeResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetAuthorizationResponse createGetAuthorizationResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.GetAuthorizationResponse");
        return (GetAuthorizationResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetAuthTokenResponse createGetAuthTokenResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.GetAuthTokenResponse");
        return (GetAuthTokenResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetCollaborationResponse createGetCollaborationResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.GetCollaborationResponse");
        return (GetCollaborationResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetCollaborationsForGroupResponse createGetCollaborationsForGroupResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.GetCollaborationsForGroupResponse");
        return (GetCollaborationsForGroupResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetCollaborationsResponse createGetCollaborationsResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.GetCollaborationsResponse");
        return (GetCollaborationsResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetFileIdResponse createGetFileIdResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.GetFileIdResponse");
        return (GetFileIdResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetFileInfoResponse createGetFileInfoResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.GetFileInfoResponse");
        return (GetFileInfoResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetFolderInfoResponse createGetFolderInfoResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.GetFolderInfoResponse");
        return (GetFolderInfoResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetFolderIdResponse createGetFolderIdResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.GetFolderIdResponse");
        return (GetFolderIdResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetFolderItemsResponse createGetFolderItemsResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.GetFolderItemsResponse");
        return (GetFolderItemsResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetFriendsResponse createGetFriendsResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.GetFriendsResponse");
        return (GetFriendsResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetGroupResponse createGetGroupResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.GetGroupResponse");
        return (GetGroupResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetGroupsForUserResponse createGetGroupsForUserResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.GetGroupsForUserResponse");
        return (GetGroupsForUserResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetMembershipResponse createGetMembershipResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.GetMembershipResponse");
        return (GetMembershipResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetMembershipsForGroupResponse createGetMembershipsForGroupResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.GetMembershipsForGroupResponse");
        return (GetMembershipsForGroupResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetMembershipsForUserResponse createGetMembershipsForUserResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.GetMembershipsForUserResponse");
        return (GetMembershipsForUserResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetPendingCollaborationsResponse createGetPendingCollaborationsResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.GetPendingCollaborationsResponse");
        return (GetPendingCollaborationsResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetTicketResponse createGetTicketResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.GetTicketResponse");
        return (GetTicketResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetUserInfoResponse createGetUserInfoResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.GetUserInfoResponse");
        return (GetUserInfoResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetUsersResponse createGetUsersResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.GetUsersResponse");
        return (GetUsersResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static LogoutResponse createLogoutResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.LogoutResponse");
        return (LogoutResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static MoveResponse createMoveResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.MoveResponse");
        return (MoveResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static PrivateShareResponse createPrivateShareResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.PrivateShareResponse");
        return (PrivateShareResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static PublicShareResponse createPublicShareResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.PublicShareResponse");
        return (PublicShareResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static PublicUnshareResponse createPublicUnshareResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.PublicUnshareResponse");
        return (PublicUnshareResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static RefreshAccessTokenResponse createRefreshAccessTokenResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.RefreshAccessTokenResponse");
        return (RefreshAccessTokenResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static RegisterNewUserResponse createRegisterNewUserResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.RegisterNewUserResponse");
        return (RegisterNewUserResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static RenameResponse createRenameResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.RenameResponse");
        return (RenameResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static RequestFriendsResponse createRequestFriendsResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.RequestFriendsResponse");
        return (RequestFriendsResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static SearchResponse createSearchResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.SearchResponse");
        return (SearchResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static SetDescriptionResponse createSetDescriptionResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.SetDescriptionResponse");
        return (SetDescriptionResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdateCollaborationResponse createUpdateCollaborationResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.UpdateCollaborationResponse");
        return (UpdateCollaborationResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdateFileInfoResponse createUpdateFileInfoResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.UpdateFileInfoResponse");
        return (UpdateFileInfoResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdateFolderInfoResponse createUpdateFolderInfoResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.UpdateFolderInfoResponse");
        return (UpdateFolderInfoResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdateGroupResponse createUpdateGroupResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.UpdateGroupResponse");
        return (UpdateGroupResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdateMembershipResponse createUpdateMembershipResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.UpdateMembershipResponse");
        return (UpdateMembershipResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdateUserResponse createUpdateUserResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.UpdateUserResponse");
        return (UpdateUserResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UploadResponse createUploadResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.UploadResponse");
        return (UploadResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static VerifyRegistrationEmailResponse createVerifyRegistrationEmailResponse() {
        Object obj = newInstanceOf("box4j.config.responsefactory.VerifyRegistrationEmailResponse");
        return (VerifyRegistrationEmailResponse) obj;
    }
}
