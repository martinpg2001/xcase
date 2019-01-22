/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.transputs;

/**
 *
 * @author martin
 */
public interface CreateFolderRequest extends SharepointRequest {

    /**
     * @return the authToken
     */
    public String getAuthToken();

    /**
     * @param authToken the authToken to set
     */
    public void setAuthToken(String authToken);

    /**
     * @return the parentFolderId
     */
    public String getParentFolderId();

    /**
     * @param parentFolderId the parentFolderId to set
     */
    public void setParentFolderId(String parentFolderId);

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
}
