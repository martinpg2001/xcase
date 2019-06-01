/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.transputs;

/**
 *
 * @author martin
 */
public interface GetRecordRequest extends SalesforceRecordRequest {

    String getRecordId();
    
    String getRecordUrl();

    void setRecordId(String recordId);

    void setRecordUrl(String url);
}
