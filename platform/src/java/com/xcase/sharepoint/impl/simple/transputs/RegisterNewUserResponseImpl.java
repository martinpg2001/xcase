/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.transputs;

import com.xcase.sharepoint.objects.SharepointUser;
import com.xcase.sharepoint.transputs.RegisterNewUserResponse;

/**
 * @author martin
 *
 */
public class RegisterNewUserResponseImpl extends SharepointResponseImpl implements
        RegisterNewUserResponse {

    /**
     * auth token.
     */
    private String authToken;

    /**
     * returned user object.
     */
    private SharepointUser user;

    /**
     * @return the authToken
     */
    public String getAuthToken() {
        return this.authToken;
    }

    /**
     * @param authToken the authToken to set
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * @return the user
     */
    public SharepointUser getUser() {
        return this.user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(SharepointUser user) {
        this.user = user;
    }
}
