/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.transputs;

import com.xcase.sharepoint.objects.SharepointUser;

/**
 *
 * @author martin
 */
public interface GetAuthTokenResponse extends SharepointResponse {

    /**
     * @return the authToken
     */
    public String getAuthToken();

    /**
     * @param authToken the authToken to set
     */
    public void setAuthToken(String authToken);

    /**
     * @return the user
     */
    public SharepointUser getUser();

    /**
     * @param user the user to set
     */
    public void setUser(SharepointUser user);
}
