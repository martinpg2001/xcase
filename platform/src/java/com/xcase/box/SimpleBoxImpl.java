/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box;

import com.xcase.box.impl.simple.methods.GetMembershipMethod;
import com.xcase.box.impl.simple.methods.UpdateMembershipMethod;
import com.xcase.box.impl.simple.methods.CreateFileMethod;
import com.xcase.box.impl.simple.methods.PrivateShareMethod;
import com.xcase.box.impl.simple.methods.GetAccountTreeMethod;
import com.xcase.box.impl.simple.methods.UpdateFileInfoMethod;
import com.xcase.box.impl.simple.methods.RefreshAccessTokenMethod;
import com.xcase.box.impl.simple.methods.CreateFolderMethod;
import com.xcase.box.impl.simple.methods.MoveMethod;
import com.xcase.box.impl.simple.methods.VerifyRegistrationEmailMethod;
import com.xcase.box.impl.simple.methods.GetFileInfoMethod;
import com.xcase.box.impl.simple.methods.UpdateGroupMethod;
import com.xcase.box.impl.simple.methods.UploadMethod;
import com.xcase.box.impl.simple.methods.GetFolderItemsMethod;
import com.xcase.box.impl.simple.methods.AddToTagMethod;
import com.xcase.box.impl.simple.methods.GetTicketMethod;
import com.xcase.box.impl.simple.methods.GetPendingCollaborationsMethod;
import com.xcase.box.impl.simple.methods.CreateUserMethod;
import com.xcase.box.impl.simple.methods.DeleteCollaborationMethod;
import com.xcase.box.impl.simple.methods.GetCollaborationMethod;
import com.xcase.box.impl.simple.methods.ExportTagsMethod;
import com.xcase.box.impl.simple.methods.UpdateFolderInfoMethod;
import com.xcase.box.impl.simple.methods.GetAuthorizationMethod;
import com.xcase.box.impl.simple.methods.GetAccessTokenMethod;
import com.xcase.box.impl.simple.methods.AddToMyBoxMethod;
import com.xcase.box.impl.simple.methods.RequestFriendsMethod;
import com.xcase.box.impl.simple.methods.UpdateCollaborationMethod;
import com.xcase.box.impl.simple.methods.GetMembershipsForUserMethod;
import com.xcase.box.impl.simple.methods.GetFolderIdMethod;
import com.xcase.box.impl.simple.methods.GetCollaborationsMethod;
import com.xcase.box.impl.simple.methods.GetGroupsForUserMethod;
import com.xcase.box.impl.simple.methods.LogoutMethod;
import com.xcase.box.impl.simple.methods.UpdateUserMethod;
import com.xcase.box.impl.simple.methods.DeleteUserMethod;
import com.xcase.box.impl.simple.methods.CreateCollaborationMethod;
import com.xcase.box.impl.simple.methods.GetUserInfoMethod;
import com.xcase.box.impl.simple.methods.DownloadMethod;
import com.xcase.box.impl.simple.methods.GetFileIdMethod;
import com.xcase.box.impl.simple.methods.GetGroupMethod;
import com.xcase.box.impl.simple.methods.PublicUnshareMethod;
import com.xcase.box.impl.simple.methods.SearchMethod;
import com.xcase.box.impl.simple.methods.GetMembershipsForGroupMethod;
import com.xcase.box.impl.simple.methods.GetFriendsMethod;
import com.xcase.box.impl.simple.methods.DeleteMembershipMethod;
import com.xcase.box.impl.simple.methods.CreateGroupMethod;
import com.xcase.box.impl.simple.methods.RegisterNewUserMethod;
import com.xcase.box.impl.simple.methods.DeleteGroupMethod;
import com.xcase.box.impl.simple.methods.CreateAuthorizationCodeMethod;
import com.xcase.box.impl.simple.methods.GetFolderInfoMethod;
import com.xcase.box.impl.simple.methods.GetAuthTokenMethod;
import com.xcase.box.impl.simple.methods.CreateMembershipMethod;
import com.xcase.box.impl.simple.methods.GetUsersMethod;
import com.xcase.box.impl.simple.methods.SetDescriptionMethod;
import com.xcase.box.impl.simple.methods.PublicShareMethod;
import com.xcase.box.impl.simple.methods.RenameMethod;
import com.xcase.box.impl.simple.methods.DeleteMethod;
import com.xcase.box.impl.simple.core.BoxConfigurationManager;
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
import java.lang.invoke.*;
import org.apache.logging.log4j.*;

