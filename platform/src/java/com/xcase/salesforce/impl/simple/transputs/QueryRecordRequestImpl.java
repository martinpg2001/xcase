/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.transputs;

import com.xcase.salesforce.transputs.QueryRecordRequest;

/**
 *
 * @author martin
 */
public class QueryRecordRequestImpl extends SalesforceRecordRequestImpl implements QueryRecordRequest {

    private String queryString;

    public String getQueryString() {
        return this.queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
}
