/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

/**
 *
 * @author martin
 */
public interface GetFileIdResponse extends BoxResponse {

    /**
     *
     * @return fileId
     */
    public String getFileId();

    /**
     *
     * @param fileId
     */
    public void setFileId(String fileId);
}