/**
 * @author martin
 *
 */
public class SimpleBoxImpl implements BoxExternalAPI {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * configuration manager
     */
    public BoxConfigurationManager LocalConfigurationManager = BoxConfigurationManager.getConfigurationManager();

    /**
     * box action implementation.
     */
    private AddToMyBoxMethod addToMyBoxMethod = new AddToMyBoxMethod();

    /**
     * box action implementation.
     */
    private AddToTagMethod addToTagMethod = new AddToTagMethod();

    /**
     * box action implementation.
     */
    private CreateAuthorizationCodeMethod createAuthorizationCodeMethod = new CreateAuthorizationCodeMethod();

    /**
     * box action implementation.
     */
    private CreateCollaborationMethod createCollaborationMethod = new CreateCollaborationMethod();

    /**
     * box action implementation.
     */
    private CreateFileMethod createFileMethod = new CreateFileMethod();

    /**
     * box action implementation.
     */
    private CreateFolderMethod createFolderMethod = new CreateFolderMethod();

    /**
     * box action implementation.
     */
    private CreateGroupMethod createGroupMethod = new CreateGroupMethod();

    /**
     * box action implementation.
     */
    private CreateMembershipMethod createMembershipMethod = new CreateMembershipMethod();

    /**
     * box action implementation.
     */
    private CreateUserMethod createUserMethod = new CreateUserMethod();

    /**
     * box action implementation.
     */
    private DeleteCollaborationMethod deleteCollaborationMethod = new DeleteCollaborationMethod();

    /**
     * box action implementation.
     */
    private DeleteGroupMethod deleteGroupMethod = new DeleteGroupMethod();

    /**
     * box action implementation.
     */
    private DeleteMembershipMethod deleteMembershipMethod = new DeleteMembershipMethod();

    /**
     * box action implementation.
     */
    private DeleteMethod deleteMethod = new DeleteMethod();

    /**
     * box action implementation.
     */
    private DeleteUserMethod deleteUserMethod = new DeleteUserMethod();

    /**
     * box action implementation.
     */
    private DownloadMethod downloadMethod = new DownloadMethod();

    /**
     * box action implementation.
     */
    private ExportTagsMethod exportTagsMethod = new ExportTagsMethod();

    /**
     * box action implementation.
     */
    private GetAccountTreeMethod getAccountTreeMethod = new GetAccountTreeMethod();

    /**
     * box action implementation.
     */
    private GetAccessTokenMethod getAccessTokenMethod = new GetAccessTokenMethod();

    /**
     * box action implementation.
     */
    private GetAuthorizationMethod getAuthorizationMethod = new GetAuthorizationMethod();

    /**
     * box action implementation.
     */
    private GetAuthTokenMethod getAuthTokenMethod = new GetAuthTokenMethod();

    /**
     * box action implementation.
     */
    private GetCollaborationMethod getCollaborationMethod = new GetCollaborationMethod();

    /**
     * box action implementation.
     */
    private GetCollaborationsMethod getCollaborationsMethod = new GetCollaborationsMethod();

    /**
     * box action implementation.
     */
    private GetFileIdMethod getFileIdMethod = new GetFileIdMethod();

    /**
     * box action implementation.
     */
    private GetFileInfoMethod getFileInfoMethod = new GetFileInfoMethod();

    /**
     * box action implementation.
     */
    private GetFolderIdMethod getFolderIdMethod = new GetFolderIdMethod();

    /**
     * box action implementation.
     */
    private GetFolderInfoMethod getFolderInfoMethod = new GetFolderInfoMethod();

    /**
     * box action implementation.
     */
    private GetFolderItemsMethod getFolderItemsMethod = new GetFolderItemsMethod();

    /**
     * box action implementation.
     */
    private GetFriendsMethod getFriendsMethod = new GetFriendsMethod();

    /**
     * box action implementation.
     */
    private GetGroupMethod getGroupMethod = new GetGroupMethod();

