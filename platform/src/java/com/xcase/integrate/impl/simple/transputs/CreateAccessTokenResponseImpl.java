/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.transputs.CreateAccessTokenResponse;

/**
 *
 * @author martinpg
 */
public class CreateAccessTokenResponseImpl extends IntegrateResponseImpl implements CreateAccessTokenResponse {

    private String accessToken;
    private int expiresIn;
    private String refreshToken;
    private String status;

    public String getAccessToken() {
        return accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getStatus() {
        return status;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
