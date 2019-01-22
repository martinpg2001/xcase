/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

/**
 *
 * @author martinpg
 */
public interface CreateAccessTokenResponse {

    public String getAccessToken();

    public int getExpiresIn();

    public String getRefreshToken();

    public String getStatus();

    public void setAccessToken(String accessToken);

    public void setExpiresIn(int expiresIn);

    public void setRefreshToken(String refreshToken);

    public void setStatus(String status);
}