    /**
     * box action implementation.
     */
    private GetGroupsForUserMethod getGroupsForUserMethod = new GetGroupsForUserMethod();

    /**
     * box action implementation.
     */
    private GetMembershipMethod getMembershipMethod = new GetMembershipMethod();

    /**
     * box action implementation.
     */
    private GetMembershipsForGroupMethod getMembershipsForGroupMethod = new GetMembershipsForGroupMethod();

    /**
     * box action implementation.
     */
    private GetMembershipsForUserMethod getMembershipsForUserMethod = new GetMembershipsForUserMethod();

    /**
     * box action implementation.
     */
    private GetPendingCollaborationsMethod getPendingCollaborationsMethod = new GetPendingCollaborationsMethod();

    /**
     * box action implementation.
     */
    private GetTicketMethod getTicketMethod = new GetTicketMethod();

    /**
     * box action implementation.
     */
    private GetUserInfoMethod getUserInfoMethod = new GetUserInfoMethod();

    /**
     * box action implementation.
     */
    private GetUsersMethod getUsersMethod = new GetUsersMethod();

    /**
     * box action implementation.
     */
    private LogoutMethod logoutMethod = new LogoutMethod();

    /**
     * box action implementation.
     */
    private MoveMethod moveMethod = new MoveMethod();

    /**
     * box action implementation.
     */
    private PublicShareMethod publicShareMethod = new PublicShareMethod();

    /**
     * box action implementation.
     */
    private PublicUnshareMethod publicUnshareMethod = new PublicUnshareMethod();

    /**
     * box action implementation.
     */
    private PrivateShareMethod privateShareMethod = new PrivateShareMethod();

    /**
     * box action implementation.
     */
    private RefreshAccessTokenMethod refreshAccessTokenMethod = new RefreshAccessTokenMethod();

    /**
     * box action implementation.
     */
    private RegisterNewUserMethod registerNewUserMethod = new RegisterNewUserMethod();

    /**
     * box action implementation.
     */
    private RenameMethod renameMethod = new RenameMethod();

    /**
     * box action implementation.
     */
    private SetDescriptionMethod setDescriptionMethod = new SetDescriptionMethod();

    /**
     * box action implementation.
     */
    private SearchMethod searchMethod = new SearchMethod();

    /**
     * box action implementation.
     */
    private UpdateCollaborationMethod updateCollaborationMethod = new UpdateCollaborationMethod();

    /**
     * box action implementation.
     */
    private UpdateFileInfoMethod updateFileInfoMethod = new UpdateFileInfoMethod();

    /**
     * box action implementation.
     */
    private UpdateFolderInfoMethod updateFolderInfoMethod = new UpdateFolderInfoMethod();

    /**
     * box action implementation.
     */
    private UpdateGroupMethod updateGroupMethod = new UpdateGroupMethod();

    /**
     * box action implementation.
     */
    private UpdateMembershipMethod updateMembershipMethod = new UpdateMembershipMethod();

    /**
     * box action implementation.
     */
    private UpdateUserMethod updateUserMethod = new UpdateUserMethod();

    /**
     * box action implementation.
     */
    private VerifyRegistrationEmailMethod verifyRegistrationEmailMethod = new VerifyRegistrationEmailMethod();

    /**
     * box action implementation.
     */
    private RequestFriendsMethod requestFriendsMethod = new RequestFriendsMethod();

    /**
     * box action implementation.
     */
    private UploadMethod uploadMethod = new UploadMethod();

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
     * @param addToMyBoxRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public AddToMyBoxResponse addToMyBox(AddToMyBoxRequest addToMyBoxRequest) throws IOException, BoxException {
        return this.addToMyBoxMethod.addToMyBox(addToMyBoxRequest);
    }

