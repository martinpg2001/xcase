/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.transputs;

/**
 *
 * @author martin
 */
public interface SearchAccountRequest extends SalesforceRequest {

    public String getSearchString();

    public void setSearchString(String searchString);
}
