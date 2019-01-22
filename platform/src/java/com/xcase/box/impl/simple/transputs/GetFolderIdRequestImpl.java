/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.transputs.GetFolderIdRequest;

/**
 *
 * @author martin
 */
public class GetFolderIdRequestImpl extends BoxRequestImpl implements GetFolderIdRequest {

    private String folderPath;

    public String getFolderPath() {
        return this.folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    /**
     * @return action name
     */
    public String getActionName() {
        return BoxConstant.ACTION_NAME_GET_FOLDER_ID;
    }
}
