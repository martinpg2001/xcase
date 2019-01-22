/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint;

import com.xcase.common.impl.simple.core.ConfigurationManager;
import com.xcase.sharepoint.impl.simple.methods.AddToMySharepointMethod;
import com.xcase.sharepoint.impl.simple.methods.AddToTagMethod;
import com.xcase.sharepoint.impl.simple.methods.CreateFolderMethod;
import com.xcase.sharepoint.impl.simple.methods.DeleteMethod;
import com.xcase.sharepoint.impl.simple.methods.DownloadMethod;
import com.xcase.sharepoint.impl.simple.methods.ExportTagsMethod;
import com.xcase.sharepoint.impl.simple.methods.GetAccessTokenMethod;
import com.xcase.sharepoint.impl.simple.methods.GetAccountTreeMethod;
import com.xcase.sharepoint.impl.simple.methods.GetAuthTokenMethod;
import com.xcase.sharepoint.impl.simple.methods.GetAuthorizationMethod;
import com.xcase.sharepoint.impl.simple.methods.GetFileInfoMethod;
import com.xcase.sharepoint.impl.simple.methods.GetFolderInfoMethod;
import com.xcase.sharepoint.impl.simple.methods.GetFriendsMethod;
import com.xcase.sharepoint.impl.simple.methods.GetTicketMethod;
import com.xcase.sharepoint.impl.simple.methods.LogoutMethod;
import com.xcase.sharepoint.impl.simple.methods.MoveMethod;
import com.xcase.sharepoint.impl.simple.methods.PrivateShareMethod;
import com.xcase.sharepoint.impl.simple.methods.PublicShareMethod;
import com.xcase.sharepoint.impl.simple.methods.PublicUnshareMethod;
import com.xcase.sharepoint.impl.simple.methods.RegisterNewUserMethod;
import com.xcase.sharepoint.impl.simple.methods.RenameMethod;
import com.xcase.sharepoint.impl.simple.methods.RequestFriendsMethod;
import com.xcase.sharepoint.impl.simple.methods.SetDescriptionMethod;
import com.xcase.sharepoint.impl.simple.methods.UploadMethod;
import com.xcase.sharepoint.impl.simple.methods.VerifyRegistrationEmailMethod;
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
import java.lang.invoke.*;
import org.apache.logging.log4j.*;

/**
 * @author martin
 *
 */
public class SimpleSharepointImpl implements SharepointExternalAPI {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    /**
     * configuration manager
     */
    public ConfigurationManager localConfigurationManager = ConfigurationManager.getConfigurationManager();

    /**
     * sharepoint action implementation.
     */
    private GetTicketMethod getTicketMethod = new GetTicketMethod();

    /**
     * sharepoint action implementation.
     */
    private GetAccessTokenMethod getAccessTokenMethod = new GetAccessTokenMethod();

    /**
     * sharepoint action implementation.
     */
    private GetAuthorizationMethod getAuthorizationMethod = new GetAuthorizationMethod();

    /**
     * sharepoint action implementation.
     */
    private GetAuthTokenMethod getAuthTokenMethod = new GetAuthTokenMethod();

    /**
     * sharepoint action implementation.
     */
    private LogoutMethod logoutMethod = new LogoutMethod();

    /**
     * sharepoint action implementation.
     */
    private RegisterNewUserMethod registerNewUserMethod = new RegisterNewUserMethod();

    /**
     * sharepoint action implementation.
     */
    private VerifyRegistrationEmailMethod verifyRegistrationEmailMethod = new VerifyRegistrationEmailMethod();

    /**
     * sharepoint action implementation.
     */
    private GetAccountTreeMethod getAccountTreeMethod = new GetAccountTreeMethod();

    /**
     * sharepoint action implementation.
     */
    private ExportTagsMethod exportTagsMethod = new ExportTagsMethod();

    /**
     * sharepoint action implementation.
     */
    private CreateFolderMethod createFolderMethod = new CreateFolderMethod();

    /**
     * sharepoint action implementation.
     */
    private MoveMethod moveMethod = new MoveMethod();

    /**
     * sharepoint action implementation.
     */
    private RenameMethod renameMethod = new RenameMethod();

    /**
     * sharepoint action implementation.
     */
    private DeleteMethod deleteMethod = new DeleteMethod();

    /**
     * sharepoint action implementation.
     */
    private PublicShareMethod publicShareMethod = new PublicShareMethod();

    /**
     * sharepoint action implementation.
     */
    private PublicUnshareMethod publicUnshareMethod = new PublicUnshareMethod();

    /**
     * sharepoint action implementation.
     */
    private PrivateShareMethod privateShareMethod = new PrivateShareMethod();

