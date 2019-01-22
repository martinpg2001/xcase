/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.CreateTokensFromUsernamePasswordRequest;

/**
 *
 * @author martinpg
 */
public class CreateTokensFromUsernamePasswordRequestImpl extends OpenRequestImpl implements CreateTokensFromUsernamePasswordRequest {
    private String baseUrl;
    private String password;
    private String tenantId;
    private String tokenUrl;
    private String username;
    
    public String getBaseUrl() {
        return this.baseUrl;
    }
    
    public String getPassword() {
        return this.password;
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
    
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    
    public void setPassword(String password) {
        this.password = password;
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
