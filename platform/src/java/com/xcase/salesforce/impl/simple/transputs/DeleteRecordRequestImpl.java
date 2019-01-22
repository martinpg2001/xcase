/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.transputs;

import com.xcase.salesforce.transputs.DeleteRecordRequest;

/**
 *
 * @author martin
 */
public class DeleteRecordRequestImpl extends SalesforceRecordRequestImpl implements DeleteRecordRequest {

    private String recordId;

    public String getRecordId() {
        return this.recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
}
