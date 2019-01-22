/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.transputs;

import com.xcase.salesforce.transputs.SalesforceRecordRequest;

/**
 *
 * @author martin
 */
public class SalesforceRecordRequestImpl extends SalesforceRequestImpl implements SalesforceRecordRequest {

    private String recordType;

    /**
     * @return the recordType
     */
    public String getRecordType() {
        return recordType;
    }

    /**
     * @param recordType the accessToken to set
     */
    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }
}
