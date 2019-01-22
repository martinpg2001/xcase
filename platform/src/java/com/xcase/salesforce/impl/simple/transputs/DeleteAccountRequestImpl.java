/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.transputs;

import com.xcase.salesforce.transputs.DeleteAccountRequest;

/**
 *
 * @author martin
 */
public class DeleteAccountRequestImpl extends SalesforceRequestImpl implements DeleteAccountRequest {

    private String accountId;

    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
