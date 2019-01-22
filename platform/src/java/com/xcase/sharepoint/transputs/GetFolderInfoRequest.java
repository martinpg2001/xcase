/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.transputs;

/**
 *
 * @author martin
 */
public interface GetFolderInfoRequest extends SharepointRequest {

    /**
     * @return the authToken
     */
    public String getAuthToken();

    /**
     * @param authToken the authToken to set
     */
    public void setAuthToken(String authToken);

    /**
     * @return the folderId
     */
    public String getFolderId();

    /**
     * @param folderId the folderId to set
     */
    public void setFolderId(String folderId);

    /**
     * @return the folderName
     */
    public String getFolderName();

    /**
     * @param folderName the folderName to set
     */
    public void setFolderName(String folderName);

    /**
     * @return the share
     */
    public boolean isShare();

    /**
     * @param share the share to set
     */
    public void setShare(boolean share);

    /**
     * @return the site
     */
    public String getSite();

    /**
     * @param site the site to set
     */
    public void setSite(String site);

    /**
     * @return the serverUrl
     */
    public String getServerUrl();

    /**
     * @param serverUrl the serverUrl to set
     */
    public void setServerUrl(String serverUrl);
}
