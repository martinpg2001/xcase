/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.GetPartyRequest;

public class GetPartyRequestImpl implements GetPartyRequest {

    private String partyId;
    private String[] properties;

    @Override
    public String getPartyId() {
        return this.partyId;
    }

    @Override
    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    @Override
    public String[] getProperties() {
        return this.properties;
    }

}
