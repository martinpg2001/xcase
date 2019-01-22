/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.transputs;

import com.xcase.salesforce.transputs.RefreshAccessTokenResponse;

/**
 *
 * @author martin
 */
public class RefreshAccessTokenResponseImpl extends SalesforceResponseImpl implements RefreshAccessTokenResponse {

    private String accessToken;
    private String refreshToken;

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
