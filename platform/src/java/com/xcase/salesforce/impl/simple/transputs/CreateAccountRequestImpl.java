/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.transputs;

import com.xcase.salesforce.transputs.CreateAccountRequest;

/**
 *
 * @author martin
 */
public class CreateAccountRequestImpl extends SalesforceRequestImpl implements CreateAccountRequest {

    private String accountName;

    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
