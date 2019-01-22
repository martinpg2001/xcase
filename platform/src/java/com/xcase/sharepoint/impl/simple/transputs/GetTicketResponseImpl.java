/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.transputs;

import com.xcase.sharepoint.transputs.GetTicketResponse;

/**
 * @author martin
 *
 */
public class GetTicketResponseImpl extends SharepointResponseImpl implements
        GetTicketResponse {

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
}
