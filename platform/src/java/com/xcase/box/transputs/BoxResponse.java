/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

/**
 *
 * @author martin
 */
public interface BoxResponse {

    /**
     *
     * @return responseCode
     */
    public int getResponseCode();

    /**
     *
     * @param responseCode
     */
    public void setResponseCode(int responseCode);

    /**
     *
     * @return message
     */
    public String getMessage();

    /**
     *
     * @param message
     */
    public void setMessage(String message);

    /**
     *
     * @return status
     */
    public String getStatus();

    /**
     *
     * @param status
     */
    public void setStatus(String status);
}
