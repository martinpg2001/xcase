/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.msgraph.transputs;

/**
 *
 * @author martin
 */
public interface MSGraphResponse {

    public int getResponseCode();

    public void setResponseCode(int responseCode);

    public String getMessage();

    public void setMessage(String message);

    public String getStatus();

    public void setStatus(String status);
}
