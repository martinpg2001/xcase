/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.transputs;

/**
 *
 * @author martin
 */
public interface GetAccessTokenRequest extends SalesforceRequest {

    /**
     * @return the authorizationCode
     */
    public String getAuthorizationCode();

    /**
     * @param authorizationCode the authorizationCode to set
     */
    public void setAuthorizationCode(String authorizationCode);

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
