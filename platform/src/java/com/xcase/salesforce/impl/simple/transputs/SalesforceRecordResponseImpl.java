/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.transputs;

import com.xcase.salesforce.transputs.SalesforceRecordResponse;

/**
 *
 * @author martin
 */
public class SalesforceRecordResponseImpl extends SalesforceResponseImpl implements SalesforceRecordResponse {

    private String recordType;

    public String getRecordType() {
        return this.recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }
}
