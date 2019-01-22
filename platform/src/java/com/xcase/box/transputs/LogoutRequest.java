/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

/**
 *
 * @author martin
 */
public interface LogoutRequest extends BoxRequest {

    /**
     * @return the authToken
     */
    public String getAuthToken();

    /**
     * @param authToken the authToken to set
     */
    public void setAuthToken(String authToken);

    /**
     * @return the clientId
     */
    public String getClientId();

    /**
     * @param clientId the clientId to set
     */
    public void setClientId(String clientId);

    /**
     * @return the clientSecret
     */
    public String getClientSecret();

    /**
     * @param clientSecret the clientSecret to set
     */
    public void setClientSecret(String clientSecret);
}
