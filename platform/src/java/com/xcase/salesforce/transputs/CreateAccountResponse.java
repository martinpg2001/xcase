/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.transputs;

import com.google.gson.JsonElement;

/**
 *
 * @author martin
 */
public interface CreateAccountResponse extends SalesforceResponse {

    public String getAccountId();

    public void setAccountId(String accountId);

}
