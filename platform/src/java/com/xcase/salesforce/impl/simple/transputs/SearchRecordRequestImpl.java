/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.transputs;

import com.xcase.salesforce.transputs.SearchRecordRequest;

/**
 *
 * @author martin
 */
public class SearchRecordRequestImpl extends SalesforceRecordRequestImpl implements SearchRecordRequest {

    private String searchString;

    public String getSearchString() {
        return this.searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
