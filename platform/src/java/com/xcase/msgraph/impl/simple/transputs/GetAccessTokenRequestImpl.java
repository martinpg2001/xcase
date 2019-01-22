/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.transputs.GetAccessTokenRequest;

/**
 *
 * @author martin
 */
public class GetAccessTokenRequestImpl extends MSGraphRequestImpl implements GetAccessTokenRequest {
    private String clientId;
    private String clientSecret;
    private String tenantId;

    public String getClientId() {
        return this.clientId;
    }

    public String getClientSecret() {
        return this.clientSecret;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

}
