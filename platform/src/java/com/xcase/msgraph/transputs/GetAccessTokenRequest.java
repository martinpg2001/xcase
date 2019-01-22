/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.msgraph.transputs;

/**
 *
 * @author martin
 */
public interface GetAccessTokenRequest extends MSGraphRequest {
    String getClientId();
    String getClientSecret();
    String getTenantId();
    void setClientId(String clientId);
    void setClientSecret(String clientSecret);
    void setTenantId(String tenantId);
}
