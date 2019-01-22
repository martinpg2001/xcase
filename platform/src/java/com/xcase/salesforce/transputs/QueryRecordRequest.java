/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.transputs;

/**
 *
 * @author martin
 */
public interface QueryRecordRequest extends SalesforceRecordRequest {

    public String getQueryString();

    public void setQueryString(String queryString);
}
