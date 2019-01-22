/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.transputs;

import com.xcase.salesforce.transputs.GetRecordRequest;

/**
 *
 * @author martin
 */
public class GetRecordRequestImpl extends SalesforceRecordRequestImpl implements GetRecordRequest {

    private String recordId;

    public String getRecordId() {
        return this.recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
}
