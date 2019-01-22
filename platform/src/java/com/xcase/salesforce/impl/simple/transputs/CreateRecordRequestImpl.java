/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.transputs;

import com.xcase.salesforce.transputs.CreateRecordRequest;

/**
 *
 * @author martin
 */
public class CreateRecordRequestImpl extends SalesforceRecordRequestImpl implements CreateRecordRequest {

    private String recordBody;
    private String recordName;

    public String getRecordBody() {
        return this.recordBody;
    }

    public void setRecordBody(String recordBody) {
        this.recordBody = recordBody;
    }

    public String getRecordName() {
        return this.recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }
}
