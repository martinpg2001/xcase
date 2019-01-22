/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

/**
 *
 * @author martin
 */
public interface UpdateFolderInfoRequest extends BoxRequest {

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
}
