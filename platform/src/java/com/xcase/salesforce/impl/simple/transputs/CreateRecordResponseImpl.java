/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.transputs;

import com.xcase.salesforce.transputs.CreateRecordResponse;

/**
 *
 * @author martin
 */
public class CreateRecordResponseImpl extends SalesforceRecordResponseImpl implements CreateRecordResponse {

    private String recordId;

    public String getRecordId() {
        return this.recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
}
