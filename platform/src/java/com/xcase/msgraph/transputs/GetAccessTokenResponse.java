/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.msgraph.transputs;

/**
 *
 * @author martin
 */
public interface GetAccessTokenResponse extends MSGraphResponse {
    String getAccessToken();
    void setAccessToken(String accessToken);
}
