/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.transputs.GetFileIdResponse;

/**
 *
 * @author martin
 */
public class GetFileIdResponseImpl extends BoxResponseImpl implements GetFileIdResponse {

    private String fileId;

    public String getFileId() {
        return this.fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
