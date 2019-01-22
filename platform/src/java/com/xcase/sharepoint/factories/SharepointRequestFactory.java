/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.factories;

import com.xcase.sharepoint.transputs.AddToMySharepointRequest;
import com.xcase.sharepoint.transputs.AddToTagRequest;
import com.xcase.sharepoint.transputs.CreateFolderRequest;
import com.xcase.sharepoint.transputs.DeleteRequest;
import com.xcase.sharepoint.transputs.DownloadRequest;
import com.xcase.sharepoint.transputs.ExportTagsRequest;
import com.xcase.sharepoint.transputs.GetAccessTokenRequest;
import com.xcase.sharepoint.transputs.GetAccountTreeRequest;
import com.xcase.sharepoint.transputs.GetAuthTokenRequest;
import com.xcase.sharepoint.transputs.GetAuthorizationRequest;
import com.xcase.sharepoint.transputs.GetFileInfoRequest;
import com.xcase.sharepoint.transputs.GetFolderInfoRequest;
import com.xcase.sharepoint.transputs.GetFriendsRequest;
import com.xcase.sharepoint.transputs.GetTicketRequest;
import com.xcase.sharepoint.transputs.LogoutRequest;
import com.xcase.sharepoint.transputs.MoveRequest;
import com.xcase.sharepoint.transputs.PrivateShareRequest;
import com.xcase.sharepoint.transputs.PublicShareRequest;
import com.xcase.sharepoint.transputs.PublicUnshareRequest;
import com.xcase.sharepoint.transputs.RefreshAccessTokenRequest;
import com.xcase.sharepoint.transputs.RegisterNewUserRequest;
import com.xcase.sharepoint.transputs.RenameRequest;
import com.xcase.sharepoint.transputs.RequestFriendsRequest;
import com.xcase.sharepoint.transputs.SetDescriptionRequest;
import com.xcase.sharepoint.transputs.UploadRequest;
import com.xcase.sharepoint.transputs.VerifyRegistrationEmailRequest;
import java.lang.invoke.*;
import org.apache.logging.log4j.*;

/**
 * @author martin
 *
 */
