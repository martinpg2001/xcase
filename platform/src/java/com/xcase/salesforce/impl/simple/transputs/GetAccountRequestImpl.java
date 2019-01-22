/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.transputs;

import com.xcase.salesforce.transputs.GetAccountRequest;

/**
 *
 * @author martin
 */
public class GetAccountRequestImpl extends SalesforceRecordRequestImpl implements GetAccountRequest {

    private String accountId;

    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
