/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.transputs;

/**
 *
 * @author martin
 */
public interface GetRecordRequest extends SalesforceRecordRequest {

    public String getRecordId();

    public void setRecordId(String recordIdId);
}
