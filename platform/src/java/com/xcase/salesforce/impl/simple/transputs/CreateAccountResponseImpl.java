/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.transputs;

import com.xcase.salesforce.transputs.CreateAccountResponse;

/**
 *
 * @author martin
 */
public class CreateAccountResponseImpl extends SalesforceResponseImpl implements CreateAccountResponse {

    private String accountId;

    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
