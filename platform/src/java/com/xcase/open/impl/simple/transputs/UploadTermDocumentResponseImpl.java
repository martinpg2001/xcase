/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.UploadTermDocumentResponse;

/**
 *
 * @author martin
 */
public class UploadTermDocumentResponseImpl extends OpenResponseImpl implements UploadTermDocumentResponse {

    private int termDocumentId;

    public int getTermDocumentId() {
        return this.termDocumentId;
    }

    public void setTermDocumentId(int termDocumentId) {
        this.termDocumentId = termDocumentId;
    }
}
