/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box;

import com.xcase.box.objects.BoxException;
import com.xcase.box.transputs.AddToMyBoxRequest;
import com.xcase.box.transputs.AddToMyBoxResponse;
import com.xcase.box.transputs.AddToTagRequest;
import com.xcase.box.transputs.AddToTagResponse;
import com.xcase.box.transputs.CreateAuthorizationCodeRequest;
import com.xcase.box.transputs.CreateAuthorizationCodeResponse;
import com.xcase.box.transputs.CreateCollaborationRequest;
import com.xcase.box.transputs.CreateCollaborationResponse;
import com.xcase.box.transputs.CreateFileRequest;
import com.xcase.box.transputs.CreateFileResponse;
import com.xcase.box.transputs.CreateFolderRequest;
import com.xcase.box.transputs.CreateFolderResponse;
import com.xcase.box.transputs.CreateGroupRequest;
import com.xcase.box.transputs.CreateGroupResponse;
import com.xcase.box.transputs.CreateMembershipRequest;
import com.xcase.box.transputs.CreateMembershipResponse;
import com.xcase.box.transputs.CreateUserRequest;
import com.xcase.box.transputs.CreateUserResponse;
import com.xcase.box.transputs.DeleteCollaborationRequest;
import com.xcase.box.transputs.DeleteCollaborationResponse;
import com.xcase.box.transputs.DeleteGroupRequest;
import com.xcase.box.transputs.DeleteGroupResponse;
import com.xcase.box.transputs.DeleteMembershipRequest;
import com.xcase.box.transputs.DeleteMembershipResponse;
import com.xcase.box.transputs.DeleteRequest;
import com.xcase.box.transputs.DeleteResponse;
import com.xcase.box.transputs.DeleteUserRequest;
import com.xcase.box.transputs.DeleteUserResponse;
import com.xcase.box.transputs.DownloadRequest;
import com.xcase.box.transputs.DownloadResponse;
import com.xcase.box.transputs.ExportTagsRequest;
import com.xcase.box.transputs.ExportTagsResponse;
import com.xcase.box.transputs.GetAccessTokenRequest;
import com.xcase.box.transputs.GetAccessTokenResponse;
import com.xcase.box.transputs.GetAccountTreeRequest;
import com.xcase.box.transputs.GetAccountTreeResponse;
import com.xcase.box.transputs.GetAuthTokenRequest;
import com.xcase.box.transputs.GetAuthTokenResponse;
import com.xcase.box.transputs.GetAuthorizationRequest;
import com.xcase.box.transputs.GetAuthorizationResponse;
import com.xcase.box.transputs.GetCollaborationRequest;
import com.xcase.box.transputs.GetCollaborationResponse;
import com.xcase.box.transputs.GetCollaborationsRequest;
import com.xcase.box.transputs.GetCollaborationsResponse;
import com.xcase.box.transputs.GetFileIdRequest;
import com.xcase.box.transputs.GetFileIdResponse;
import com.xcase.box.transputs.GetFileInfoRequest;
import com.xcase.box.transputs.GetFileInfoResponse;
import com.xcase.box.transputs.GetFolderIdRequest;
import com.xcase.box.transputs.GetFolderIdResponse;
import com.xcase.box.transputs.GetFolderInfoRequest;
import com.xcase.box.transputs.GetFolderInfoResponse;
import com.xcase.box.transputs.GetFolderItemsRequest;
import com.xcase.box.transputs.GetFolderItemsResponse;
import com.xcase.box.transputs.GetFriendsRequest;
import com.xcase.box.transputs.GetFriendsResponse;
import com.xcase.box.transputs.GetGroupRequest;
import com.xcase.box.transputs.GetGroupResponse;
import com.xcase.box.transputs.GetGroupsForUserRequest;
import com.xcase.box.transputs.GetGroupsForUserResponse;
import com.xcase.box.transputs.GetMembershipRequest;
import com.xcase.box.transputs.GetMembershipResponse;
import com.xcase.box.transputs.GetMembershipsForGroupRequest;
import com.xcase.box.transputs.GetMembershipsForGroupResponse;
import com.xcase.box.transputs.GetMembershipsForUserRequest;
import com.xcase.box.transputs.GetMembershipsForUserResponse;
import com.xcase.box.transputs.GetPendingCollaborationsRequest;
import com.xcase.box.transputs.GetPendingCollaborationsResponse;
import com.xcase.box.transputs.GetTicketRequest;
import com.xcase.box.transputs.GetTicketResponse;
import com.xcase.box.transputs.GetUserInfoRequest;
import com.xcase.box.transputs.GetUserInfoResponse;
import com.xcase.box.transputs.GetUsersRequest;
import com.xcase.box.transputs.GetUsersResponse;
import com.xcase.box.transputs.LogoutRequest;
import com.xcase.box.transputs.LogoutResponse;
import com.xcase.box.transputs.MoveRequest;
import com.xcase.box.transputs.MoveResponse;
import com.xcase.box.transputs.PrivateShareRequest;
import com.xcase.box.transputs.PrivateShareResponse;
import com.xcase.box.transputs.PublicShareRequest;
import com.xcase.box.transputs.PublicShareResponse;
import com.xcase.box.transputs.PublicUnshareRequest;
import com.xcase.box.transputs.PublicUnshareResponse;
import com.xcase.box.transputs.RefreshAccessTokenRequest;
import com.xcase.box.transputs.RefreshAccessTokenResponse;
import com.xcase.box.transputs.RegisterNewUserRequest;
import com.xcase.box.transputs.RegisterNewUserResponse;
import com.xcase.box.transputs.RenameRequest;
import com.xcase.box.transputs.RenameResponse;
import com.xcase.box.transputs.RequestFriendsRequest;
import com.xcase.box.transputs.RequestFriendsResponse;
import com.xcase.box.transputs.SearchRequest;
import com.xcase.box.transputs.SearchResponse;
import com.xcase.box.transputs.SetDescriptionRequest;
import com.xcase.box.transputs.SetDescriptionResponse;
import com.xcase.box.transputs.UpdateCollaborationRequest;
import com.xcase.box.transputs.UpdateCollaborationResponse;
import com.xcase.box.transputs.UpdateFileInfoRequest;
import com.xcase.box.transputs.UpdateFileInfoResponse;
import com.xcase.box.transputs.UpdateFolderInfoRequest;
import com.xcase.box.transputs.UpdateFolderInfoResponse;
import com.xcase.box.transputs.UpdateGroupRequest;
import com.xcase.box.transputs.UpdateGroupResponse;
import com.xcase.box.transputs.UpdateMembershipRequest;
import com.xcase.box.transputs.UpdateMembershipResponse;
import com.xcase.box.transputs.UpdateUserRequest;
import com.xcase.box.transputs.UpdateUserResponse;
import com.xcase.box.transputs.UploadRequest;
import com.xcase.box.transputs.UploadResponse;
import com.xcase.box.transputs.VerifyRegistrationEmailRequest;
import com.xcase.box.transputs.VerifyRegistrationEmailResponse;
import java.io.IOException;

