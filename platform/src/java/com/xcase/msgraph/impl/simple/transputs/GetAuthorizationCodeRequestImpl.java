/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.transputs.GetAuthorizationCodeRequest;

/**
 *
 * @author martin
 */
public class GetAuthorizationCodeRequestImpl extends MSGraphRequestImpl implements GetAuthorizationCodeRequest {
    private String clientId;
    private String password;
    private String tenantId;
    private String username;
    
    public String getClientId() {
        return this.clientId;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public String getTenantId() {
        return this.tenantId;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    public void setPassword(String password)
             {
        this.password = password;
    }
    
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
}
