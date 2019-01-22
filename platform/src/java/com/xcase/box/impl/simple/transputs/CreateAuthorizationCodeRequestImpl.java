/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.transputs.CreateAuthorizationCodeRequest;

/**
 *
 * @author martin
 */
public class CreateAuthorizationCodeRequestImpl extends BoxRequestImpl implements CreateAuthorizationCodeRequest {

    private String password;
    private String redirectURI;
    private String username;

    /**
     * @return the accessibleBy
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the redirectURI
     */
    public String getRedirectURI() {
        return this.redirectURI;
    }

    /**
     * @param redirectURI the redirectURI to set
     */
    public void setRedirectURI(String redirectURI) {
        this.redirectURI = redirectURI;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return action name
     */
    public String getActionName() {
        return BoxConstant.ACTION_NAME_CREATE_COLLABORATION;
    }
}
