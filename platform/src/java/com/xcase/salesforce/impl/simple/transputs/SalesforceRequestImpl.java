/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.transputs;

import com.xcase.salesforce.transputs.SalesforceRequest;

/**
 *
 * @author martin
 */
public abstract class SalesforceRequestImpl implements SalesforceRequest {

    private String accessToken;
    private String clientId;

    /**
     * @return the accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param accessToken the accessToken to set
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return the clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @param clientId the apiKey to set
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
