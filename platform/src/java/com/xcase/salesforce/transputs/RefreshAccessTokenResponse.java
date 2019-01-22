/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.transputs;

/**
 *
 * @author martin
 */
public interface RefreshAccessTokenResponse extends SalesforceResponse {

    public String getAccessToken();

    public void setAccessToken(String accessToken);

    public String getRefreshToken();

    public void setRefreshToken(String refreshToken);
}
