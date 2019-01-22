/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

import com.xcase.box.objects.BoxUser;

/**
 *
 * @author martin
 */
public interface RefreshAccessTokenResponse extends BoxResponse {

    /**
     * @return the accessToken
     */
    public String getAccessToken();

    /**
     * @param accessToken the accessToken to set
     */
    public void setAccessToken(String accessToken);

    /**
     * @return the expiresIn
     */
    public int getExpiresIn();

    /**
     */
    public void setExpiresIn(int expiresIn);

    /**
     * @return the refreshToken
     */
    public String getRefreshToken();

    /**
     * @param refreshToken the refreshToken to set
     */
    public void setRefreshToken(String refreshToken);

    /**
     * @return the tokenType
     */
    public String getTokenType();

    /**
     * @param tokenType the tokenType to set
     */
    public void setTokenType(String tokenType);

    /**
     * @return the user
     */
    public BoxUser getUser();

    /**
     * @param user the user to set
     */
    public void setUser(BoxUser user);
}
