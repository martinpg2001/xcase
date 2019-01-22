/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.open.transputs;

/**
 *
 * @author martin
 */
public interface CreateAuthorizationCodeFromApplicationRequest {
    String getAuthorizationUrl();
    String getBaseUrl();
    String getClientId();
    String getClientSecret();
    String getPassword();
    String getRedirectUrl();
    String getTenantId();  
    String getTokenUrl();
    String getUsername();
    void setAuthorizationUrl(String authorizationUrl);
    void setBaseUrl(String baseUrl);
    void setClientId(String clientId);
    void setClientSecret(String clientSecret);
    void setPassword(String password);
    void setRedirectUrl(String redirectUrl);
    void setTenantId(String tenantId);
    void setTokenUrl(String tokenUrl);
    void setUsername(String username);
}
