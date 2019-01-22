/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.transputs;

/**
 *
 * @author martin
 */
public interface RequestFriendsRequest extends SharepointRequest {

    /**
     * @return the authToken
     */
    public String getAuthToken();

    /**
     * @param authToken the authToken to set
     */
    public void setAuthToken(String authToken);

    /**
     * @return the emails
     */
    public String[] getEmails();

    /**
     * @param emails the emails to set
     */
    public void setEmails(String[] emails);

    /**
     * @return the message
     */
    public String getMessage();

    /**
     * @param message the message to set
     */
    public void setMessage(String message);

    /**
     * @return the params
     */
    public String[] getParams();

    /**
     * @param params the params to set
     */
    public void setParams(String[] params);
}
