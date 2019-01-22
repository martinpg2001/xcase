/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.transputs;

import com.xcase.sharepoint.constant.SharepointConstant;
import com.xcase.sharepoint.transputs.GetFolderInfoRequest;

/**
 *
 * @author martin
 */
public class GetFolderInfoRequestImpl extends SharepointRequestImpl implements GetFolderInfoRequest {

    /**
     * auth token.
     */
    private String authToken;

    /**
     * parent folder id.
     */
    private String folderId;

    /**
     * folder name.
     */
    private String folderName;

    /**
     * share this folder or not.
     */
    private boolean share;

    /**
     * directory name.
     */
    private String site;

    /**
     * file name.
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
     * @return the folderId
     */
    public String getFolderId() {
        return this.folderId;
    }

    /**
     * @param folderId the folderId to set
     */
    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    /**
     * @return the folderName
     */
    public String getFolderName() {
        return this.folderName;
    }

    /**
     * @param folderName the folderName to set
     */
    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    /**
     * @return the share
     */
    public boolean isShare() {
        return this.share;
    }

    /**
     * @param share the share to set
     */
    public void setShare(boolean share) {
        this.share = share;
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
        return SharepointConstant.ACTION_NAME_CREATE_FOLDER;
    }
}