/**
 * @author martin
 *
 */
public interface BoxExternalAPI {

    /**
     * This method copies a file publicly shared by someone to a user's mybox.
     * 'file_id' and 'public_name' params identify a publicly shared file, you
     * should provide either file_id or public name (like '31nhke0ahp') as a
     * parameter. 'folder_id' is the id of a user's folder, where files are to
     * be copied.
     *
     * On a successful result, the status will be 'addtomybox_ok'. If the result
     * wasn't successful, the status field can be: 'addtomybox_error',
     * 'not_logged_id', 'application_restricted', 's_link_exists'.
     *
     * @param addToMyBoxRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    AddToMyBoxResponse addToMyBox(AddToMyBoxRequest addToMyBoxRequest) throws IOException, BoxException;

    /**
     * This method adds file or folder to tags list. 'target' param can be
     * either 'file' or 'folder' depending on what do you want to add,
     * 'target_id' is the id of a file or folder to be added, 'tags' array of
     * tags where target will be added.
     *
     * On successful a result, you will receive 'addtotag_ok'. If the result
     * wasn't successful, status field can be: addtotag_error.
     *
     * @param addToTagRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    AddToTagResponse addToTag(AddToTagRequest addToTagRequest) throws IOException, BoxException;

    /**
     * This method creates a new authorization code.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be:
     * 'e_no_parent_folder', 'not_logged_in', 'application_r'stricted'.
     *
     * @param createAuthorizationCodeRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    CreateAuthorizationCodeResponse createAuthorizationCode(CreateAuthorizationCodeRequest createAuthorizationCodeRequest) throws IOException, BoxException;

    /**
     * This method creates a new collaboration.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be:
     * 'e_no_parent_folder', 'not_logged_in', 'application_r'stricted'.
     *
     * @param createCollaborationRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    CreateCollaborationResponse createCollaboration(CreateCollaborationRequest createCollaborationRequest) throws IOException, BoxException;

    /**
     * This method creates a new file.
     *
     * 'parent_id' param is the id of a folder in which a new file will be
     * created, 'name' param is the name of a new folder. Set 'share' to 1 if
     * you want to share a folder publicly.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be:
     * 'e_no_parent_folder', 'not_logged_in', 'application_r'stricted'.
     *
     * @param createFileRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    CreateFileResponse createFile(CreateFileRequest createFileRequest) throws IOException, BoxException;

    /**
     * This method creates a new folder.
     *
     * 'parent_id' param is the id of a folder in which a new folder will be
     * created, 'name' param is the name of a new folder. Set 'share' to 1 if
     * you want to share a folder publicly.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be:
     * 'e_no_parent_folder', 'not_logged_in', 'application_r'stricted'.
     *
     * @param createFolderRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    CreateFolderResponse createFolder(CreateFolderRequest createFolderRequest) throws IOException, BoxException;

    /**
     * This method creates a new group.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be: 'not_logged_in',
     * 'application_r'stricted'.
     *
     * @param createGroupRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    CreateGroupResponse createGroup(CreateGroupRequest createGroupRequest) throws IOException, BoxException;

    /**
     * This method creates a new group membership.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be: 'not_logged_in',
     * 'application_r'stricted'.
     *
     * @param createMembershipRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    CreateMembershipResponse createMembership(CreateMembershipRequest createMembershipRequest) throws IOException, BoxException;

    /**
     * This method creates a new user.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be: 'not_logged_in',
     * 'application_r'stricted'.
     *
     * @param createUserRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    CreateUserResponse createUser(CreateUserRequest createUserRequest) throws IOException, BoxException;

    /**
     * This method deletes a collaboration.
     *
     * @param deleteCollaborationRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    DeleteCollaborationResponse deleteCollaboration(DeleteCollaborationRequest deleteCollaborationRequest) throws IOException, BoxException;

    /**
     * This method deletes a group.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be: 'not_logged_in',
     * 'application_r'stricted'.
     *
     * @param deleteGroupRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    DeleteGroupResponse deleteGroup(DeleteGroupRequest deleteGroupRequest) throws IOException, BoxException;

    /**
     * This method deletes a group membership.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be: 'not_logged_in',
     * 'application_r'stricted'.
     *
     * @param deleteMembershipRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    DeleteMembershipResponse deleteMembership(DeleteMembershipRequest deleteMembershipRequest) throws IOException, BoxException;

    /**
     * This method deletes a file or folder.
     *
     * 'target' param can be either 'file' or 'folder' depending on what you
     * want to delete, 'target_id' is id of a file or folder to be deleted.
     *
     * On a successful result, the status will be 's_delete_node'. If the result
     * wasn't successful, status field can be: 'e_delete_node', 'not_logged_in',
     * 'application_restricted'.
     *
     * @param deleteRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    DeleteResponse delete(DeleteRequest deleteRequest) throws IOException, BoxException;

    /**
     * This method deletes a user.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be: 'not_logged_in',
     * 'application_r'stricted'.
     *
     * @param deleteUserRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    DeleteUserResponse deleteUser(DeleteUserRequest deleteUserRequest) throws IOException, BoxException;

    /**
     * download a file.
     *
     * @param downloadRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    DownloadResponse download(DownloadRequest downloadRequest) throws IOException, BoxException;

    /**
     * This method returns all the user's tags.
     *
     * On successful a result, you will receive 'export_tags_ok' and tag_xml
     * will be base64 encoded tags xml. After decoding tag_xml you will get xml
     * like this:
     *
     * {@code
     * <?xml version="1.0"?> <tags> <tag id="37"> music </tag> <tag id="38"> mp3
     * </tag> </tags>
     * }
     * 
     * If the result wasn't successful, status field can be:
     * not_logged_id, application_restricted.
     *
     * @param exportTagsRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    ExportTagsResponse exportTags(ExportTagsRequest exportTagsRequest) throws IOException, BoxException;

    /**
     * This method is used to get a user's files and folders tree.
     *
     * 'folder_id' param defines root folder from which the tree begins.
     * 'params' is array of string where you can set additional parameters,
     * which are: onelevel - make a tree of one level depth, so you will get
     * only files and folders stored in folder which folder_id you have
     * provided. nofiles - include folders only in result tree, no files. nozip
     * - do not zip tree xml.
     *
     * On successful result you will receive 'listing_ok' as status and base64
     * encoded zipped tree xml. So you have to decode the received tree, then
     * unzip it (if you haven't set 'nozip' param) and you will get xml like
     * this: (note that updatedand createdare UNIX timestamps in PST).
     *
     * @param getAccountTreeRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    GetAccountTreeResponse getAccountTree(GetAccountTreeRequest getAccountTreeRequest) throws IOException, BoxException;

    /**
     * This method is used in the authorization process. You should call this
     * method after the user has authorized themselves on box.net site. Pass
     * ticket that you get when called get_ticket method. On a successful
     * result, method will return 'get_auth_token_ok' as status, auth_token to
     * use in other method calls, and user struct which describes logged user.
     * Request.
     *
     * @param getAuthTokenRequest request object
     * @return getAuthTokenResponse response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    GetAuthTokenResponse getAuthToken(GetAuthTokenRequest getAuthTokenRequest) throws IOException, BoxException;

    GetAuthorizationResponse getAuthorization(GetAuthorizationRequest getAuthorizationRequest) throws IOException, BoxException;

    GetAccessTokenResponse getAccessToken(GetAccessTokenRequest getAccessTokenRequest) throws IOException, BoxException;

    /**
     * This method gets a collaboration.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be:
     * 'e_no_parent_folder', 'not_logged_in', 'application_r'stricted'.
     *
     * @param getCollaborationRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    GetCollaborationResponse getCollaboration(GetCollaborationRequest getCollaborationRequest) throws IOException, BoxException;

    /**
     * This method gets a collaboration.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be:
     * 'e_no_parent_folder', 'not_logged_in', 'application_r'stricted'.
     *
     * @param getCollaborationsRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    GetCollaborationsResponse getCollaborations(GetCollaborationsRequest getCollaborationsRequest) throws IOException, BoxException;

    GetFileIdResponse getFileId(GetFileIdRequest getFileIdRequest) throws IOException, BoxException;

    /**
     * This method gets file info. 'file_id' param should contain valid if of
     * user file.
     *
     * On successful a result, you will receive status 's_get_file_info' and
     * file info in 'info'. If the result wasn't successful, status field can
     * be: e_access_denied.
     *
     * @param getFileInfoRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    GetFileInfoResponse getFileInfo(GetFileInfoRequest getFileInfoRequest) throws IOException, BoxException;

    GetFolderIdResponse getFolderId(GetFolderIdRequest getFolderIdRequest) throws IOException, BoxException;

    GetFolderInfoResponse getFolderInfo(GetFolderInfoRequest getFolderInfoRequest) throws IOException, BoxException;

    GetFolderItemsResponse getFolderItems(GetFolderItemsRequest getFolderItemsRequest) throws IOException, BoxException;

    /**
     * This method gets a group.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be: 'not_logged_in',
     * 'application_r'stricted'.
     *
     * @param getGroupRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    GetGroupResponse getGroup(GetGroupRequest getGroupRequest) throws IOException, BoxException;

    /**
     * This method gets groups for a user.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be: 'not_logged_in',
     * 'application_r'stricted'.
     *
     * @param getGroupsForUserRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    GetGroupsForUserResponse getGroupsForUser(GetGroupsForUserRequest getGroupsForUserRequest) throws IOException, BoxException;

    /**
     * This method gets a membership.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be: 'not_logged_in',
     * 'application_r'stricted'.
     *
     * @param getMembershipRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    GetMembershipResponse getMembership(GetMembershipRequest getMembershipRequest) throws IOException, BoxException;

    /**
     * This method gets memberships for a group.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be: 'not_logged_in',
     * 'application_r'stricted'.
     *
     * @param getMembershipsForGroupRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    GetMembershipsForGroupResponse getMembershipsForGroup(GetMembershipsForGroupRequest getMembershipsForGroupRequest) throws IOException, BoxException;

    /**
     * This method gets memberships for a user.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be: 'not_logged_in',
     * 'application_r'stricted'.
     *
     * @param getMembershipsForUserRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    GetMembershipsForUserResponse getMembershipsForUser(GetMembershipsForUserRequest getMembershipsForUserRequest) throws IOException, BoxException;

    /**
     * This method gets pending collaborations.
     *
     * 'parent_id' param is the id of a folder in which a new file will be
     * created, 'name' param is the name of a new folder. Set 'share' to 1 if
     * you want to share a folder publicly.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be:
     * 'e_no_parent_folder', 'not_logged_in', 'application_r'stricted'.
     *
     * @param getPendingCollaborationsRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    GetPendingCollaborationsResponse getPendingCollaborations(GetPendingCollaborationsRequest getPendingCollaborationsRequest) throws IOException, BoxException;

    /**
     * This method is used in the authorization process.
     *
     * @param getTicketRequest request object
     * @return getTicketResponse response object
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    GetTicketResponse getTicket(GetTicketRequest getTicketRequest) throws IOException, BoxException;

    /**
     * This method gets user info. 'file_id' param should contain valid if of
     * user file.
     *
     * On successful a result, you will receive status 's_get_file_info' and
     * file info in 'info'. If the result wasn't successful, status field can
     * be: e_access_denied.
     *
     * @param getUserInfoRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    GetUserInfoResponse getUserInfo(GetUserInfoRequest getUserInfoRequest) throws IOException, BoxException;

    /**
     * This method is used in the authorization process.
     *
     * @param getUsersRequest request object
     * @return getTicketResponse response object
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    GetUsersResponse getUsers(GetUsersRequest getUsersRequest) throws IOException, BoxException;

    /**
     * This method is used to logout a user. On successful logout method will
     * return 'logout_ok' as status. If logout wasn't successful, then status
     * filed can be: 'invalid_auth_token' when auth_token is invalid.
     *
     * @param logoutRequest request object
     * @return LogoutResponse object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    LogoutResponse logout(LogoutRequest logoutRequest) throws IOException, BoxException;

    /**
     * This method moves a file or folder to another folder.
     *
     * 'target' param can be either 'file' or 'folder' depending on what do you
     * want to move, 'target_id' is the id of a file or folder to be moved,
     * 'destination_id' is the destination folder id.
     *
     * On a successful result, status will be 's_move_node'. If the result
     * wasn't successful, status field can be: 'e_move_node', 'not_logged_in',
     * 'application_restricted'.
     *
     * @param moveRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    MoveResponse move(MoveRequest moveRequest) throws IOException, BoxException;

    RefreshAccessTokenResponse refreshAccessToken(RefreshAccessTokenRequest refreshAccessTokenRequest) throws IOException, BoxException;

    /**
     * This method is used to register a user. Upon a successful registration,
     * the status param will be 'successful_register'. If registration wasn't
     * successful, status field can be: 'e_register', 'email_invalid',
     * 'email_already_registered', 'application_restricted'.
     *
     * @param registerNewUserRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    RegisterNewUserResponse registerNewUser(RegisterNewUserRequest registerNewUserRequest) throws IOException, BoxException;

    SearchResponse search(SearchRequest searchRequest) throws IOException, BoxException;

    /**
     * This method is used to verify user registration email . Upon a not used
     * and right registration email, the status param will be 'email_ok'. Else
     * status field can be: 'email_invalid', 'email_already_registered',
     * 'application_restricted'.
     *
     * @param verifyRegistrationEmailRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    VerifyRegistrationEmailResponse verifyRegistrationEmail(VerifyRegistrationEmailRequest verifyRegistrationEmailRequest) throws IOException, BoxException;

    /**
     * This method renames a file or folder.
     *
     * 'target' param can be either 'file' or 'folder' depending on what you
     * want to rename, 'target_id' is the id of a file or folder to be renamed,
     * 'new_name' is the new name for a file or folder.
     *
     * On a successful result, status will be 's_rename_node'. If result wasn't
     * successful, stat's field can be: 'e_rename_node', 'not_logged_in',
     * 'application_restricted'.
     *
     * @param renameRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    RenameResponse rename(RenameRequest renameRequest) throws IOException, BoxException;

    /**
     * This method publicly shares a file or folder. 'target' param should be
     * either 'file' or 'folder', 'target_id' is id of the file or folder to be
     * shared. 'password' param is to protect sharing with a password, 'emails'
     * params is array of emails to notify about a new share, 'message' param is
     * some message to be included in a notification email.
     *
     * On a successful result, the status will be 'share_ok' and 'public_name'
     * param will be a unique identifier of a publicly shared file or folder. If
     * the result wasn't successful, the status field can be: 'share_error',
     * 'wrong_node', 'not_logged_in', 'application_restricted'.
     *
     * @param publicShareRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    PublicShareResponse publicShare(PublicShareRequest publicShareRequest) throws IOException, BoxException;

    /**
     * This method unshares a shared file or folder. 'target' param shoud be
     * either 'file' or 'folder', 'target_id' is id of a file or folder to be
     * unshared.
     *
     * On a successful result, the status will be 'unshare_ok'. If the result
     * wasn't successful, the status field can be: 'unshare_error',
     * 'wrong_node', 'not_logged_in', 'application_restricted'.
     *
     * @param publicUnshareRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    PublicUnshareResponse publicUnshare(PublicUnshareRequest publicUnshareRequest) throws IOException, BoxException;

    /**
     * This method privately shares a file or folder with another user(s).
     * 'target' param should be either 'file' or 'folder', 'target_id' is the id
     * of the file or folder to be shared. 'emails' params is an array of emails
     * of users' to share files with. if 'notify' param is true, then a
     * notification email will be sent to users, 'message' param is a message to
     * be included in the notification email.
     *
     * Note: currently only files can be shared privately.
     *
     * On a successful result, the status will be 'private_share_ok'. If the
     * result wasn't successful, the status field can be: 'private_share_error',
     * 'wrong_node', 'not_logged_in', 'application_restricted'.
     *
     * @param privateShareRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    PrivateShareResponse privateShare(PrivateShareRequest privateShareRequest) throws IOException, BoxException;

    /**
     * This method updates a collaboration.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be: 'not_logged_in',
     * 'application_r'stricted'.
     *
     * @param updateCollaborationRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    UpdateCollaborationResponse updateCollaboration(UpdateCollaborationRequest updateCollaborationRequest) throws IOException, BoxException;

    /**
     * This method updates a group.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be: 'not_logged_in',
     * 'application_r'stricted'.
     *
     * @param updateGroupRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    UpdateGroupResponse updateGroup(UpdateGroupRequest updateGroupRequest) throws IOException, BoxException;

    /**
     * This method updates a membership.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be: 'not_logged_in',
     * 'application_r'stricted'.
     *
     * @param updateMembershipRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    UpdateMembershipResponse updateMembership(UpdateMembershipRequest updateMembershipRequest) throws IOException, BoxException;

    /**
     * This method updates a user.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be: 'not_logged_in',
     * 'application_r'stricted'.
     *
     * @param updateUserRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) throws IOException, BoxException;

    /**
     * This method sets a description to a file or folder. 'target' can be a
     * 'file' or 'folder', 'target_id' - id of file or folder, description -
     * string that should be set as description.
     *
     * On successful a result, you will receive status 's_set_description'. If
     * the result wasn't successful, status field can be: e_set_description.
     *
     * @param setDescriptionRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    SetDescriptionResponse setDescription(SetDescriptionRequest setDescriptionRequest) throws IOException, BoxException;

    /**
     * This method is used to retrieve a list of freinds.
     *
     * 'params' is an array of the string where you can set additional
     * parameters, which are: nozip - do not zip tree xml.
     *
     * On a successful result you will receive 's_get_friends' as the status and
     * base64 encoded (and zipped) friends xml. Friends xml looks like this:
     * ......
     *
     * @param getFriendsRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    GetFriendsResponse getFriends(GetFriendsRequest getFriendsRequest) throws IOException, BoxException;

    /**
     * This method requests new friends to be added to your network. 'emails' -
     * array of emails. 'message' - text message that you want to send to
     * freinds. 'params' is an array of string where you can set additional
     * parameters, which are: box_auto_subscribe - subscribe to public boxes of
     * inveted users. no_email - don't send emails to invited users.
     *
     * On a successful result, you will receive status 's_request_friends'. If
     * the result wasn't successful, status field can be: e_request_friends.
     *
     * @param requestFriendsRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    RequestFriendsResponse requestFriends(RequestFriendsRequest requestFriendsRequest) throws IOException, BoxException;

    UpdateFileInfoResponse updateFileInfo(UpdateFileInfoRequest updateFileInforequest) throws IOException, BoxException;

    UpdateFolderInfoResponse updateFolderInfo(UpdateFolderInfoRequest updateFolderInforequest) throws IOException, BoxException;

    /**
     * upload files.
     *
     * @param uploadRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    UploadResponse upload(UploadRequest uploadRequest) throws BoxException, Exception, IOException;
}
