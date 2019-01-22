/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.transputs;

import com.xcase.salesforce.transputs.SearchAccountRequest;

/**
 *
 * @author martin
 */
public class SearchAccountRequestImpl extends SalesforceRequestImpl implements SearchAccountRequest {

    private String searchString;

    public String getSearchString() {
        return this.searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
