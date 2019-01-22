/**
 * Copyright 2017 Xcase. All rights reserved.
 */

package com.xcase.msgraph.transputs;

/**
 *
 * @author martin
 */
public interface GetAuthorizationCodeRequest {
    String getClientId();
    String getPassword();
    String getTenantId();
    String getUsername();
    void setClientId(String clientId);
    void setPassword(String password);
    void setTenantId(String tenantId);
    void setUsername(String username);
}
