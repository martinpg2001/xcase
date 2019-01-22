/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.transputs;

/**
 *
 * @author martin
 */
public interface CreateRecordResponse extends SalesforceRecordResponse {

    public String getRecordId();

    public void setRecordId(String recordId);
}