    /**
     * This method adds file or folder to tags list. 'target' param can be
     * either 'file' or 'folder' depending on what do you want to add,
     * 'target_id' is the id of a file or folder to be added, 'tags' array of
     * tags where target will be added.
     *
     * On successful a result, you will receive 'addtotag_ok'. If the result
     * wasn't successful, status field can be: addtotag_error.
     *
     * @param addToTagRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public AddToTagResponse addToTag(AddToTagRequest addToTagRequest) throws IOException, BoxException {
        return this.addToTagMethod.addToTag(addToTagRequest);
    }

    /**
     * This method creates a new authorization code.
     *
     * @param createAuthorizationCodeRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public CreateAuthorizationCodeResponse createAuthorizationCode(CreateAuthorizationCodeRequest createAuthorizationCodeRequest) throws IOException, BoxException {
        return this.createAuthorizationCodeMethod.createAuthorizationCode(createAuthorizationCodeRequest);
    }

    /**
     * This method creates a new collaboration.
     *
     * @param createCollaborationRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public CreateCollaborationResponse createCollaboration(CreateCollaborationRequest createCollaborationRequest) throws IOException, BoxException {
        return this.createCollaborationMethod.createCollaboration(createCollaborationRequest);
    }

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
     * @param createFileRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public CreateFileResponse createFile(CreateFileRequest createFileRequest) throws IOException, BoxException {
        return this.createFileMethod.createFile(createFileRequest);
    }

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
     * @param createFolderRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public CreateFolderResponse createFolder(CreateFolderRequest createFolderRequest) throws IOException, BoxException {
        return this.createFolderMethod.createFolder(createFolderRequest);
    }

    /**
     * This method creates a new group.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be: 'not_logged_in',
     * 'application_r'stricted'.
     *
     * @param createGroupRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public CreateGroupResponse createGroup(CreateGroupRequest createGroupRequest) throws IOException, BoxException {
        return this.createGroupMethod.createGroup(createGroupRequest);
    }

    /**
     * This method creates a new group membership.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be: 'not_logged_in',
     * 'application_r'stricted'.
     *
     * @param createMembershipRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public CreateMembershipResponse createMembership(CreateMembershipRequest createMembershipRequest) throws IOException, BoxException {
        return this.createMembershipMethod.createMembership(createMembershipRequest);
    }

    /**
     * This method creates a new user.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be: 'not_logged_in',
     * 'application_r'stricted'.
     *
     * @param createUserRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) throws IOException, BoxException {
        return this.createUserMethod.createUser(createUserRequest);
    }

    /**
     * This method deletes a collaboration.
     *
     * @param deleteCollaborationRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public DeleteCollaborationResponse deleteCollaboration(DeleteCollaborationRequest deleteCollaborationRequest) throws IOException, BoxException {
        return this.deleteCollaborationMethod.deleteCollaboration(deleteCollaborationRequest);
    }

    /**
     * This method deletes a group.
     *
     * @param deleteGroupRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public DeleteGroupResponse deleteGroup(DeleteGroupRequest deleteGroupRequest) throws IOException, BoxException {
        return this.deleteGroupMethod.deleteGroup(deleteGroupRequest);
    }

    /**
     * This method deletes a group membership.
     *
     * @param deleteMembershipRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public DeleteMembershipResponse deleteMembership(DeleteMembershipRequest deleteMembershipRequest) throws IOException, BoxException {
        return this.deleteMembershipMethod.deleteMembership(deleteMembershipRequest);
    }

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
     * @param deleteRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public DeleteResponse delete(DeleteRequest deleteRequest) throws IOException, BoxException {
        return this.deleteMethod.delete(deleteRequest);
    }

    /**
     * This method creates a new user.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be: 'not_logged_in',
     * 'application_r'stricted'.
     *
     * @param deleteUserRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public DeleteUserResponse deleteUser(DeleteUserRequest deleteUserRequest) throws IOException, BoxException {
        return this.deleteUserMethod.deleteUser(deleteUserRequest);
    }

    /**
     * @param downloadRequest request
     * @return response
     * @throws IOException IO exception
     */
    public DownloadResponse download(DownloadRequest downloadRequest) throws IOException, BoxException {
        return this.downloadMethod.download(downloadRequest);
    }

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
     *  If the result wasn't successful, status field can be:
     * not_logged_id, application_restricted.
     *
     * @param exportTagsRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public ExportTagsResponse exportTags(ExportTagsRequest exportTagsRequest) throws IOException, BoxException {
        return this.exportTagsMethod.exportTags(exportTagsRequest);
    }

