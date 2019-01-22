/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.transputs.GetFolderIdResponse;

/**
 *
 * @author martin
 */
public class GetFolderIdResponseImpl extends BoxResponseImpl implements GetFolderIdResponse {

    private String folderId;

    public String getFolderId() {
        return this.folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }
}
