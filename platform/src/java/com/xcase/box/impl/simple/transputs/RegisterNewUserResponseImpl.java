/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.objects.BoxUser;
import com.xcase.box.transputs.RegisterNewUserResponse;

/**
 * @author martin
 *
 */
public class RegisterNewUserResponseImpl extends BoxResponseImpl implements RegisterNewUserResponse {

    /**
     * auth token.
     */
    private String authToken;

    /**
     * returned user object.
     */
    private BoxUser user;

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
    public BoxUser getUser() {
        return this.user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(BoxUser user) {
        this.user = user;
    }
}
