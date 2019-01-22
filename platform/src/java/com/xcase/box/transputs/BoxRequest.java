/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

/**
 *
 * @author martin
 */
public interface BoxRequest {

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
     * @return the onBehalfOf
     */
    public String getOnBehalfOf();

    /**
     * @param onBehalfOf the onBehalfOf to set
     */
    public void setOnBehalfOf(String onBehalfOf);

    /**
     * get action name.
     *
     * @return action name
     */
    String getActionName();
}
