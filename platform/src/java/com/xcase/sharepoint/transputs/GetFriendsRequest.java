/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.transputs;

/**
 *
 * @author martin
 */
public interface GetFriendsRequest extends SharepointRequest {

    /**
     * @return the authToken
     */
    public String getAuthToken();

    /**
     * @param authToken the authToken to set
     */
    public void setAuthToken(String authToken);

    /**
     * @return the params
     */
    public String[] getParams();

    /**
     * @param params the params to set
     */
    public void setParams(String[] params);
}
