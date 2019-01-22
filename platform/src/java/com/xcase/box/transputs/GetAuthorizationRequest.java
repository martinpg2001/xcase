/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

/**
 *
 * @author martin
 */
public interface GetAuthorizationRequest extends BoxRequest {

    /**
     * @return the ticket
     */
    public String getTicket();

    /**
     * @param ticket the ticket to set
     */
    public void setTicket(String ticket);
}
