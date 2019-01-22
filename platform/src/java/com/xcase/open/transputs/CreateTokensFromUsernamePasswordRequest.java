/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

/**
 *
 * @author martinpg
 */
public interface CreateTokensFromUsernamePasswordRequest extends OpenRequest {
    String getBaseUrl();
    String getPassword();
    String getTenantId();  
    String getTokenUrl();
    String getUsername();
    void setBaseUrl(String baseUrl);
    void setPassword(String password);
    void setTenantId(String tenantId);
    void setTokenUrl(String tokenUrl);
    void setUsername(String username);
}
