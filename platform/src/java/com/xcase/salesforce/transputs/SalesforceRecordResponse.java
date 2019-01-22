/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.transputs;

/**
 *
 * @author martin
 */
public interface SalesforceRecordResponse extends SalesforceResponse {

    /**
     * @return the recordType
     */
    String getRecordType();

    /**
     * @param recordType the recordType to set
     */
    void setRecordType(String recordType);
}
