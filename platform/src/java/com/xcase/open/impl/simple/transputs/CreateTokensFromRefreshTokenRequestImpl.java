/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.CreateTokensFromRefreshTokenRequest;

/**
 *
 * @author martin
 */
public class CreateTokensFromRefreshTokenRequestImpl extends OpenRequestImpl implements CreateTokensFromRefreshTokenRequest {
    private String baseUrl;
    private String clientId;
    private String clientSecret;
    private String redirectUrl;
    private String refreshToken;
    private String tenantId;
    private String tokenUrl;
    private String username;
    
    public String getRefreshToken() {
        return this.refreshToken;
    }
    
    public String getBaseUrl() {
        return this.baseUrl;
    }
    
    public String getClientId() {
        return this.clientId;
    }
    
    public String getClientSecret() {
        return this.clientSecret;
    }
    
    public String getRedirectUrl() {
        return this.redirectUrl;
    }
    
    public String getTenantId() {
        return this.tenantId;
    }
    
    public String getTokenUrl() {
        return this.tokenUrl;
    }
     
    public String getUsername() {
        return this.username;
    }
    
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
    
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
    
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
    
    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }    
}
