/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.transputs;

import com.xcase.salesforce.transputs.GetAccessTokenResponse;

/**
 *
 * @author martin
 */
public class GetAccessTokenResponseImpl extends SalesforceResponseImpl implements GetAccessTokenResponse {

    private int expiresIn;
    private String accessToken;
    private String refreshToken;
    private String tokenType;

    /**
     * get the accessToken string.
     *
     * @return the accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * set the accessToken string.
     *
     * @param accessToken the accessToken to set
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * get the tokenType string.
     *
     * @return the status
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * set the status string.
     *
     * @param tokenType the status to set
     */
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
