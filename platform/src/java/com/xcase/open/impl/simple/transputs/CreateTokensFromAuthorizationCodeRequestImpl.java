/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.CreateTokensFromAuthorizationCodeRequest;

/**
 *
 * @author martin
 */
public class CreateTokensFromAuthorizationCodeRequestImpl extends OpenRequestImpl implements CreateTokensFromAuthorizationCodeRequest {
    private String authorizationCode;
    private String baseUrl;
    private String clientId;
    private String clientSecret;
    private String redirectUrl;
    private String tenantId;
    private String tokenUrl;
    private String username;
    
    public String getAuthorizationCode() {
        return this.authorizationCode;
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
    
    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
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
