/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.transputs;

/**
 *
 * @author martin
 */
public interface SalesforceRequest {

    /**
     * @return the accessToken
     */
    public String getAccessToken();

    /**
     * @param accessToken the accessToken to set
     */
    public void setAccessToken(String accessToken);

    /**
     * @return the clientId
     */
    String getClientId();

    /**
     * @param clientId the apiKey to set
     */
    void setClientId(String clientId);
}