    /**
     * sharepoint action implementation.
     */
    private AddToMySharepointMethod addToMySharepointMethod = new AddToMySharepointMethod();

    /**
     * sharepoint action implementation.
     */
    private AddToTagMethod addToTagMethod = new AddToTagMethod();

    /**
     * sharepoint action implementation.
     */
    private GetFileInfoMethod getFileInfoMethod = new GetFileInfoMethod();

    /**
     * sharepoint action implementation.
     */
    private GetFolderInfoMethod getFolderInfoMethod = new GetFolderInfoMethod();

    /**
     * sharepoint action implementation.
     */
    private SetDescriptionMethod setDescriptionMethod = new SetDescriptionMethod();

    /**
     * sharepoint action implementation.
     */
    private GetFriendsMethod getFriendsMethod = new GetFriendsMethod();

    /**
     * sharepoint action implementation.
     */
    private RequestFriendsMethod requestFriendsMethod = new RequestFriendsMethod();

    /**
     * sharepoint action implementation.
     */
    private DownloadMethod downloadMethod = new DownloadMethod();

    /**
     * sharepoint action implementation.
     */
    private UploadMethod uploadMethod = new UploadMethod();

    /**
     * @param getAccessTokenRequest request
     * @return response
     * @throws IOException IO exception
     * @throws SharepointException sharepoint exception
     */
    public GetAccessTokenResponse getAccessToken(GetAccessTokenRequest getAccessTokenRequest) throws IOException, SharepointException {
        LOGGER.debug("starting getAccessToken()");
        return this.getAccessTokenMethod.getAccessToken(getAccessTokenRequest);
    }

    /**
     * @param uploadRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException sharepoint exception
     */
    public UploadResponse upload(UploadRequest uploadRequest) throws IOException, SharepointException {
        LOGGER.debug("starting upload()");
        return this.uploadMethod.upload(uploadRequest);
    }

    /**
     * @param downloadRequest request
     * @return response
     * @throws IOException IO exception
     */
    public DownloadResponse download(DownloadRequest downloadRequest) throws IOException, SharepointException {
        return this.downloadMethod.download(downloadRequest);
    }

