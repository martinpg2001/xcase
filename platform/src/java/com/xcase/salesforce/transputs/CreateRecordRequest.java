/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.transputs;

/**
 *
 * @author martin
 */
public interface CreateRecordRequest extends SalesforceRecordRequest {

    public String getRecordBody();

    public void setRecordBody(String recordBody);

    public String getRecordName();

    public void setRecordName(String recordName);
}
