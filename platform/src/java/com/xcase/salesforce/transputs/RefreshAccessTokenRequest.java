/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.transputs;

/**
 *
 * @author martin
 */
public interface RefreshAccessTokenRequest extends SalesforceRequest {

    public String getRefreshToken();

    public void setRefreshToken(String refreshToken);

    public String getClientSecret();

    public void setClientSecret(String refreshToken);
}
