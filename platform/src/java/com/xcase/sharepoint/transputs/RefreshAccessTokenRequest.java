/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.transputs;

/**
 *
 * @author martin
 */
public interface RefreshAccessTokenRequest extends SharepointRequest {

    /**
     * @return the clientSecret
     */
    public String getClientSecret();

    /**
     * @param clientSecret the clientSecret to set
     */
    public void setClientSecret(String clientSecret);

    /**
     * @return the refreshToken
     */
    public String getRefreshToken();

    /**
     * @param refreshToken the refreshToken to set
     */
    public void setRefreshToken(String refreshToken);
}
