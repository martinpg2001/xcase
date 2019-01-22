/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

import com.xcase.box.objects.BoxShare;

/**
 *
 * @author martin
 */
public interface UpdateFileInfoRequest extends BoxRequest {

    /**
     * @return the fileDescription
     */
    public String getFileDescription();

    /**
     * @param fileDescription the fileDescription to set
     */
    public void setFileDescription(String fileDescription);

    /**
     * @return the fileId
     */
    public String getFileId();

    /**
     * @param fileId the fileId to set
     */
    public void setFileId(String fileId);

    /**
     * @return the fileName
     */
    public String getFileName();

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName);

    /**
     * @return the fileParent
     */
    public String getFileParent();

    /**
     * @param fileParent the fileParent to set
     */
    public void setFileParent(String fileParent);

    /**
     * @return the fileShare
     */
    public BoxShare getFileShare();

    /**
     * @param fileShare the fileShare to set
     */
    public void setFileShare(BoxShare fileShare);
}