    public GetAccessTokenResponse getAccessToken(GetAccessTokenRequest getAccessTokenRequest) throws IOException, BoxException {
        return this.getAccessTokenMethod.getAccessToken(getAccessTokenRequest);
    }

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
     * @param getAccountTreeRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public GetAccountTreeResponse getAccountTree(GetAccountTreeRequest getAccountTreeRequest) throws IOException, BoxException {
        return this.getAccountTreeMethod.getAccountTree(getAccountTreeRequest);
    }

    /**
     * This method is used in the authorization process. You should call this
     * method after the user has authorized themself on box.net site. Pass
     * ticket that you get when called get_ticket method. On a successful
     * result, method will return 'get_auth_token_ok' as status, auth_token to
     * use in other method calls, and user struct which describes logged user.
     * Request.
     *
     * @param getAuthTokenRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public GetAuthTokenResponse getAuthToken(GetAuthTokenRequest getAuthTokenRequest) throws IOException, BoxException {
        LOGGER.debug("starting getAuthToken()");
        return this.getAuthTokenMethod.getAuthToken(getAuthTokenRequest);
    }

    public GetAuthorizationResponse getAuthorization(GetAuthorizationRequest getAuthorizationRequest) throws IOException, BoxException {
        LOGGER.debug("starting getAuthorization()");
        return this.getAuthorizationMethod.getAuthorization(getAuthorizationRequest);
    }

    /**
     * This method gets a collaboration.
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
     * @param getCollaborationRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public GetCollaborationResponse getCollaboration(GetCollaborationRequest getCollaborationRequest) throws IOException, BoxException {
        return this.getCollaborationMethod.getCollaboration(getCollaborationRequest);
    }

    /**
     * This method gets collaborations on a folder.
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
     * @param getCollaborationsRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public GetCollaborationsResponse getCollaborations(GetCollaborationsRequest getCollaborationsRequest) throws IOException, BoxException {
        return this.getCollaborationsMethod.getCollaborations(getCollaborationsRequest);
    }

    /**
     * This method gets file info. 'file_id' param should contain valid if of
     * user file.
     *
     * On successful a result, you will receive status 's_get_file_info' and
     * file info in 'info'. If the result wasn't successful, status field can
     * be: e_access_denied.
     *
     * @param getFileInfoRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public GetFileInfoResponse getFileInfo(GetFileInfoRequest getFileInfoRequest) throws IOException, BoxException {
        return this.getFileInfoMethod.getFileInfo(getFileInfoRequest);
    }

    public GetFolderIdResponse getFolderId(GetFolderIdRequest getFolderIdRequest) throws IOException, BoxException {
        return this.getFolderIdMethod.getFolderId(getFolderIdRequest);
    }

    public GetFolderInfoResponse getFolderInfo(GetFolderInfoRequest getFolderInfoRequest) throws IOException, BoxException {
        return this.getFolderInfoMethod.getFolderInfo(getFolderInfoRequest);
    }

    public GetFolderItemsResponse getFolderItems(GetFolderItemsRequest getFolderItemsRequest) throws IOException, BoxException {
        return this.getFolderItemsMethod.getFolderItems(getFolderItemsRequest);
    }

    public GetFileIdResponse getFileId(GetFileIdRequest getFileIdRequest) throws IOException, BoxException {
        return this.getFileIdMethod.getFileId(getFileIdRequest);
    }

    /**
     * This method is used to retrieve a list of friends.
     *
     * 'params' is an array of the string where you can set additional
     * parameters, which are: nozip - do not zip tree xml.
     *
     * On a successful result you will receive 's_get_friends' as the status and
     * base64 encoded (and zipped) friends xml. Friends xml looks like this:
     * ......
     *
     * @param getFriendsRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public GetFriendsResponse getFriends(GetFriendsRequest getFriendsRequest) throws IOException, BoxException {
        return this.getFriendsMethod.getFriends(getFriendsRequest);
    }

    /**
     * This method gets a group.
     *
     * @param getGroupRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public GetGroupResponse getGroup(GetGroupRequest getGroupRequest) throws IOException, BoxException {
        return this.getGroupMethod.getGroup(getGroupRequest);
    }

    /**
     * This method gets groups for a user.
     *
     * @param getGroupsForUserRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public GetGroupsForUserResponse getGroupsForUser(GetGroupsForUserRequest getGroupsForUserRequest) throws IOException, BoxException {
        return this.getGroupsForUserMethod.getGroupsForUser(getGroupsForUserRequest);
    }

    /**
     * This method gets a membership.
     *
     * @param getMembershipRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public GetMembershipResponse getMembership(GetMembershipRequest getMembershipRequest) throws IOException, BoxException {
        return this.getMembershipMethod.getMembership(getMembershipRequest);
    }

    /**
     * This method gets memberships for a group.
     *
     * @param getMembershipsForGroupRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public GetMembershipsForGroupResponse getMembershipsForGroup(GetMembershipsForGroupRequest getMembershipsForGroupRequest) throws IOException, BoxException {
        return this.getMembershipsForGroupMethod.getMembershipsForGroup(getMembershipsForGroupRequest);
    }

    /**
     * This method gets membership for a users.
     *
     * @param getMembershipsForUserRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public GetMembershipsForUserResponse getMembershipsForUser(GetMembershipsForUserRequest getMembershipsForUserRequest) throws IOException, BoxException {
        return this.getMembershipsForUserMethod.getMembershipsForUser(getMembershipsForUserRequest);
    }

    /**
     * This method gets pending collaborations.
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
     * @param getPendingCollaborationsRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public GetPendingCollaborationsResponse getPendingCollaborations(GetPendingCollaborationsRequest getPendingCollaborationsRequest) throws IOException, BoxException {
        return this.getPendingCollaborationsMethod.getPendingCollaborations(getPendingCollaborationsRequest);
    }

    /**
     * @param getTicketRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public GetTicketResponse getTicket(GetTicketRequest getTicketRequest) throws IOException, BoxException {
        return this.getTicketMethod.getTicket(getTicketRequest);
    }

    /**
     * This method gets user info. 'file_id' param should contain valid if of
     * user file.
     *
     * On successful a result, you will receive status 's_get_file_info' and
     * file info in 'info'. If the result wasn't successful, status field can
     * be: e_access_denied.
     *
     * @param getUserInfoRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public GetUserInfoResponse getUserInfo(GetUserInfoRequest getUserInfoRequest) throws IOException, BoxException {
        return this.getUserInfoMethod.getUserInfo(getUserInfoRequest);
    }

    /**
     * @param getUsersRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public GetUsersResponse getUsers(GetUsersRequest getUsersRequest) throws IOException, BoxException {
        return this.getUsersMethod.getUsers(getUsersRequest);
    }

    /**
     * This method is used to logout a user. On successful logout method will
     * return 'logout_ok' as status. If logout wasn't successful, then status
     * filed can be: 'invalid_auth_token' when auth_token is invalid.
     *
     * @param logoutRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public LogoutResponse logout(LogoutRequest logoutRequest) throws IOException, BoxException {
        return this.logoutMethod.logout(logoutRequest);
    }

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
     * @param moveRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public MoveResponse move(MoveRequest moveRequest) throws IOException, BoxException {
        return this.moveMethod.move(moveRequest);
    }

    public RefreshAccessTokenResponse refreshAccessToken(RefreshAccessTokenRequest refreshAccessTokenRequest) throws IOException, BoxException {
        return this.refreshAccessTokenMethod.refreshAccessToken(refreshAccessTokenRequest);
    }

    /**
     * This method is used to register a user. Upon a successful registration,
     * the status param will be 'successful_register'. If registration wasn't
     * successful, status field can be: 'e_register', 'email_invalid',
     * 'email_already_registered', 'application_restricted'.
     *
     * @param registerNewUserRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public RegisterNewUserResponse registerNewUser(RegisterNewUserRequest registerNewUserRequest) throws IOException, BoxException {
        return this.registerNewUserMethod.registerNewUser(registerNewUserRequest);
    }

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
     * @param requestFriendsRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public RequestFriendsResponse requestFriends(RequestFriendsRequest requestFriendsRequest) throws IOException, BoxException {
        return this.requestFriendsMethod.requestFriends(requestFriendsRequest);
    }

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
     * @param privateShareRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public PrivateShareResponse privateShare(PrivateShareRequest privateShareRequest) throws IOException, BoxException {
        return this.privateShareMethod.privateShare(privateShareRequest);
    }

    /**
     * This method unshares a shared file or folder. 'target' param shoud be
     * either 'file' or 'folder', 'target_id' is id of a file or folder to be
     * unshared.
     *
     * On a successful result, the status will be 'unshare_ok'. If the result
     * wasn't successful, the status field can be: 'unshare_error',
     * 'wrong_node', 'not_logged_in', 'application_restricted'.
     *
     * @param publicUnshareRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public PublicUnshareResponse publicUnshare(PublicUnshareRequest publicUnshareRequest) throws IOException, BoxException {
        return this.publicUnshareMethod.publicUnshare(publicUnshareRequest);
    }

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
     * @param publicShareRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public PublicShareResponse publicShare(PublicShareRequest publicShareRequest) throws IOException, BoxException {
        return this.publicShareMethod.publicShare(publicShareRequest);
    }

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
     * @param renameRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public RenameResponse rename(RenameRequest renameRequest) throws IOException, BoxException {
        return this.renameMethod.rename(renameRequest);
    }

    /**
     * This method searches Box.net.
     *
     * @param searchRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public SearchResponse search(SearchRequest searchRequest) throws IOException, BoxException {
        return this.searchMethod.search(searchRequest);
    }

    /**
     * This method sets a description to a file or folder. 'target' can be a
     * 'file' or 'folder', 'target_id' - id of file or folder, description -
     * string that should be set as description.
     *
     * On successful a result, you will receive status 's_set_description'. If
     * the result wasn't successful, status field can be: e_set_description.
     *
     * @param setDescriptionRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public SetDescriptionResponse setDescription(SetDescriptionRequest setDescriptionRequest) throws IOException, BoxException {
        return this.setDescriptionMethod.setDescription(setDescriptionRequest);
    }

    /**
     * @param updateCollaborationRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public UpdateCollaborationResponse updateCollaboration(UpdateCollaborationRequest updateCollaborationRequest) throws IOException, BoxException {
        LOGGER.debug("starting updateCollaboration()");
        return this.updateCollaborationMethod.updateCollaboration(updateCollaborationRequest);
    }

    /**
     * @param updateFileInfoRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public UpdateFileInfoResponse updateFileInfo(UpdateFileInfoRequest updateFileInfoRequest) throws IOException, BoxException {
        LOGGER.debug("starting updateFileInfo()");
        return this.updateFileInfoMethod.updateFileInfo(updateFileInfoRequest);
    }

    /**
     * @param updateFolderInfoRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public UpdateFolderInfoResponse updateFolderInfo(UpdateFolderInfoRequest updateFolderInfoRequest) throws IOException, BoxException {
        LOGGER.debug("starting updateFolderInfo()");
        return this.updateFolderInfoMethod.updateFolderInfo(updateFolderInfoRequest);
    }

    /**
     * @param updateGroupRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public UpdateGroupResponse updateGroup(UpdateGroupRequest updateGroupRequest) throws IOException, BoxException {
        LOGGER.debug("starting updateGroup()");
        return this.updateGroupMethod.updateGroup(updateGroupRequest);
    }

    /**
     * @param updateMembershipRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public UpdateMembershipResponse updateMembership(UpdateMembershipRequest updateMembershipRequest) throws IOException, BoxException {
        LOGGER.debug("starting updateMembership()");
        return this.updateMembershipMethod.updateMembership(updateMembershipRequest);
    }

    /**
     * @param updateUserRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) throws IOException, BoxException {
        LOGGER.debug("starting updateUser()");
        return this.updateUserMethod.updateUser(updateUserRequest);
    }

    /**
     * @param uploadRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public UploadResponse upload(UploadRequest uploadRequest) throws BoxException, Exception, IOException {
        LOGGER.debug("starting upload()");
        return this.uploadMethod.upload(uploadRequest);
    }

    /**
     * This method is used to verify user registration email . Upon a not used
     * and right registration email, the status param will be 'email_ok'. Else
     * status field can be: 'email_invalid', 'email_already_registered',
     * 'application_restricted'.
     *
     * @param verifyRegistrationEmailRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public VerifyRegistrationEmailResponse verifyRegistrationEmail(VerifyRegistrationEmailRequest verifyRegistrationEmailRequest) throws IOException, BoxException {
        return this.verifyRegistrationEmailMethod.verifyRegistrationEmail(verifyRegistrationEmailRequest);
    }
}
