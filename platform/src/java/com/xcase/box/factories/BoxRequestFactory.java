/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.factories;

import com.xcase.box.impl.simple.core.BoxConfigurationManager;
import com.xcase.box.transputs.AddToMyBoxRequest;
import com.xcase.box.transputs.AddToTagRequest;
import com.xcase.box.transputs.CreateAuthorizationCodeRequest;
import com.xcase.box.transputs.CreateCollaborationRequest;
import com.xcase.box.transputs.CreateFileRequest;
import com.xcase.box.transputs.CreateFolderRequest;
import com.xcase.box.transputs.CreateGroupRequest;
import com.xcase.box.transputs.CreateMembershipRequest;
import com.xcase.box.transputs.CreateUserRequest;
import com.xcase.box.transputs.DeleteCollaborationRequest;
import com.xcase.box.transputs.DeleteGroupRequest;
import com.xcase.box.transputs.DeleteMembershipRequest;
import com.xcase.box.transputs.DeleteRequest;
import com.xcase.box.transputs.DeleteUserRequest;
import com.xcase.box.transputs.DownloadRequest;
import com.xcase.box.transputs.ExportTagsRequest;
import com.xcase.box.transputs.GetAccessTokenRequest;
import com.xcase.box.transputs.GetAccountTreeRequest;
import com.xcase.box.transputs.GetAuthTokenRequest;
import com.xcase.box.transputs.GetAuthorizationRequest;
import com.xcase.box.transputs.GetCollaborationRequest;
import com.xcase.box.transputs.GetCollaborationsRequest;
import com.xcase.box.transputs.GetFileIdRequest;
import com.xcase.box.transputs.GetFileInfoRequest;
import com.xcase.box.transputs.GetFolderIdRequest;
import com.xcase.box.transputs.GetFolderInfoRequest;
import com.xcase.box.transputs.GetFolderItemsRequest;
import com.xcase.box.transputs.GetFriendsRequest;
import com.xcase.box.transputs.GetGroupRequest;
import com.xcase.box.transputs.GetGroupsForUserRequest;
import com.xcase.box.transputs.GetMembershipRequest;
import com.xcase.box.transputs.GetMembershipsForGroupRequest;
import com.xcase.box.transputs.GetMembershipsForUserRequest;
import com.xcase.box.transputs.GetPendingCollaborationsRequest;
import com.xcase.box.transputs.GetTicketRequest;
import com.xcase.box.transputs.GetUserInfoRequest;
import com.xcase.box.transputs.GetUsersRequest;
import com.xcase.box.transputs.LogoutRequest;
import com.xcase.box.transputs.MoveRequest;
import com.xcase.box.transputs.PrivateShareRequest;
import com.xcase.box.transputs.PublicShareRequest;
import com.xcase.box.transputs.PublicUnshareRequest;
import com.xcase.box.transputs.RefreshAccessTokenRequest;
import com.xcase.box.transputs.RegisterNewUserRequest;
import com.xcase.box.transputs.RenameRequest;
import com.xcase.box.transputs.RequestFriendsRequest;
import com.xcase.box.transputs.SearchRequest;
import com.xcase.box.transputs.SetDescriptionRequest;
import com.xcase.box.transputs.UpdateCollaborationRequest;
import com.xcase.box.transputs.UpdateFileInfoRequest;
import com.xcase.box.transputs.UpdateFolderInfoRequest;
import com.xcase.box.transputs.UpdateGroupRequest;
import com.xcase.box.transputs.UpdateMembershipRequest;
import com.xcase.box.transputs.UpdateUserRequest;
import com.xcase.box.transputs.UploadRequest;
import com.xcase.box.transputs.VerifyRegistrationEmailRequest;
import java.io.File;
import java.lang.invoke.*;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.*;

/**
 * @author martin
 *
 */
