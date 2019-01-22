/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.transputs.GetAccessTokenResponse;

/**
 *
 * @author martin
 */
public class GetAccessTokenResponseImpl extends MSGraphResponseImpl implements GetAccessTokenResponse {
    private String accessToken;

    public String getAccessToken() {
        return this.accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
