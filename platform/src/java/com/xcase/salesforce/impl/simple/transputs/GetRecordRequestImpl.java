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
    
    private String recordUrl;

    public String getRecordId() {
        return this.recordId;
    }
    
    @Override
    public String getRecordUrl() {
        return recordUrl;
    }  

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    @Override
    public void setRecordUrl(String recordUrl) {
        this.recordUrl = recordUrl;        
    }
}
