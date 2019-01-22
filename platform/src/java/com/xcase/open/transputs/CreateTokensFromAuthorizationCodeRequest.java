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
public interface CreateTokensFromAuthorizationCodeRequest extends OpenRequest {
    String getAuthorizationCode();
    String getBaseUrl();
    String getClientId();
    String getClientSecret();
    String getRedirectUrl();
    String getTenantId();  
    String getTokenUrl();
    void setAuthorizationCode(String authorizationCode);
    void setBaseUrl(String baseUrl);
    void setClientId(String clientId);
    void setClientSecret(String clientSecret);
    void setRedirectUrl(String redirectUrl);
    void setTenantId(String tenantId);
    void setTokenUrl(String tokenUrl);    
}
