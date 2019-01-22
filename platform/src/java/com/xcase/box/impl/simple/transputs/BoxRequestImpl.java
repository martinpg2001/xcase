/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.transputs.BoxRequest;

/**
 * @author martin
 *
 */
public abstract class BoxRequestImpl implements BoxRequest {

    /**
     * access token.
     */
    private String accessToken;

    /**
     * api key.
     */
    protected String apiKey;

    /**
     * on-behalf-of email address
     */
    private String onBehalfOf;

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
     * @return the apiKey
     */
    public String getOnBehalfOf() {
        return this.onBehalfOf;
    }

    /**
     */
    public void setOnBehalfOf(String onBehalfOf) {
        this.onBehalfOf = onBehalfOf;
    }

    /**
     * get action name.
     *
     * @return action name
     */
    public abstract String getActionName();
}
