/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.factories;

import com.xcase.sharepoint.transputs.DeleteRequest;
import com.xcase.sharepoint.transputs.DownloadRequest;
import com.xcase.sharepoint.transputs.GetAccessTokenRequest;
import com.xcase.sharepoint.transputs.GetFileInfoRequest;
import com.xcase.sharepoint.transputs.GetFolderInfoRequest;
import com.xcase.sharepoint.transputs.UploadRequest;
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
}