public class SharepointRequestFactory extends BaseSharepointFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * create request object.
     *
     * @return request object
     */
    public static AddToMySharepointRequest createAddToMySharepointRequest() {
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.AddToMySharepointRequest");
        return (AddToMySharepointRequest) obj;
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
    public static AddToMySharepointRequest createAddToMySharepointRequest(String apiKey, String authToken, String fileId, String publicName,
            String folderId, String[] tags) {
        AddToMySharepointRequest addToMyBoxRequest = createAddToMySharepointRequest();
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
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.AddToTagRequest");
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
    public static CreateFolderRequest createCreateFolderRequest() {
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.CreateFolderRequest");
        return (CreateFolderRequest) obj;
    }

    /**
     * create request object.
     *
     * @param apiKey API key
     * @param authToken auth token
     * @param parentFolderId parent folder id
     * @param folderName folder name
     * @param share share flag
     *
     * @return request object
     */
    public static CreateFolderRequest createCreateFolderRequest(String apiKey, String authToken, String parentFolderId, String folderName, boolean share) {
        CreateFolderRequest createFolderRequest = createCreateFolderRequest();
        createFolderRequest.setApiKey(apiKey);
        createFolderRequest.setAccessToken(authToken);
        createFolderRequest.setAuthToken(authToken);
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
    public static DeleteRequest createDeleteRequest() {
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.DeleteRequest");
        return (DeleteRequest) obj;
    }

    /**
     * create request object.
     *
     * @param apiKey API key
     * @param accessToken
     * @param target target
     * @param targetId target id
     * @return request object
     */
    public static DeleteRequest createDeleteRequest(String apiKey, String accessToken, String target, String targetId) {
        DeleteRequest deleteRequest = createDeleteRequest(apiKey, accessToken, target, targetId, false);
        return deleteRequest;
    }

    /**
     * create request object.
     *
     * @param apiKey
     * @param accessToken
     * @param target target
     * @param targetId target id
     * @param recursive
     * @return request object
     */
    public static DeleteRequest createDeleteRequest(String apiKey, String accessToken, String target, String targetId, boolean recursive) {
        DeleteRequest deleteRequest = createDeleteRequest();
        deleteRequest.setApiKey(apiKey);
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
     * @param accessToken
     * @param domain
     * @param serverUrl
     * @param password
     * @param site
     * @param username
     * @param fileName
     * @param directoryName
     * @return request object
     */
    public static DeleteRequest createDeleteRequest(String accessToken, String serverUrl, String domain, String username, String password, String site, String directoryName, String fileName) {
        LOGGER.debug("starting createDeleteRequest()");
        DeleteRequest deleteRequest = createDeleteRequest();
        deleteRequest.setAccessToken(accessToken);
        deleteRequest.setServerUrl(serverUrl);
        deleteRequest.setDomain(domain);
        deleteRequest.setUsername(username);
        deleteRequest.setPassword(password);
        deleteRequest.setSite(site);
        deleteRequest.setDirectoryName(directoryName);
        deleteRequest.setFileName(fileName);
        return deleteRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static DownloadRequest createDownloadRequest() {
        LOGGER.debug("starting createDownloadRequest()");
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.DownloadRequest");
        return (DownloadRequest) obj;
    }

    /**
     * create request object.
     *
     * @param serverUrl serverUrl
     * @param domain credentials domain
     * @param username credentials username
     * @param password credentials password
     * @param site
     * @param directoryId
     * @param fileId
     * @return request object
     */
    public static DownloadRequest createDownloadRequest(String serverUrl, String domain, String username, String password, String site, String directoryId, String fileId) {
        LOGGER.debug("starting createDownloadRequest()");
        DownloadRequest downloadRequest = createDownloadRequest();
        downloadRequest.setServerUrl(serverUrl);
        LOGGER.debug("set serverUrl to " + serverUrl);
        downloadRequest.setDomain(domain);
        LOGGER.debug("set domain to " + domain);
        downloadRequest.setUsername(username);
        LOGGER.debug("set username to " + username);
        downloadRequest.setPassword(password);
        LOGGER.debug("set password to " + password);
        downloadRequest.setSite(site);
        LOGGER.debug("set site to " + site);
        downloadRequest.setDirectoryId(directoryId);
        LOGGER.debug("set directoryId to " + directoryId);
        downloadRequest.setFileId(fileId);
        LOGGER.debug("set fileId to " + fileId);
        return downloadRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static ExportTagsRequest createExportTagsRequest() {
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.ExportTagsRequest");
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
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.GetAccountTreeRequest");
        return (GetAccountTreeRequest) obj;
    }

    /**
     * create request object.
     *
     * @param apiKey API key
     * @param accessToken
     * @param folderId folder id
     * @param params parameters
     *
     * @return request object
     */
    public static GetAccountTreeRequest createGetAccountTreeRequest(String apiKey, String accessToken, String folderId, String[] params) {
        LOGGER.debug("starting createGetAccountTreeRequest()");
        GetAccountTreeRequest getAccountTreeRequest = createGetAccountTreeRequest();
        getAccountTreeRequest.setApiKey(apiKey);
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
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.GetAccessTokenRequest");
        return (GetAccessTokenRequest) obj;
    }

    /**
     * create request object.
     *
     * @param serverUrl
     * @param domain
     * @param username
     * @param site
     * @param password
     *
     * @return request object
     */
    public static GetAccessTokenRequest createGetAccessTokenRequest(String serverUrl, String domain, String username, String password, String site) {
        LOGGER.debug("starting createGetAccessToken()");
        GetAccessTokenRequest getAccessTokenRequest = createGetAccessTokenRequest();
        getAccessTokenRequest.setServerUrl(serverUrl);
        getAccessTokenRequest.setDomain(domain);
        getAccessTokenRequest.setUsername(username);
        getAccessTokenRequest.setPassword(password);
        getAccessTokenRequest.setSite(site);
        LOGGER.debug("set site to " + site);
        return getAccessTokenRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static RefreshAccessTokenRequest createRefreshAccessTokenRequest() {
        LOGGER.debug("starting createRefreshAccessTokenRequest()");
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.RefreshAccessTokenRequest");
        return (RefreshAccessTokenRequest) obj;
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
        return refreshAccessTokenRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetAuthTokenRequest createGetAuthTokenRequest() {
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.GetAuthTokenRequest");
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
     * @return request object
     */
    public static GetAuthorizationRequest createGetAuthorizationRequest() {
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.GetAuthorizationRequest");
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
    public static GetFileInfoRequest createGetFileInfoRequest() {
        LOGGER.debug("starting createGetFileInfoRequest()");
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.GetFileInfoRequest");
        return (GetFileInfoRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param webUrl
     * @param domain
     * @param username
     * @param fileId file id
     * @param password
     * @param directoryId
     * @param site
     *
     * @return request object
     */
    public static GetFileInfoRequest createGetFileInfoRequest(String accessToken, String webUrl, String domain, String username, String password, String site, String directoryId, String fileId) {
        LOGGER.debug("starting createGetFileInfoRequest()");
        GetFileInfoRequest getFileInfoRequest = createGetFileInfoRequest();
        LOGGER.debug("created getFileInfoRequest");
        getFileInfoRequest.setAccessToken(accessToken);
        LOGGER.debug("set accessToken to " + accessToken);
        getFileInfoRequest.setServerUrl(webUrl);
        LOGGER.debug("set webUrl to " + webUrl);
        getFileInfoRequest.setDomain(domain);
        LOGGER.debug("set domain to " + domain);
        getFileInfoRequest.setUsername(username);
        LOGGER.debug("set username to " + username);
        getFileInfoRequest.setPassword(password);
        LOGGER.debug("set password to " + password);
        getFileInfoRequest.setSite(site);
        LOGGER.debug("set site to " + site);
        getFileInfoRequest.setDirectoryId(directoryId);
        LOGGER.debug("set directoryId to " + directoryId);
        getFileInfoRequest.setFileId(fileId);
        LOGGER.debug("set fileId to " + fileId);
        return getFileInfoRequest;
    }

    /**
     * create request object.
     *
     * @param domain
     * @param username
     * @param password
     * @param directoryId
     * @param fileId file id
     *
     * @return request object
     */
    public static GetFileInfoRequest createGetFileInfoRequest(String domain, String username, String password, String directoryId, String fileId) {
        LOGGER.debug("starting createGetFileInfoRequest()");
        GetFileInfoRequest getFileInfoRequest = createGetFileInfoRequest();
        LOGGER.debug("created getFileInfoRequest");
        getFileInfoRequest.setDomain(domain);
        LOGGER.debug("set domain to " + domain);
        getFileInfoRequest.setUsername(username);
        LOGGER.debug("set username to " + username);
        getFileInfoRequest.setPassword(password);
        LOGGER.debug("set password to " + password);
        getFileInfoRequest.setDirectoryId(directoryId);
        LOGGER.debug("set directoryId to " + directoryId);
        getFileInfoRequest.setFileId(fileId);
        LOGGER.debug("set fileId to " + fileId);
        return getFileInfoRequest;
    }

    /**
     * create request object.
     *
     * @param domain
     * @param username
     * @param password
     * @param fileId file id
     *
     * @return request object
     */
    public static GetFileInfoRequest createGetFileInfoRequest(String domain, String username, String password, String fileId) {
        LOGGER.debug("starting createGetFileInfoRequest()");
        GetFileInfoRequest getFileInfoRequest = createGetFileInfoRequest(domain, username, password, null, fileId);
        return getFileInfoRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetFolderInfoRequest createGetFolderInfoRequest() {
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.GetFolderInfoRequest");
        return (GetFolderInfoRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param site
     * @param serverUrl
     * @param folderName
     *
     * @return request object
     */
    public static GetFolderInfoRequest createGetFolderInfoRequest(String accessToken, String serverUrl, String site, String folderName) {
        GetFolderInfoRequest getFolderInfoRequest = createGetFolderInfoRequest();
        getFolderInfoRequest.setAccessToken(accessToken);
        getFolderInfoRequest.setServerUrl(serverUrl);
        getFolderInfoRequest.setSite(site);
        getFolderInfoRequest.setFolderName(folderName);
        return getFolderInfoRequest;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param serverUrl
     * @param domain
     * @param password
     * @param site
     * @param folderName
     * @param username
     *
     * @return request object
     */
    public static GetFolderInfoRequest createGetFolderInfoRequest(String accessToken, String serverUrl, String domain, String username, String password, String site, String folderName) {
        GetFolderInfoRequest getFolderInfoRequest = createGetFolderInfoRequest();
        getFolderInfoRequest.setAccessToken(accessToken);
        getFolderInfoRequest.setServerUrl(serverUrl);
        getFolderInfoRequest.setDomain(domain);
        LOGGER.debug("set domain to " + domain);
        getFolderInfoRequest.setUsername(username);
        LOGGER.debug("set username to " + username);
        getFolderInfoRequest.setPassword(password);
        LOGGER.debug("set password to " + password);
        getFolderInfoRequest.setSite(site);
        getFolderInfoRequest.setFolderName(folderName);
        return getFolderInfoRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetFriendsRequest createGetFriendsRequest() {
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.GetFriendsRequest");
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
    public static GetTicketRequest createGetTicketRequest() {
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.GetTicketRequest");
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
    public static LogoutRequest createLogoutRequest() {
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.LogoutRequest");
        return (LogoutRequest) obj;
    }

    /**
     * create request object.
     *
     * @param apiKey API key
     * @param accessToken
     * @param clientSecret
     *
     * @return request object
     */
    public static LogoutRequest createLogoutRequest(String apiKey, String accessToken, String clientSecret) {
        LOGGER.debug("starting createLogoutRequest()");
        LogoutRequest logoutRequest = createLogoutRequest();
        logoutRequest.setApiKey(apiKey);
        logoutRequest.setAccessToken(accessToken);
        logoutRequest.setAuthToken(accessToken);
        logoutRequest.setClientSecret(clientSecret);
        return logoutRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static MoveRequest createMoveRequest() {
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.MoveRequest");
        return (MoveRequest) obj;
    }

    /**
     * create request object.
     *
     * @param apiKey API key
     * @param authToken auth token
     * @param target target
     * @param targetId target id
     * @param destinationId destination id
     *
     * @return request object
     */
    public static MoveRequest createMoveRequest(String apiKey, String authToken, String target, String targetId, String destinationId) {
        MoveRequest moveRequest = createMoveRequest();
        moveRequest.setApiKey(apiKey);
        moveRequest.setAuthToken(authToken);
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
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.PrivateShareRequest");
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
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.PublicShareRequest");
        return (PublicShareRequest) obj;
    }

    /**
     * create request object.
     *
     * @param apiKey API key
     * @param accessToken
     * @param targetId
     * @param target target
     * @param password password to protect the item
     * @param message message of the item
     * @param emails email array
     * @return request object
     */
    public static PublicShareRequest createPublicShareRequest(String apiKey, String accessToken, String target, String targetId, String password, String message, String[] emails) {
        LOGGER.debug("starting createPublicShareRequest()");
        PublicShareRequest publicShareRequest = createPublicShareRequest();
        publicShareRequest.setApiKey(apiKey);
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
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.PublicUnshareRequest");
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
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.RegisterNewUserRequest");
        return (RegisterNewUserRequest) obj;
    }

    /**
     * create request object.
     *
     * @param apiKey API key
     * @param loginName login name
     * @param password password
     * @return request object
     */
    public static RegisterNewUserRequest createRegisterNewUserRequest(String apiKey, String loginName, String password) {
        RegisterNewUserRequest registerNewUserRequest = createRegisterNewUserRequest();
        registerNewUserRequest.setApiKey(apiKey);
        registerNewUserRequest.setLoginName(loginName);
        registerNewUserRequest.setPassword(password);
        return registerNewUserRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static RenameRequest createRenameRequest() {
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.RenameRequest");
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
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.RequestFriendsRequest");
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
    public static SetDescriptionRequest createSetDescriptionRequest() {
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.SetDescriptionRequest");
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
    public static UploadRequest createUploadRequest() {
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.UploadRequest");
        return (UploadRequest) obj;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param serverUrl
     * @param asFile upload as file flag
     * @param domain
     * @param password
     * @param username
     * @param fileName
     * @param site
     * @param directoryName
     * @return request object
     */
    public static UploadRequest createUploadRequest(String accessToken, boolean asFile, String serverUrl, String domain, String username, String password, String site, String directoryName, String fileName) {
        LOGGER.debug("starting createUploadRequest()");
        UploadRequest uploadRequest = createUploadRequest();
        uploadRequest.setAccessToken(accessToken);
        uploadRequest.setAsFile(asFile);
        uploadRequest.setServerUrl(serverUrl);
        uploadRequest.setDomain(domain);
        uploadRequest.setUsername(username);
        uploadRequest.setPassword(password);
        uploadRequest.setSite(site);
        uploadRequest.setDirectoryName(directoryName);
        uploadRequest.setFileName(fileName);
        return uploadRequest;
    }

    /**
     * create request object.
     *
     * @param accessToken
     * @param serverUrl
     * @param asFile upload as file flag
     * @param directoryName
     * @param fileName
     * @param site
     * @return request object
     */
    public static UploadRequest createUploadRequest(String accessToken, boolean asFile, String serverUrl, String site, String directoryName, String fileName) {
        LOGGER.debug("starting createUploadRequest()");
        UploadRequest uploadRequest = createUploadRequest();
        uploadRequest.setAccessToken(accessToken);
        uploadRequest.setAsFile(asFile);
        uploadRequest.setServerUrl(serverUrl);
        uploadRequest.setSite(site);
        uploadRequest.setDirectoryName(directoryName);
        uploadRequest.setFileName(fileName);
        return uploadRequest;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static VerifyRegistrationEmailRequest createVerifyRegistrationEmailRequest() {
        Object obj = newInstanceOf("sharepoint4j.config.requestfactory.VerifyRegistrationEmailRequest");
        return (VerifyRegistrationEmailRequest) obj;
    }

    /**
     * create request object.
     *
     * @param apiKey API key
     * @param loginName login name
     * @return request object
     */
    public static VerifyRegistrationEmailRequest createVerifyRegistrationEmailRequest(
            String apiKey, String loginName) {
        VerifyRegistrationEmailRequest verifyRegistrationEmailRequest = createVerifyRegistrationEmailRequest();
        verifyRegistrationEmailRequest.setApiKey(apiKey);
        verifyRegistrationEmailRequest.setLoginName(loginName);
        return verifyRegistrationEmailRequest;
    }

}
