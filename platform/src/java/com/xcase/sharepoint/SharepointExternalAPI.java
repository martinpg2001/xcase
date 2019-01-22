/**
 * Copyright 2016 Xcase All rights reserved.
 */
/**
 *
 */
package com.xcase.sharepoint;

import com.xcase.sharepoint.objects.SharepointException;
import com.xcase.sharepoint.transputs.AddToMySharepointRequest;
import com.xcase.sharepoint.transputs.AddToMySharepointResponse;
import com.xcase.sharepoint.transputs.AddToTagRequest;
import com.xcase.sharepoint.transputs.AddToTagResponse;
import com.xcase.sharepoint.transputs.CreateFolderRequest;
import com.xcase.sharepoint.transputs.CreateFolderResponse;
import com.xcase.sharepoint.transputs.DeleteRequest;
import com.xcase.sharepoint.transputs.DeleteResponse;
import com.xcase.sharepoint.transputs.DownloadRequest;
import com.xcase.sharepoint.transputs.DownloadResponse;
import com.xcase.sharepoint.transputs.ExportTagsRequest;
import com.xcase.sharepoint.transputs.ExportTagsResponse;
import com.xcase.sharepoint.transputs.GetAccessTokenRequest;
import com.xcase.sharepoint.transputs.GetAccessTokenResponse;
import com.xcase.sharepoint.transputs.GetAccountTreeRequest;
import com.xcase.sharepoint.transputs.GetAccountTreeResponse;
import com.xcase.sharepoint.transputs.GetAuthTokenRequest;
import com.xcase.sharepoint.transputs.GetAuthTokenResponse;
import com.xcase.sharepoint.transputs.GetAuthorizationRequest;
import com.xcase.sharepoint.transputs.GetAuthorizationResponse;
import com.xcase.sharepoint.transputs.GetFileInfoRequest;
import com.xcase.sharepoint.transputs.GetFileInfoResponse;
import com.xcase.sharepoint.transputs.GetFolderInfoRequest;
import com.xcase.sharepoint.transputs.GetFolderInfoResponse;
import com.xcase.sharepoint.transputs.GetFriendsRequest;
import com.xcase.sharepoint.transputs.GetFriendsResponse;
import com.xcase.sharepoint.transputs.GetTicketRequest;
import com.xcase.sharepoint.transputs.GetTicketResponse;
import com.xcase.sharepoint.transputs.LogoutRequest;
import com.xcase.sharepoint.transputs.LogoutResponse;
import com.xcase.sharepoint.transputs.MoveRequest;
import com.xcase.sharepoint.transputs.MoveResponse;
import com.xcase.sharepoint.transputs.PrivateShareRequest;
import com.xcase.sharepoint.transputs.PrivateShareResponse;
import com.xcase.sharepoint.transputs.PublicShareRequest;
import com.xcase.sharepoint.transputs.PublicShareResponse;
import com.xcase.sharepoint.transputs.PublicUnshareRequest;
import com.xcase.sharepoint.transputs.PublicUnshareResponse;
import com.xcase.sharepoint.transputs.RegisterNewUserRequest;
import com.xcase.sharepoint.transputs.RegisterNewUserResponse;
import com.xcase.sharepoint.transputs.RenameRequest;
import com.xcase.sharepoint.transputs.RenameResponse;
import com.xcase.sharepoint.transputs.RequestFriendsRequest;
import com.xcase.sharepoint.transputs.RequestFriendsResponse;
import com.xcase.sharepoint.transputs.SetDescriptionRequest;
import com.xcase.sharepoint.transputs.SetDescriptionResponse;
import com.xcase.sharepoint.transputs.UploadRequest;
import com.xcase.sharepoint.transputs.UploadResponse;
import com.xcase.sharepoint.transputs.VerifyRegistrationEmailRequest;
import com.xcase.sharepoint.transputs.VerifyRegistrationEmailResponse;
import java.io.IOException;

/**
 * @author martin
 *
 */
public interface SharepointExternalAPI {

