/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

/**
 *
 * @author martin
 */
public interface CreateFileRequest extends BoxRequest {

    /**
     * @return the content
     */
    public String getContent();

    /**
     * @param content the content to set
     */
    public void setContent(String content);

    /**
     * @return the parentFolderId
     */
    public String getParentFolderId();

    /**
     * @param parentFolderId the parentFolderId to set
     */
    public void setParentFolderId(String parentFolderId);

    /**
     * @return the fileName
     */
    public String getFileName();

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName);
}
