/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.CreateTokensFromAuthorizationCodeResponse;

/**
 *
 * @author martin
 */
public class CreateTokensFromAuthorizationCodeResponseImpl extends OpenResponseImpl implements CreateTokensFromAuthorizationCodeResponse {
    private String accessToken;
    private String refreshToken;
    
    public String getAccessToken() {
        return this.accessToken;
    }
    
    public String getRefreshToken() {
        return this.refreshToken;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
