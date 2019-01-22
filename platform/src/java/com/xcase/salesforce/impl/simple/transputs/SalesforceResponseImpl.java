/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.transputs;

import com.xcase.salesforce.transputs.SalesforceResponse;

/**
 *
 * @author martin
 */
public abstract class SalesforceResponseImpl implements SalesforceResponse {

    private String status;

    /**
     * get the status string.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * set the status string.
     *
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
