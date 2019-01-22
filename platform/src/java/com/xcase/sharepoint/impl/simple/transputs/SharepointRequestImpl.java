/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.transputs;

import com.xcase.sharepoint.transputs.SharepointRequest;

/**
 * @author martin
 *
 */
public abstract class SharepointRequestImpl implements SharepointRequest {

    /**
     * access token.
     */
    private String accessToken;

    /**
     * api key.
     */
    protected String apiKey;

    /**
     * domain.
     */
    private String domain;

    /**
     * username.
     */
    private String username;

    /**
     * password.
     */
    private String password;

    /**
     * @return the accessToken
     */
    public String getAccessToken() {
        return this.accessToken;
    }

    /**
     * @param accessToken the authToken to set
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return the apiKey
     */
    public String getApiKey() {
        return this.apiKey;
    }

    /**
     * @param apiKey the apiKey to set
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * @return the domain
     */
    public String getDomain() {
        return this.domain;
    }

    /**
     * @param domain the domain to set
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * @return the apiKey
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the apiKey
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
     * get action name.
     *
     * @return action name
     */
    public abstract String getActionName();
}
