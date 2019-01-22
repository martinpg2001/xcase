/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.transputs;

import com.xcase.sharepoint.constant.SharepointConstant;
import com.xcase.sharepoint.transputs.GetFileInfoRequest;

/**
 * @author martin
 *
 */
public class GetFileInfoRequestImpl extends SharepointRequestImpl implements GetFileInfoRequest {

    /**
     * auth token.
     */
    private String authToken;

    /**
     * file id.
     */
    private String directoryId;

    /**
     * file id.
     */
    private String fileId;

    /**
     * site name.
     */
    private String site;

    /**
     * serverUrl name.
     */
    private String serverUrl;

    /**
     * @return the authToken
     */
    public String getAuthToken() {
        return this.authToken;
    }

    /**
     * @param authToken the authToken to set
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * @return the directoryId
     */
    public String getDirectoryId() {
        return this.directoryId;
    }

    /**
     * @param directoryId the directoryId to set
     */
    public void setDirectoryId(String directoryId) {
        this.directoryId = directoryId;
    }

    /**
     * @return the fileId
     */
    public String getFileId() {
        return this.fileId;
    }

    /**
     * @param fileId the fileId to set
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    /**
     * @return the site
     */
    public String getSite() {
        return this.site;
    }

    /**
     * @param site the directoryName to set
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     * @return the serverUrl
     */
    public String getServerUrl() {
        return this.serverUrl;
    }

    /**
     * @param serverUrl the serverUrl to set
     */
    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    /**
     * @return action name
     */
    public String getActionName() {
        return SharepointConstant.ACTION_NAME_GET_FILE_INFO;
    }
}
