/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.transputs.UpdateFolderInfoRequest;

/**
 *
 * @author martin
 */
public class UpdateFolderInfoRequestImpl extends BoxRequestImpl implements UpdateFolderInfoRequest {

    private String folderId;
    private String folderName;

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    /**
     * @return action name
     */
    public String getActionName() {
        return BoxConstant.ACTION_NAME_UPLOAD;
    }
}