    /**
     * This method requests new friends to be added to your network. 'emails' -
     * array of emails. 'message' - text message that you want to send to
     * freinds. 'params' is an array of string where you can set additional
     * parameters, which are: sharepoint_auto_subscribe - subscribe to public
     * sharepointes of inveted users. no_email - don't send emails to invited
     * users.
     *
     * On a successful result, you will receive status 's_request_friends'. If
     * the result wasn't successful, status field can be: e_request_friends.
     *
     * @param requestFriendsRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException sharepoint exception
     */
    public RequestFriendsResponse requestFriends(RequestFriendsRequest requestFriendsRequest) throws IOException, SharepointException {
        return this.requestFriendsMethod.requestFriends(requestFriendsRequest);
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
     * @throws BoxException sharepoint exception
     */
    public GetFriendsResponse getFriends(GetFriendsRequest getFriendsRequest) throws IOException, SharepointException {
        return this.getFriendsMethod.getFriends(getFriendsRequest);
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
     * @throws BoxException sharepoint exception
     */
    public SetDescriptionResponse setDescription(SetDescriptionRequest setDescriptionRequest) throws IOException, SharepointException {
        return this.setDescriptionMethod.setDescription(setDescriptionRequest);
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
     * @throws BoxException sharepoint exception
     */
    public GetFileInfoResponse getFileInfo(GetFileInfoRequest getFileInfoRequest) throws IOException, SharepointException {
        LOGGER.debug("starting getFileInfo()");
        return this.getFileInfoMethod.getFileInfo(getFileInfoRequest);
    }

    public GetFolderInfoResponse getFolderInfo(GetFolderInfoRequest getFolderInfoRequest) throws IOException, SharepointException {
        return this.getFolderInfoMethod.getFolderInfo(getFolderInfoRequest);
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
     * @throws BoxException sharepoint exception
     */
    public AddToTagResponse addToTag(AddToTagRequest addToTagRequest)
            throws IOException, SharepointException {
        return this.addToTagMethod.addToTag(addToTagRequest);
    }

    /**
     * This method copies a file publicly shared by someone to a user's
     * mysharepoint. 'file_id' and 'public_name' params identify a publicly
     * shared file, you should provide either file_id or public name (like
     * '31nhke0ahp') as a parameter. 'folder_id' is the id of a user's folder,
     * where files are to be copied.
     *
     * On a successful result, the status will be 'addtomysharepoint_ok'. If the
     * result wasn't successful, the status field can be:
     * 'addtomysharepoint_error', 'not_logged_id', 'application_restricted',
     * 's_link_exists'.
     *
     * @param addToMySharepointRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException sharepoint exception
     */
    public AddToMySharepointResponse addToMySharepoint(AddToMySharepointRequest addToMySharepointRequest)
            throws IOException, SharepointException {
        return this.addToMySharepointMethod.addToMySharepoint(addToMySharepointRequest);
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
     * @throws BoxException sharepoint exception
     */
    public PrivateShareResponse privateShare(
            PrivateShareRequest privateShareRequest) throws IOException,
            SharepointException {
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
     * @throws BoxException sharepoint exception
     */
    public PublicUnshareResponse publicUnshare(
            PublicUnshareRequest publicUnshareRequest) throws IOException,
            SharepointException {
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
     * @throws BoxException sharepoint exception
     */
    public PublicShareResponse publicShare(PublicShareRequest publicShareRequest)
            throws IOException, SharepointException {
        return this.publicShareMethod.publicShare(publicShareRequest);
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
     * @throws BoxException sharepoint exception
     */
    public DeleteResponse delete(DeleteRequest deleteRequest)
            throws IOException, SharepointException {
        return this.deleteMethod.delete(deleteRequest);
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
     * @throws BoxException sharepoint exception
     */
    public RenameResponse rename(RenameRequest renameRequest)
            throws IOException, SharepointException {
        return this.renameMethod.rename(renameRequest);
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
     * @throws BoxException sharepoint exception
     */
    public MoveResponse move(MoveRequest moveRequest) throws IOException,
            SharepointException {
        return this.moveMethod.move(moveRequest);
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
     * @throws BoxException sharepoint exception
     */
    public CreateFolderResponse createFolder(
            CreateFolderRequest createFolderRequest) throws IOException,
            SharepointException {
        return this.createFolderMethod.createFolder(createFolderRequest);
    }

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
     * @param exportTagsRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException sharepoint exception
     */
    public ExportTagsResponse exportTags(ExportTagsRequest exportTagsRequest)
            throws IOException, SharepointException {
        return this.exportTagsMethod.exportTags(exportTagsRequest);
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
     * @throws BoxException sharepoint exception
     */
    public GetAccountTreeResponse getAccountTree(
            GetAccountTreeRequest getAccountTreeRequest) throws IOException, SharepointException {
        return this.getAccountTreeMethod.getAccountTree(getAccountTreeRequest);
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
     * @throws BoxException sharepoint exception
     */
    public VerifyRegistrationEmailResponse verifyRegistrationEmail(
            VerifyRegistrationEmailRequest verifyRegistrationEmailRequest) throws IOException, SharepointException {
        return this.verifyRegistrationEmailMethod.verifyRegistrationEmail(verifyRegistrationEmailRequest);
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
     * @throws BoxException sharepoint exception
     */
    public RegisterNewUserResponse registerNewUser(
            RegisterNewUserRequest registerNewUserRequest) throws IOException, SharepointException {
        return this.registerNewUserMethod.registerNewUser(registerNewUserRequest);
    }

    /**
     * This method is used to logout a user. On successful logout method will
     * return 'logout_ok' as status. If logout wasn't successful, then status
     * filed can be: 'invalid_auth_token' when auth_token is invalid.
     *
     * @param logoutRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException sharepoint exception
     */
    public LogoutResponse logout(LogoutRequest logoutRequest)
            throws IOException, SharepointException {
        return this.logoutMethod.logout(logoutRequest);
    }

    /**
     * This method is used in the authorization process. You should call this
     * method after the user has authorized themself on sharepoint.net site.
     * Pass ticket that you get when called get_ticket method. On a successful
     * result, method will return 'get_auth_token_ok' as status, auth_token to
     * use in other method calls, and user struct which describes logged user.
     * Request.
     *
     * @param getAuthTokenRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException sharepoint exception
     */
    public GetAuthTokenResponse getAuthToken(GetAuthTokenRequest getAuthTokenRequest) throws IOException, SharepointException {
        LOGGER.debug("starting getAuthToken()");
        return this.getAuthTokenMethod.getAuthToken(getAuthTokenRequest);
    }

    public GetAuthorizationResponse getAuthorization(GetAuthorizationRequest getAuthorizationRequest) throws IOException, SharepointException {
        LOGGER.debug("starting getAuthorization()");
        return this.getAuthorizationMethod.getAuthorization(getAuthorizationRequest);
    }

    /**
     * @param getTicketRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException sharepoint exception
     */
    public GetTicketResponse getTicket(GetTicketRequest getTicketRequest) throws IOException, SharepointException {
        return this.getTicketMethod.getTicket(getTicketRequest);
    }
}
