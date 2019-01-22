/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

/**
 *
 * @author martin
 */
public interface GetFolderIdRequest extends BoxRequest {

    /**
     *
     * @return folderPath
     */
    public String getFolderPath();

    /**
     *
     * @param folderPath
     */
    public void setFolderPath(String folderPath);
}