public class BoxRequestFactory extends BaseBoxFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * create request object.
     *
     * @return request object
     */
    public static AddToMyBoxRequest createAddToMyBoxRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.AddToMyBoxRequest");
        return (AddToMyBoxRequest) obj;
    }

    /**
     * create request object.
     *
     * @param apiKey API key
     * @param authToken auth token
     * @param fileId file id
     * @param publicName public name
     * @param folderId folder id
     * @param tags tag string array
     * @return request object
     */
    public static AddToMyBoxRequest createAddToMyBoxRequest(String apiKey, String authToken, String fileId, String publicName, String folderId, String[] tags) {
        AddToMyBoxRequest addToMyBoxRequest = createAddToMyBoxRequest();
        addToMyBoxRequest.setApiKey(apiKey);
        addToMyBoxRequest.setAuthToken(authToken);
        addToMyBoxRequest.setFileId(fileId);
        addToMyBoxRequest.setPublicName(publicName);
        addToMyBoxRequest.setFolderId(folderId);
        addToMyBoxRequest.setTags(tags);
        return addToMyBoxRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static AddToTagRequest createAddToTagRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.AddToTagRequest");
        return (AddToTagRequest) obj;
    }

    /**
     *
     * create request object.
     *
     * @param apiKey API key
     * @param authToken auth token
     * @param tags tag string array
     * @param target target
     * @param targetId target id
     * @return request object
     */
    public static AddToTagRequest createAddToTagRequest(String apiKey, String authToken, String[] tags, String target, String targetId) {
        AddToTagRequest addToTagRequest = createAddToTagRequest();
        addToTagRequest.setApiKey(apiKey);
        addToTagRequest.setAuthToken(authToken);
        addToTagRequest.setTags(tags);
        addToTagRequest.setTarget(target);
        addToTagRequest.setTargetId(targetId);
        return addToTagRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateAuthorizationCodeRequest createCreateAuthorizationCodeRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.CreateAuthorizationCodeRequest");
        return (CreateAuthorizationCodeRequest) obj;
    }

    /**
     *
     * create request object.
     *
     * @param apiKey API key
     * @param username username
     * @param password password
     * @param redirectURI redirectURI
     * @return request object
     */
    public static CreateAuthorizationCodeRequest createCreateAuthorizationCodeRequest(String apiKey, String username, String password, String redirectURI) {
        CreateAuthorizationCodeRequest createAuthorizationCodeRequest = createCreateAuthorizationCodeRequest();
        createAuthorizationCodeRequest.setApiKey(apiKey);
        createAuthorizationCodeRequest.setUsername(username);
        createAuthorizationCodeRequest.setPassword(password);
        createAuthorizationCodeRequest.setRedirectURI(redirectURI);
        return createAuthorizationCodeRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateCollaborationRequest createCreateCollaborationRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.CreateCollaborationRequest");
        return (CreateCollaborationRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param folderId
     * @param role
     * @param login
     *
     * @return request object
     */
    public static CreateCollaborationRequest createCreateCollaborationRequest(String accessToken, String folderId, String login, String role) {
        CreateCollaborationRequest createCollaborationRequest = createCreateCollaborationRequest();
        createCollaborationRequest.setAccessToken(accessToken);
        createCollaborationRequest.setFolderId(folderId);
        createCollaborationRequest.setAccessibleBy(login);
        createCollaborationRequest.setRole(role);
        return createCollaborationRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateFileRequest createCreateFileRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.CreateFileRequest");
        return (CreateFileRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param fileName
     * @param parentFolderId parent folder id
     *
     * @return request object
     */
    public static CreateFileRequest createCreateFileRequest(String accessToken, String parentFolderId, String fileName) {
        CreateFileRequest createFileRequest = createCreateFileRequest();
        createFileRequest.setAccessToken(accessToken);
        createFileRequest.setParentFolderId(parentFolderId);
        createFileRequest.setFileName(fileName);
        return createFileRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateFolderRequest createCreateFolderRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.CreateFolderRequest");
        return (CreateFolderRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken access token
     * @param parentFolderId parent folder id
     * @param folderName folder name
     * @param share share flag
     *
     * @return request object
     */
    public static CreateFolderRequest createCreateFolderRequest(String accessToken, String parentFolderId, String folderName, boolean share) {
        CreateFolderRequest createFolderRequest = createCreateFolderRequest();
        createFolderRequest.setAccessToken(accessToken);
        createFolderRequest.setAuthToken(accessToken);
        createFolderRequest.setParentFolderId(parentFolderId);
        createFolderRequest.setFolderName(folderName);
        createFolderRequest.setShare(share);
        return createFolderRequest;
    }

    /**
     * create request object.
     *
     * @param apiKey
     * @param accessToken access token
     * @param parentFolderId parent folder id
     * @param folderName folder name
     * @param share share flag
     *
     * @return request object
     */
    public static CreateFolderRequest createCreateFolderRequest(String apiKey, String accessToken, String parentFolderId, String folderName, boolean share) {
        CreateFolderRequest createFolderRequest = createCreateFolderRequest();
        createFolderRequest.setAccessToken(accessToken);
        createFolderRequest.setAuthToken(accessToken);
        createFolderRequest.setParentFolderId(parentFolderId);
        createFolderRequest.setFolderName(folderName);
        createFolderRequest.setShare(share);
        return createFolderRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateGroupRequest createCreateGroupRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.CreateGroupRequest");
        return (CreateGroupRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param groupName
     *
     * @return request object
     */
    public static CreateGroupRequest createCreateGroupRequest(String accessToken, String groupName) {
        CreateGroupRequest createGroupRequest = createCreateGroupRequest();
        createGroupRequest.setAccessToken(accessToken);
        createGroupRequest.setName(groupName);
        return createGroupRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateMembershipRequest createCreateMembershipRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.CreateMembershipRequest");
        return (CreateMembershipRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param groupId
     * @param userId
     * @param role
     *
     * @return request object
     */
    public static CreateMembershipRequest createCreateMembershipRequest(String accessToken, String groupId, String userId, String role) {
        CreateMembershipRequest createMembershipRequest = createCreateMembershipRequest();
        createMembershipRequest.setAccessToken(accessToken);
        createMembershipRequest.setGroupId(groupId);
        createMembershipRequest.setUserId(userId);
        createMembershipRequest.setRole(role);
        return createMembershipRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateUserRequest createCreateUserRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.CreateUserRequest");
        return (CreateUserRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param userLogin
     * @param userName
     *
     * @return request object
     */
    public static CreateUserRequest createCreateUserRequest(String accessToken, String userLogin, String userName) {
        CreateUserRequest createUserRequest = createCreateUserRequest();
        createUserRequest.setAccessToken(accessToken);
        createUserRequest.setLogin(userLogin);
        createUserRequest.setName(userName);
        return createUserRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static DeleteCollaborationRequest createDeleteCollaborationRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.DeleteCollaborationRequest");
        return (DeleteCollaborationRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken access token
     * @param collaborationId
     *
     * @return request object
     */
    public static DeleteCollaborationRequest createDeleteCollaborationRequest(String accessToken, String collaborationId) {
        DeleteCollaborationRequest deleteCollaborationRequest = createDeleteCollaborationRequest();
        deleteCollaborationRequest.setAccessToken(accessToken);
        deleteCollaborationRequest.setCollaborationId(collaborationId);
        return deleteCollaborationRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static DeleteGroupRequest createDeleteGroupRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.DeleteGroupRequest");
        return (DeleteGroupRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param groupId
     * @return request object
     */
    public static DeleteGroupRequest createDeleteGroupRequest(String accessToken, String groupId) {
        DeleteGroupRequest deleteGroupRequest = createDeleteGroupRequest();
        deleteGroupRequest.setAccessToken(accessToken);
        deleteGroupRequest.setId(groupId);
        return deleteGroupRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static DeleteMembershipRequest createDeleteMembershipRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.DeleteMembershipRequest");
        return (DeleteMembershipRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param membershipId
     * @return request object
     */
    public static DeleteMembershipRequest createDeleteMembershipRequest(String accessToken, String membershipId) {
        DeleteMembershipRequest deleteMembershipRequest = createDeleteMembershipRequest();
        deleteMembershipRequest.setAccessToken(accessToken);
        deleteMembershipRequest.setId(membershipId);
        return deleteMembershipRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static DeleteRequest createDeleteRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.DeleteRequest");
        return (DeleteRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken access token
     * @param target target
     * @param targetId target id
     * @return request object
     */
    public static DeleteRequest createDeleteRequest(String accessToken, String target, String targetId) {
        DeleteRequest deleteRequest = createDeleteRequest(accessToken, target, targetId, false);
        return deleteRequest;
    }

    /**
     * create request object.
     *
     * @param apiKey
     * @param accessToken access token
     * @param target target
     * @param targetId target id
     * @return request object
     */
    public static DeleteRequest createDeleteRequest(String apiKey, String accessToken, String target, String targetId) {
        DeleteRequest deleteRequest = createDeleteRequest(accessToken, target, targetId, false);
        return deleteRequest;
    }

    /**
     * create request object.
     *
     * @param accessToken access token
     * @param target target
     * @param targetId target id
     * @param recursive
     * @return request object
     */
    public static DeleteRequest createDeleteRequest(String accessToken, String target, String targetId, boolean recursive) {
        DeleteRequest deleteRequest = createDeleteRequest();
        deleteRequest.setAccessToken(accessToken);
        deleteRequest.setAuthToken(accessToken);
        deleteRequest.setRecursive(recursive);
        deleteRequest.setTarget(target);
        deleteRequest.setTargetId(targetId);
        return deleteRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static DeleteUserRequest createDeleteUserRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.DeleteUserRequest");
        return (DeleteUserRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param userId
     *
     * @return request object
     */
    public static DeleteUserRequest createDeleteUserRequest(String accessToken, String userId) {
        DeleteUserRequest deleteUserRequest = createDeleteUserRequest();
        deleteUserRequest.setAccessToken(accessToken);
        deleteUserRequest.setId(userId);
        return deleteUserRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static DownloadRequest createDownloadRequest() {
        LOGGER.debug("starting createDownloadRequest()");
        Object obj = newInstanceOf("box4j.config.requestfactory.DownloadRequest");
        return (DownloadRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken access token
     * @param fileId file id
     * @param asFile if download as JAVA file object
     * @param inFile input JAVA File object
     * @return request object
     */
    public static DownloadRequest createDownloadRequest(String accessToken, String fileId, boolean asFile, File inFile) {
        LOGGER.debug("starting createDownloadRequest()");
        DownloadRequest downloadRequest = createDownloadRequest();
        downloadRequest.setAccessToken(accessToken);
        downloadRequest.setFileId(fileId);
        downloadRequest.setAsFile(asFile);
        downloadRequest.setInFile(inFile);
        return downloadRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static ExportTagsRequest createExportTagsRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.ExportTagsRequest");
        return (ExportTagsRequest) obj;
    }

    /**
     * create request object.
     *
     * @param apiKey API key
     * @param authToken auth token
     *
     * @return request object
     */
    public static ExportTagsRequest createExportTagsRequest(String apiKey, String authToken) {
        ExportTagsRequest exportTagsRequest = createExportTagsRequest();
        exportTagsRequest.setApiKey(apiKey);
        exportTagsRequest.setAuthToken(authToken);
        return exportTagsRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetAccountTreeRequest createGetAccountTreeRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.GetAccountTreeRequest");
        return (GetAccountTreeRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param folderId
     * @param params
     *
     * @return request object
     */
    public static GetAccountTreeRequest createGetAccountTreeRequest(String accessToken, String folderId, String[] params) {
        LOGGER.debug("starting createGetAccountTreeRequest()");
        GetAccountTreeRequest getAccountTreeRequest = createGetAccountTreeRequest();
        getAccountTreeRequest.setAccessToken(accessToken);
        LOGGER.debug("set access token to " + accessToken);
        getAccountTreeRequest.setAuthToken(accessToken);
        getAccountTreeRequest.setFolderId(folderId);
        getAccountTreeRequest.setParams(params);
        return getAccountTreeRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetAccessTokenRequest createGetAccessTokenRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.GetAccessTokenRequest");
        return (GetAccessTokenRequest) obj;
    }

    /**
     * create request object.
     *
     * @param apiKey API key
     * @param authorizationCode
     * @param isEnterprise
     * @param clientSecret
     *
     * @return request object
     */
    public static GetAccessTokenRequest createGetAccessTokenRequest(String apiKey, String authorizationCode, String clientSecret, boolean isEnterprise) {
        LOGGER.debug("starting createGetAccessToken()");
        GetAccessTokenRequest getAccessTokenRequest = createGetAccessTokenRequest();
        String configAuthorizationCode = BoxConfigurationManager.getConfigurationManager().getConfig().getProperty("box4j.config.api.oauth2_authorize_code");
        LOGGER.debug("configAuthorizationCode is " + configAuthorizationCode);
        getAccessTokenRequest.setApiKey(apiKey);
        getAccessTokenRequest.setAuthorizationCode(authorizationCode);
        getAccessTokenRequest.setClientSecret(clientSecret);
        getAccessTokenRequest.setIsEnterprise(isEnterprise);
        return getAccessTokenRequest;
    }

    /**
     * create request object.
     *
     * @param apiKey API key
     * @param authorizationCode
     * @param clientSecret
     *
     * @return request object
     */
    public static GetAccessTokenRequest createGetAccessTokenRequest(String apiKey, String authorizationCode, String clientSecret) {
        LOGGER.debug("starting createGetAccessTokenRequest()");
        GetAccessTokenRequest getAccessTokenRequest = createGetAccessTokenRequest();
        getAccessTokenRequest.setApiKey(apiKey);
        getAccessTokenRequest.setAuthorizationCode(authorizationCode);
        getAccessTokenRequest.setClientSecret(clientSecret);
        getAccessTokenRequest.setIsEnterprise(false);
        return getAccessTokenRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetAuthorizationRequest createGetAuthorizationRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.GetAuthorizationRequest");
        return (GetAuthorizationRequest) obj;
    }

    /**
     * create request object.
     *
     * @param apiKey API key
     *
     * @return request object
     */
    public static GetAuthorizationRequest createGetAuthorizationRequest(String apiKey) {
        GetAuthorizationRequest getAuthorizationRequest = createGetAuthorizationRequest();
        getAuthorizationRequest.setApiKey(apiKey);
        return getAuthorizationRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetAuthTokenRequest createGetAuthTokenRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.GetAuthTokenRequest");
        return (GetAuthTokenRequest) obj;
    }

    /**
     * create request object.
     *
     * @param apiKey API key
     * @param ticket the ticket
     *
     * @return request object
     */
    public static GetAuthTokenRequest createGetAuthTokenRequest(String apiKey, String ticket) {
        GetAuthTokenRequest getAuthTokenRequest = createGetAuthTokenRequest();
        getAuthTokenRequest.setApiKey(apiKey);
        getAuthTokenRequest.setTicket(ticket);
        return getAuthTokenRequest;
    }

    /**
     * create request object.
     *
     * @param apiKey API key
     * @param password
     * @param username
     *
     * @return request object
     */
    public static GetAuthTokenRequest createGetAuthTokenRequest(String apiKey, String password, String username) {
        LOGGER.debug("starting GetAuthTokenRequest()");
        GetAuthTokenRequest getAuthTokenRequest = createGetAuthTokenRequest();
        getAuthTokenRequest.setApiKey(apiKey);
        getAuthTokenRequest.setPassword(password);
        getAuthTokenRequest.setUsername(username);
        return getAuthTokenRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetCollaborationRequest createGetCollaborationRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.GetCollaborationRequest");
        return (GetCollaborationRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken accessToken
     * @param collaborationId
     *
     * @return request object
     */
    public static GetCollaborationRequest createGetCollaborationRequest(String accessToken, String collaborationId) {
        GetCollaborationRequest getCollaborationRequest = createGetCollaborationRequest();
        getCollaborationRequest.setAccessToken(accessToken);
        getCollaborationRequest.setCollaborationId(collaborationId);
        return getCollaborationRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetCollaborationsRequest createGetCollaborationsRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.GetCollaborationsRequest");
        return (GetCollaborationsRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken accessToken
     * @param folderId folderId
     *
     * @return request object
     */
    public static GetCollaborationsRequest createGetCollaborationsRequest(String accessToken, String folderId) {
        GetCollaborationsRequest getCollaborationsRequest = createGetCollaborationsRequest();
        getCollaborationsRequest.setAccessToken(accessToken);
        getCollaborationsRequest.setFolderId(folderId);
        return getCollaborationsRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetFileIdRequest createGetFileIdRequest() {
        LOGGER.debug("starting createGetFileIdRequest()");
        Object obj = newInstanceOf("box4j.config.requestfactory.GetFileIdRequest");
        return (GetFileIdRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param filePath
     *
     * @return request object
     */
    public static GetFileIdRequest createGetFileIdRequest(String accessToken, String filePath) {
        LOGGER.debug("starting createGetFileIdRequest()");
        GetFileIdRequest getFileIdRequest = createGetFileIdRequest();
        LOGGER.debug("created getFileIdRequest");
        getFileIdRequest.setAccessToken(accessToken);
        LOGGER.debug("set accessToken to " + accessToken);
        getFileIdRequest.setFilePath(filePath);
        LOGGER.debug("set filePath to " + filePath);
        return getFileIdRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetFileInfoRequest createGetFileInfoRequest() {
        LOGGER.debug("starting createGetFileInfoRequest()");
        Object obj = newInstanceOf("box4j.config.requestfactory.GetFileInfoRequest");
        return (GetFileInfoRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken access token
     * @param fileId file id
     *
     * @return request object
     */
    public static GetFileInfoRequest createGetFileInfoRequest(String accessToken, String fileId) {
        LOGGER.debug("starting createGetFileInfoRequest()");
        GetFileInfoRequest getFileInfoRequest = createGetFileInfoRequest();
        LOGGER.debug("created getFileInfoRequest");
        getFileInfoRequest.setAccessToken(accessToken);
        LOGGER.debug("set accessToken to " + accessToken);
        getFileInfoRequest.setFileId(fileId);
        LOGGER.debug("set fileId to " + fileId);
        return getFileInfoRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetFolderIdRequest createGetFolderIdRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.GetFolderIdRequest");
        return (GetFolderIdRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param folderPath
     *
     * @return request object
     */
    public static GetFolderIdRequest createGetFolderIdRequest(String accessToken, String folderPath) {
        GetFolderIdRequest getFolderIdRequest = createGetFolderIdRequest();
        getFolderIdRequest.setAccessToken(accessToken);
        getFolderIdRequest.setFolderPath(folderPath);
        return getFolderIdRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetFolderInfoRequest createGetFolderInfoRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.GetFolderInfoRequest");
        return (GetFolderInfoRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken access token
     * @param folderId
     *
     * @return request object
     */
    public static GetFolderInfoRequest createGetFolderInfoRequest(String accessToken, String folderId) {
        LOGGER.debug("starting createGetFolderInfoRequest()");
        GetFolderInfoRequest getFolderInfoRequest = createGetFolderInfoRequest();
        getFolderInfoRequest.setAccessToken(accessToken);
        getFolderInfoRequest.setFolderId(folderId);
        return getFolderInfoRequest;
    }

    /**
     * create request object.
     *
     * @param apiKey
     * @param accessToken access token
     * @param folderId
     *
     * @return request object
     */
    public static GetFolderInfoRequest createGetFolderInfoRequest(String apiKey, String accessToken, String folderId) {
        LOGGER.debug("starting createGetFolderInfoRequest()");
        GetFolderInfoRequest getFolderInfoRequest = createGetFolderInfoRequest();
        getFolderInfoRequest.setAccessToken(accessToken);
        getFolderInfoRequest.setFolderId(folderId);
        return getFolderInfoRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetFolderItemsRequest createGetFolderItemsRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.GetFolderItemsRequest");
        return (GetFolderItemsRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param folderId
     *
     * @return request object
     */
    public static GetFolderItemsRequest createGetFolderItemsRequest(String accessToken, String folderId) {
        GetFolderItemsRequest getFolderItemsRequest = createGetFolderItemsRequest();
        getFolderItemsRequest.setAccessToken(accessToken);
        getFolderItemsRequest.setFolderId(folderId);
        return getFolderItemsRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetFriendsRequest createGetFriendsRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.GetFriendsRequest");
        return (GetFriendsRequest) obj;
    }

    /**
     * create request object.
     *
     * @param apiKey API key
     * @param authToken auth token
     * @param params parameters
     *
     * @return request object
     */
    public static GetFriendsRequest createGetFriendsRequest(String apiKey, String authToken, String[] params) {
        GetFriendsRequest getFriendsRequest = createGetFriendsRequest();
        getFriendsRequest.setApiKey(apiKey);
        getFriendsRequest.setAuthToken(authToken);
        getFriendsRequest.setParams(params);
        return getFriendsRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetGroupRequest createGetGroupRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.GetGroupRequest");
        return (GetGroupRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param groupId
     * @return request object
     */
    public static GetGroupRequest createGetGroupRequest(String accessToken, String groupId) {
        GetGroupRequest getGroupRequest = createGetGroupRequest();
        getGroupRequest.setAccessToken(accessToken);
        getGroupRequest.setId(groupId);
        return getGroupRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetGroupsForUserRequest createGetGroupsForUserRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.GetGroupsForUserRequest");
        return (GetGroupsForUserRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param userId
     * @return request object
     */
    public static GetGroupsForUserRequest createGetGroupsForUserRequest(String accessToken, String userId) {
        GetGroupsForUserRequest getGroupsForUserRequest = createGetGroupsForUserRequest();
        getGroupsForUserRequest.setAccessToken(accessToken);
        getGroupsForUserRequest.setId(userId);
        return getGroupsForUserRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetMembershipRequest createGetMembershipRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.GetMembershipRequest");
        return (GetMembershipRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param membershipId
     * @return request object
     */
    public static GetMembershipRequest createGetMembershipRequest(String accessToken, String membershipId) {
        GetMembershipRequest getMembershipRequest = createGetMembershipRequest();
        getMembershipRequest.setAccessToken(accessToken);
        getMembershipRequest.setId(membershipId);
        return getMembershipRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetMembershipsForGroupRequest createGetMembershipsForGroupRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.GetMembershipsForGroupRequest");
        return (GetMembershipsForGroupRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param groupId
     * @return request object
     */
    public static GetMembershipsForGroupRequest createGetMembershipsForGroupRequest(String accessToken, String groupId) {
        GetMembershipsForGroupRequest getMembershipsForGroupRequest = createGetMembershipsForGroupRequest();
        getMembershipsForGroupRequest.setAccessToken(accessToken);
        getMembershipsForGroupRequest.setId(groupId);
        return getMembershipsForGroupRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetMembershipsForUserRequest createGetMembershipsForUserRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.GetMembershipsForUserRequest");
        return (GetMembershipsForUserRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param userId
     * @return request object
     */
    public static GetMembershipsForUserRequest createGetMembershipsForUserRequest(String accessToken, String userId) {
        GetMembershipsForUserRequest getMembershipsForUserRequest = createGetMembershipsForUserRequest();
        getMembershipsForUserRequest.setAccessToken(accessToken);
        getMembershipsForUserRequest.setId(userId);
        return getMembershipsForUserRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetPendingCollaborationsRequest createGetPendingCollaborationsRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.GetPendingCollaborationsRequest");
        return (GetPendingCollaborationsRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken accessToken
     *
     * @return request object
     */
    public static GetPendingCollaborationsRequest createGetPendingCollaborationsRequest(String accessToken) {
        GetPendingCollaborationsRequest getPendingCollaborationsRequest = createGetPendingCollaborationsRequest();
        getPendingCollaborationsRequest.setAccessToken(accessToken);
        return getPendingCollaborationsRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static RefreshAccessTokenRequest createRefreshAccessTokenRequest() {
        LOGGER.debug("starting createRefreshAccessTokenRequest()");
        Object obj = newInstanceOf("box4j.config.requestfactory.RefreshAccessTokenRequest");
        return (RefreshAccessTokenRequest) obj;
    }

    /**
     * create request object.
     *
     * @param apiKey API key
     * @param refreshToken
     * @param clientSecret
     * @param isEnterprise
     *
     * @return request object
     */
    public static RefreshAccessTokenRequest createRefreshAccessTokenRequest(String apiKey, String refreshToken, String clientSecret, boolean isEnterprise) {
        LOGGER.debug("starting createRefreshAccessTokenRequest()");
        RefreshAccessTokenRequest refreshAccessTokenRequest = createRefreshAccessTokenRequest();
        LOGGER.debug("created refreshAccessTokenRequest");
        LOGGER.debug("refreshToken is " + refreshToken);
        refreshAccessTokenRequest.setApiKey(apiKey);
        refreshAccessTokenRequest.setRefreshToken(refreshToken);
        refreshAccessTokenRequest.setClientSecret(clientSecret);
        refreshAccessTokenRequest.setIsEnterprise(isEnterprise);
        return refreshAccessTokenRequest;
    }

    /**
     * create request object.
     *
     * @param apiKey API key
     * @param refreshToken
     * @param clientSecret
     *
     * @return request object
     */
    public static RefreshAccessTokenRequest createRefreshAccessTokenRequest(String apiKey, String refreshToken, String clientSecret) {
        LOGGER.debug("starting createRefreshAccessTokenRequest()");
        RefreshAccessTokenRequest refreshAccessTokenRequest = createRefreshAccessTokenRequest();
        LOGGER.debug("created refreshAccessTokenRequest");
        LOGGER.debug("refreshToken is " + refreshToken);
        refreshAccessTokenRequest.setApiKey(apiKey);
        refreshAccessTokenRequest.setRefreshToken(refreshToken);
        refreshAccessTokenRequest.setClientSecret(clientSecret);
        refreshAccessTokenRequest.setIsEnterprise(false);
        return refreshAccessTokenRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetTicketRequest createGetTicketRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.GetTicketRequest");
        return (GetTicketRequest) obj;
    }

    /**
     * create request object.
     *
     * @param apiKey API key
     *
     * @return request object
     */
    public static GetTicketRequest createGetTicketRequest(String apiKey) {
        LOGGER.debug("starting createGetTicketRequest()");
        GetTicketRequest getTicketRequest = createGetTicketRequest();
        LOGGER.debug("created getTicketRequest");
        getTicketRequest.setApiKey(apiKey);
        LOGGER.debug("set apiKey");
        return getTicketRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetUserInfoRequest createGetUserInfoRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.GetUserInfoRequest");
        return (GetUserInfoRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken access token
     * @param userId user id
     *
     * @return request object
     */
    public static GetUserInfoRequest createGetUserInfoRequest(String accessToken, String userId) {
        LOGGER.debug("starting createGetFolderInfoRequest()");
        GetUserInfoRequest getUserInfoRequest = createGetUserInfoRequest();
        getUserInfoRequest.setAccessToken(accessToken);
        getUserInfoRequest.setUserId(userId);
        return getUserInfoRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetUsersRequest createGetUsersRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.GetUsersRequest");
        return (GetUsersRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken access token
     *
     * @return request object
     */
    public static GetUsersRequest createGetUsersRequest(String accessToken) {
        LOGGER.debug("starting createUsersRequest()");
        GetUsersRequest getUsersRequest = createGetUsersRequest();
        LOGGER.debug("created getUsersRequest");
        getUsersRequest.setAccessToken(accessToken);
        LOGGER.debug("set access token");
        return getUsersRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static LogoutRequest createLogoutRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.LogoutRequest");
        return (LogoutRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param clientId
     * @param clientSecret
     *
     * @return request object
     */
    public static LogoutRequest createLogoutRequest(String accessToken, String clientId, String clientSecret) {
        LOGGER.debug("starting createLogoutRequest()");
        LogoutRequest logoutRequest = createLogoutRequest();
        logoutRequest.setAccessToken(accessToken);
        logoutRequest.setClientId(clientId);
        logoutRequest.setClientSecret(clientSecret);
        return logoutRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static MoveRequest createMoveRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.MoveRequest");
        return (MoveRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken access token
     * @param target target
     * @param targetId target id
     * @param destinationId destination id
     *
     * @return request object
     */
    public static MoveRequest createMoveRequest(String accessToken, String target, String targetId, String destinationId) {
        MoveRequest moveRequest = createMoveRequest();
        moveRequest.setAccessToken(accessToken);
        moveRequest.setTarget(target);
        moveRequest.setTargetId(targetId);
        moveRequest.setDestinationId(destinationId);
        return moveRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static PrivateShareRequest createPrivateShareRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.PrivateShareRequest");
        return (PrivateShareRequest) obj;
    }

    /**
     * create request object.
     *
     * @param apiKey API key
     * @param authToken auth token
     * @param target target
     * @param targetId target id
     * @param emails email array
     * @param message message
     * @param nofity notify flag
     * @return request object
     */
    public static PrivateShareRequest createPrivateShareRequest(String apiKey, String authToken, String target, String targetId, String[] emails, String message, boolean nofity) {
        PrivateShareRequest privateShareRequest = createPrivateShareRequest();
        privateShareRequest.setApiKey(apiKey);
        privateShareRequest.setAuthToken(authToken);
        privateShareRequest.setTarget(target);
        privateShareRequest.setTargetId(targetId);
        privateShareRequest.setEmails(emails);
        privateShareRequest.setMessage(message);
        privateShareRequest.setNofity(nofity);
        return privateShareRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static PublicShareRequest createPublicShareRequest() {
        LOGGER.debug("starting createPublicShareRequest()");
        Object obj = newInstanceOf("box4j.config.requestfactory.PublicShareRequest");
        return (PublicShareRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param target target
     * @param password
     * @param targetId target id
     * @param message message of the item
     * @param emails email array
     * @return request object
     */
    public static PublicShareRequest createPublicShareRequest(String accessToken, String target, String targetId, String password, String message, String[] emails) {
        LOGGER.debug("starting createPublicShareRequest()");
        PublicShareRequest publicShareRequest = createPublicShareRequest();
        publicShareRequest.setAccessToken(accessToken);
        publicShareRequest.setAuthToken(accessToken);
        publicShareRequest.setTarget(target);
        publicShareRequest.setTargetId(targetId);
        publicShareRequest.setPassword(password);
        publicShareRequest.setMessage(message);
        publicShareRequest.setEmails(emails);
        return publicShareRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static PublicUnshareRequest createPublicUnshareRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.PublicUnshareRequest");
        return (PublicUnshareRequest) obj;
    }

    /**
     * create request object.
     *
     * @param apiKey API key
     * @param authToken auth token
     * @param target target
     * @param targetId target id
     * @return request object
     */
    public static PublicUnshareRequest createPublicUnshareRequest(String apiKey, String authToken, String target, String targetId) {
        PublicUnshareRequest publicUnshareRequest = createPublicUnshareRequest();
        publicUnshareRequest.setApiKey(apiKey);
        publicUnshareRequest.setAuthToken(authToken);
        publicUnshareRequest.setTarget(target);
        publicUnshareRequest.setTargetId(targetId);
        return publicUnshareRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static RegisterNewUserRequest createRegisterNewUserRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.RegisterNewUserRequest");
        return (RegisterNewUserRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken access token
     * @param loginName login name
     * @param name name
     * @return request object
     */
    public static RegisterNewUserRequest createRegisterNewUserRequest(String accessToken, String loginName, String name) {
        RegisterNewUserRequest registerNewUserRequest = createRegisterNewUserRequest();
        registerNewUserRequest.setAccessToken(accessToken);
        registerNewUserRequest.setLoginName(loginName);
        registerNewUserRequest.setName(name);
        return registerNewUserRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static RenameRequest createRenameRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.RenameRequest");
        return (RenameRequest) obj;
    }

    /**
     * create request object.
     *
     * @param apiKey API key
     * @param authToken auth token
     * @param target target
     * @param targetId target id
     * @param newName new name of item
     * @return request object
     */
    public static RenameRequest createRenameRequest(String apiKey, String authToken, String target, String targetId, String newName) {
        RenameRequest renameRequest = createRenameRequest();
        renameRequest.setApiKey(apiKey);
        renameRequest.setAuthToken(authToken);
        renameRequest.setTarget(target);
        renameRequest.setTargetId(targetId);
        renameRequest.setNewName(newName);
        return renameRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static RequestFriendsRequest createRequestFriendsRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.RequestFriendsRequest");
        return (RequestFriendsRequest) obj;
    }

    /**
     * create request object.
     *
     * @param apiKey API key
     * @param authToken auth token
     * @param emails email arrays
     * @param message message
     * @param params parameters
     * @return request object
     */
    public static RequestFriendsRequest createRequestFriendsRequest(String apiKey, String authToken, String[] emails, String message, String[] params) {
        RequestFriendsRequest requestFriendsRequest = createRequestFriendsRequest();
        requestFriendsRequest.setApiKey(apiKey);
        requestFriendsRequest.setAuthToken(authToken);
        requestFriendsRequest.setEmails(emails);
        requestFriendsRequest.setMessage(message);
        requestFriendsRequest.setParams(params);
        return requestFriendsRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static SearchRequest createSearchRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.SearchRequest");
        return (SearchRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken access token
     * @param query query
     * @param limit limit
     * @param offset offset
     * @return request object
     */
    public static SearchRequest createSearchRequest(String accessToken, String query, int limit, int offset) {
        SearchRequest searchRequest = createSearchRequest();
        searchRequest.setAccessToken(accessToken);
        searchRequest.setQuery(query);
        searchRequest.setLimit(limit);
        searchRequest.setOffset(offset);
        return searchRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static SetDescriptionRequest createSetDescriptionRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.SetDescriptionRequest");
        return (SetDescriptionRequest) obj;
    }

    /**
     * create request object.
     *
     * @param apiKey API key
     * @param authToken auth token
     * @param target target
     * @param targetId target id
     * @param description description
     * @return request object
     */
    public static SetDescriptionRequest createSetDescriptionRequest(String apiKey, String authToken, String target, String targetId, String description) {
        SetDescriptionRequest setDescriptionRequest = createSetDescriptionRequest();
        setDescriptionRequest.setApiKey(apiKey);
        setDescriptionRequest.setAuthToken(authToken);
        setDescriptionRequest.setTarget(target);
        setDescriptionRequest.setTargetId(targetId);
        setDescriptionRequest.setDescription(description);
        return setDescriptionRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UpdateCollaborationRequest createUpdateCollaborationRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.UpdateCollaborationRequest");
        return (UpdateCollaborationRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken access token
     * @param collaborationId
     * @param role
     *
     * @return request object
     */
    public static UpdateCollaborationRequest createUpdateCollaborationRequest(String accessToken, String collaborationId, String role) {
        LOGGER.debug("starting createUpdateCollaborationRequest()");
        UpdateCollaborationRequest updateCollaborationRequest = createUpdateCollaborationRequest();
        updateCollaborationRequest.setAccessToken(accessToken);
        updateCollaborationRequest.setId(collaborationId);
        updateCollaborationRequest.setRole(role);
        return updateCollaborationRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UpdateFileInfoRequest createUpdateFileInfoRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.UpdateFileInfoRequest");
        return (UpdateFileInfoRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken access token
     * @param fileId file id
     * @return request object
     */
    public static UpdateFileInfoRequest createUpdateFileInfoRequest(String accessToken, String fileId) {
        LOGGER.debug("starting createUpdateFileInfoRequest()");
        UpdateFileInfoRequest updateFileInfoRequest = createUpdateFileInfoRequest();
        updateFileInfoRequest.setAccessToken(accessToken);
        updateFileInfoRequest.setFileId(fileId);
        return updateFileInfoRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UpdateFolderInfoRequest createUpdateFolderInfoRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.UpdateFolderInfoRequest");
        return (UpdateFolderInfoRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken access token
     * @param folderId folder id
     * @return request object
     */
    public static UpdateFolderInfoRequest createUpdateFolderInfoRequest(String accessToken, String folderId) {
        LOGGER.debug("starting createUpdateFolderInfoRequest()");
        UpdateFolderInfoRequest updateFolderInfoRequest = createUpdateFolderInfoRequest();
        updateFolderInfoRequest.setAccessToken(accessToken);
        updateFolderInfoRequest.setFolderId(folderId);
        return updateFolderInfoRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UpdateGroupRequest createUpdateGroupRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.UpdateGroupRequest");
        return (UpdateGroupRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken access token
     * @param groupId
     * @param name
     *
     * @return request object
     */
    public static UpdateGroupRequest createUpdateGroupRequest(String accessToken, String groupId, String name) {
        LOGGER.debug("starting createUpdateUserRequest()");
        UpdateGroupRequest updateGroupRequest = createUpdateGroupRequest();
        updateGroupRequest.setAccessToken(accessToken);
        updateGroupRequest.setId(groupId);
        updateGroupRequest.setName(name);
        return updateGroupRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UpdateMembershipRequest createUpdateMembershipRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.UpdateMembershipRequest");
        return (UpdateMembershipRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken access token
     * @param membershipId membership id
     * @param role membership role
     *
     * @return request object
     */
    public static UpdateMembershipRequest createUpdateMembershipRequest(String accessToken, String membershipId, String role) {
        LOGGER.debug("starting createUpdateMembershipRequest()");
        UpdateMembershipRequest updateMembershipRequest = createUpdateMembershipRequest();
        updateMembershipRequest.setAccessToken(accessToken);
        updateMembershipRequest.setId(membershipId);
        updateMembershipRequest.setRole(role);
        return updateMembershipRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UpdateUserRequest createUpdateUserRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.UpdateUserRequest");
        return (UpdateUserRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken access token
     * @param userId user id
     * @param name
     *
     * @return request object
     */
    public static UpdateUserRequest createUpdateUserRequest(String accessToken, String userId, String name) {
        LOGGER.debug("starting createUpdateUserRequest()");
        UpdateUserRequest updateUserRequest = createUpdateUserRequest();
        updateUserRequest.setAccessToken(accessToken);
        updateUserRequest.setId(userId);
        updateUserRequest.setName(name);
        return updateUserRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static UploadRequest createUploadRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.UploadRequest");
        return (UploadRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param directoryName
     * @param asFile upload as file flag
     * @param parentFolderId parent folder id
     * @param nameValueMap key is file name, value could be file or byte array.
     * @return request object
     */
    public static UploadRequest createUploadRequest(String accessToken, boolean asFile, String directoryName, String parentFolderId, HashMap<String, File> nameValueMap) {
        LOGGER.debug("starting createUploadRequest()");
        UploadRequest uploadRequest = createUploadRequest();
        uploadRequest.setAccessToken(accessToken);
        uploadRequest.setAuthToken(accessToken);
        uploadRequest.setAsFile(asFile);
        uploadRequest.setDirectoryName(directoryName);
        uploadRequest.setFolderId(parentFolderId);
        uploadRequest.setDataMap(nameValueMap);
        return uploadRequest;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param asFile upload as file flag
     * @param parentFolderId parent folder id
     * @param nameValueMap key is file name, value could be file or byte array.
     * @return request object
     */
    public static UploadRequest createUploadRequest(String accessToken, boolean asFile, String parentFolderId, HashMap<String, File> nameValueMap) {
        LOGGER.debug("starting createUploadRequest()");
        UploadRequest uploadRequest = createUploadRequest();
        uploadRequest.setAccessToken(accessToken);
        uploadRequest.setAuthToken(accessToken);
        uploadRequest.setAsFile(asFile);
        uploadRequest.setFolderId(parentFolderId);
        uploadRequest.setDataMap(nameValueMap);
        return uploadRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static VerifyRegistrationEmailRequest createVerifyRegistrationEmailRequest() {
        Object obj = newInstanceOf("box4j.config.requestfactory.VerifyRegistrationEmailRequest");
        return (VerifyRegistrationEmailRequest) obj;
    }

    /**
     * create request object.
     *
     * @param apiKey API key
     * @param loginName login name
     * @return request object
     */
    public static VerifyRegistrationEmailRequest createVerifyRegistrationEmailRequest(String apiKey, String loginName) {
        VerifyRegistrationEmailRequest verifyRegistrationEmailRequest = createVerifyRegistrationEmailRequest();
        verifyRegistrationEmailRequest.setApiKey(apiKey);
        verifyRegistrationEmailRequest.setLoginName(loginName);
        return verifyRegistrationEmailRequest;
    }
}
