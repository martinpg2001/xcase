/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.objects.BoxShare;
import com.xcase.box.transputs.UpdateFileInfoRequest;

/**
 *
 * @author martin
 */
public class UpdateFileInfoRequestImpl extends BoxRequestImpl implements UpdateFileInfoRequest {

    private String fileDescription;
    private String fileId;
    private String fileName;
    private String fileParent;
    private BoxShare fileShare;

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileParent() {
        return this.fileParent;
    }

    public void setFileParent(String fileParent) {
        this.fileParent = fileParent;
    }

    public BoxShare getFileShare() {
        return this.fileShare;
    }

    public void setFileShare(BoxShare fileShare) {
        this.fileShare = fileShare;
    }

    /**
     * @return action name
     */
    public String getActionName() {
        return BoxConstant.ACTION_NAME_UPLOAD;
    }
}
