/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.transputs;

/**
 *
 * @author martin
 */
public interface SalesforceResponse {

    /**
     * get the status string.
     *
     * @return the status
     */
    public String getStatus();

    /**
     * set the status string.
     *
     * @param status the status to set
     */
    public void setStatus(String status);
}
