/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.transputs;

import com.xcase.salesforce.objects.SalesforceRecord;

/**
 *
 * @author martin
 */
public interface UpdateRecordRequest extends SalesforceRecordRequest {

    public String getRecordBody();

    public void setRecordBody(String recordBody);

    public String getRecordId();

    public void setRecordId(String recordId);

    public SalesforceRecord getRecord();

    public void setRecord(SalesforceRecord salesforceRecord);
}
