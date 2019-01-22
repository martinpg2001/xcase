/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.transputs;

/**
 *
 * @author martin
 */
public interface GetAccessTokenResponse extends SalesforceResponse {

    public String getAccessToken();

    public void setAccessToken(String accessToken);

    public void setExpiresIn(int expiresIn);

    public String getRefreshToken();

    public void setRefreshToken(String refreshToken);

    public void setTokenType(String tokenType);

    public String getStatus();
}
