/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

/**
 *
 * @author martin
 */
public interface GetFolderInfoRequest extends BoxRequest {

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
}
