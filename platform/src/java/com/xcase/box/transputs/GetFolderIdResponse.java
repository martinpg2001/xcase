/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

/**
 *
 * @author martin
 */
public interface GetFolderIdResponse extends BoxResponse {

    /**
     *
     * @return folderId
     */
    public String getFolderId();

    /**
     *
     * @param folderId
     */
    public void setFolderId(String folderId);
}
