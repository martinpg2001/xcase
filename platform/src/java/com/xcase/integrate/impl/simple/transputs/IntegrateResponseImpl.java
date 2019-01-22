/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.common.impl.simple.transputs.CommonResponseImpl;
import com.xcase.integrate.transputs.IntegrateResponse;

/**
 *
 * @author martin
 */
public class IntegrateResponseImpl extends CommonResponseImpl implements IntegrateResponse {

    /**
     * message of response.
     */
    protected String message;
    /**
     * status of response.
     */
    protected String status = "SUCCESS";

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
