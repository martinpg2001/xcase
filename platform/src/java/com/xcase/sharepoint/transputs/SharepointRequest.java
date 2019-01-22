/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.transputs;

/**
 *
 * @author martin
 */
public interface SharepointRequest {

    /**
     * @return the accessToken
     */
    public String getAccessToken();

    /**
     * @param accessToken the accessToken to set
     */
    public void setAccessToken(String accessToken);

    /**
     * @return the apiKey
     */
    String getApiKey();

    /**
     * @param apiKey the apiKey to set
     */
    void setApiKey(String apiKey);

    /**
     * @return the domain
     */
    String getDomain();

    /**
     * @param domain the domain to set
     */
    void setDomain(String domain);

    /**
     * @return the username
     */
    String getUsername();

    /**
     * @param username the username to set
     */
    void setUsername(String username);

    /**
     * @return the password
     */
    String getPassword();

    /**
     * @param password the domain to set
     */
    void setPassword(String password);

    /**
     * get action name.
     *
     * @return action name
     */
    String getActionName();
}
