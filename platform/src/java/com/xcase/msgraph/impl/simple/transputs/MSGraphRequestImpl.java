/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.transputs.MSGraphRequest;

/**
 *
 * @author martin
 */
public class MSGraphRequestImpl implements MSGraphRequest {
    private String accessToken;

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken)  {
        this.accessToken = accessToken;
    }
}
