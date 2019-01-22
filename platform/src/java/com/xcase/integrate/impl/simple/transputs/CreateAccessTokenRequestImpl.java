/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.transputs.CreateAccessTokenRequest;

/**
 *
 * @author martinpg
 */
public class CreateAccessTokenRequestImpl extends IntegrateRequestImpl implements CreateAccessTokenRequest {

    private String password;

    private String username;

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
