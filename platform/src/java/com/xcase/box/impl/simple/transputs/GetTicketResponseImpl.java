/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.transputs.GetTicketResponse;

/**
 * @author martin
 *
 */
public class GetTicketResponseImpl extends BoxResponseImpl implements GetTicketResponse {

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
