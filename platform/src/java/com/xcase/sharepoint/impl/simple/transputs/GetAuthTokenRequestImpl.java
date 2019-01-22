/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.transputs;

import com.xcase.sharepoint.constant.SharepointConstant;
import com.xcase.sharepoint.transputs.GetAuthTokenRequest;

/**
 * @author martin
 *
 */
public class GetAuthTokenRequestImpl extends SharepointRequestImpl implements GetAuthTokenRequest {

    /**
     * ticket.
     */
    private String ticket;

    /**
     * @return the ticket
     */
    public String getTicket() {
        return this.ticket;
    }

    /**
     * @param ticket the ticket to set
     */
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    /**
     * @return action name
     */
    public String getActionName() {
        return SharepointConstant.ACTION_NAME_GET_AUTH_TOKEN;
    }
}
