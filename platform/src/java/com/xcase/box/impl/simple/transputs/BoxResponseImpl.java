/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.transputs.BoxResponse;

/**
 * @author martin
 *
 */
public class BoxResponseImpl implements BoxResponse {

    /**
     * HTTP response code of response.
     */
    protected int responseCode;
    /**
     * message of response.
     */
    protected String message;
    /**
     * status of response.
     */
    protected String status = "SUCCESS";

    public int getResponseCode() {
        return this.responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
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
     * @return the status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
