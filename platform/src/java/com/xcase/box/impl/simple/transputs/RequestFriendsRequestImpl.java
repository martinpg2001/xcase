/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.transputs.RequestFriendsRequest;

/**
 * @author martin
 *
 */
public class RequestFriendsRequestImpl extends BoxRequestImpl implements RequestFriendsRequest {

    /**
     * auth token.
     */
    private String authToken;

    /**
     * email array.
     */
    private String[] emails;

    /**
     * message.
     */
    private String message;

    /**
     * parameters, could be 'box_auto_subscribe' or 'no_email'.
     */
    private String[] params;

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
     * @return the emails
     */
    public String[] getEmails() {
        return this.emails;
    }

    /**
     * @param emails the emails to set
     */
    public void setEmails(String[] emails) {
        this.emails = emails;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the params
     */
    public String[] getParams() {
        return this.params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(String[] params) {
        this.params = params;
    }

    /**
     * @return action name
     */
    public String getActionName() {
        return BoxConstant.ACTION_NAME_REQUEST_FRIENDS;
    }
}
