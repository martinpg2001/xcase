/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

/**
 *
 * @author martin
 */
public interface CreateTokensFromRefreshTokenResponse {
    String getAccessToken();
    String getRefreshToken();
    void setAccessToken(String accessToken);
    void setRefreshToken(String refreshToken);       
}
