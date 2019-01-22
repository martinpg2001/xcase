/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

/**
 *
 * @author martinpg
 */
public interface CreateTokensFromUsernamePasswordResponse {
    String getAccessToken();
    String getRefreshToken();
    void setAccessToken(String accessToken);
    void setRefreshToken(String refreshToken);      
}