    /**
     *
     * @param getAccessTokenRequest request object
     * @return getAccessTokenResponse response object
     * @throws IOException io exception
     * @throws SharepointException sharepoint exception
     */
    GetAccessTokenResponse getAccessToken(GetAccessTokenRequest getAccessTokenRequest) throws IOException, SharepointException;

    /**
     * This method is used in the authorization process. You should call this
     * method after the user has authorized themself on box.net site. Pass
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
    GetAuthTokenResponse getAuthToken(GetAuthTokenRequest getAuthTokenRequest) throws IOException, SharepointException;

    GetAuthorizationResponse getAuthorization(GetAuthorizationRequest getAuthorizationRequest) throws IOException, SharepointException;

    /**
     * This method is used in the authorization process.
     *
     * @param getTicketRequest request object
     * @return getTicketResponse response object
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    GetTicketResponse getTicket(GetTicketRequest getTicketRequest) throws IOException, SharepointException;

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
    LogoutResponse logout(LogoutRequest logoutRequest) throws IOException, SharepointException;

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
    RegisterNewUserResponse registerNewUser(RegisterNewUserRequest registerNewUserRequest) throws IOException, SharepointException;

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
    VerifyRegistrationEmailResponse verifyRegistrationEmail(VerifyRegistrationEmailRequest verifyRegistrationEmailRequest) throws IOException, SharepointException;

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
    GetAccountTreeResponse getAccountTree(GetAccountTreeRequest getAccountTreeRequest) throws IOException, SharepointException;

    /**
     * This method returns all the user's tags.
     *
     * On successful a result, you will receive 'export_tags_ok' and tag_xml
     * will be base64 encoded tags xml. After decoding tag_xml you will get xml
     * like this:
     *
     * <?xml version="1.0"?> <tags> <tag id="37"> music </tag> <tag id="38"> mp3
     * </tag> </tags> If the result wasn't successful, status field can be:
     * not_logged_id, application_restricted.
     *
     * @param exportTagsRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    ExportTagsResponse exportTags(ExportTagsRequest exportTagsRequest) throws IOException, SharepointException;

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
    CreateFolderResponse createFolder(CreateFolderRequest createFolderRequest) throws IOException, SharepointException;

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
    MoveResponse move(MoveRequest moveRequest) throws IOException, SharepointException;

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
    RenameResponse rename(RenameRequest renameRequest) throws IOException, SharepointException;

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
    DeleteResponse delete(DeleteRequest deleteRequest) throws IOException,
            SharepointException;

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
    PublicShareResponse publicShare(PublicShareRequest publicShareRequest) throws IOException, SharepointException;

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
    PublicUnshareResponse publicUnshare(PublicUnshareRequest publicUnshareRequest) throws IOException, SharepointException;

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
    PrivateShareResponse privateShare(PrivateShareRequest privateShareRequest)
            throws IOException, SharepointException;

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
     * @param addToMySharepointRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    AddToMySharepointResponse addToMySharepoint(AddToMySharepointRequest addToMySharepointRequest)
            throws IOException, SharepointException;

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
    AddToTagResponse addToTag(AddToTagRequest addToTagRequest)
            throws IOException, SharepointException;

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
     * @throws SharepointException SharePoint exception
     */
    GetFileInfoResponse getFileInfo(GetFileInfoRequest getFileInfoRequest)
            throws IOException, SharepointException;

    GetFolderInfoResponse getFolderInfo(GetFolderInfoRequest getFolderInfoRequest)
            throws IOException, SharepointException;

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
    SetDescriptionResponse setDescription(
            SetDescriptionRequest setDescriptionRequest) throws IOException, SharepointException;

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
     * @param getFriendsRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    GetFriendsResponse getFriends(GetFriendsRequest getFriendsRequest)
            throws IOException, SharepointException;

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
    RequestFriendsResponse requestFriends(
            RequestFriendsRequest requestFriendsRequest) throws IOException, SharepointException;

    /**
     * download a file.
     *
     * @param downloadRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    DownloadResponse download(DownloadRequest downloadRequest)
            throws IOException, SharepointException;

    /**
     * upload files.
     *
     * @param uploadRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    UploadResponse upload(UploadRequest uploadRequest) throws IOException, SharepointException;

}
