/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.transputs;

import com.xcase.salesforce.objects.SalesforceRecord;
import com.xcase.salesforce.transputs.UpdateRecordRequest;

/**
 *
 * @author martin
 */
public class UpdateRecordRequestImpl extends SalesforceRecordRequestImpl implements UpdateRecordRequest {

    private SalesforceRecord salesforceRecord;
    private String recordBody;
    private String recordId;

    public String getRecordBody() {
        return this.recordBody;
    }

    public void setRecordBody(String recordBody) {
        this.recordBody = recordBody;
    }

    public SalesforceRecord getRecord() {
        return salesforceRecord;
    }

    public void setRecord(SalesforceRecord salesforceRecord) {
        this.salesforceRecord = salesforceRecord;
    }

    public String getRecordId() {
        return this.recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
}
