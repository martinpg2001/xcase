/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.transputs;

import com.xcase.sharepoint.objects.SharepointUser;

/**
 *
 * @author martin
 */
public interface RefreshAccessTokenResponse extends SharepointResponse {

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
     * @param expiresIn the expiresIn to set
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
    public SharepointUser getUser();

    /**
     * @param user the user to set
     */
    public void setUser(SharepointUser user);
}
