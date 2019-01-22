/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.PartyData;
import com.xcase.open.transputs.GetPartyResponse;

public class GetPartyResponseImpl implements GetPartyResponse {

    private PartyData partyData;

    @Override
    public PartyData getPartyData() {
        return this.partyData;
    }

    @Override
    public void setPartyData(PartyData partyData) {
        this.partyData = partyData;
    }

}
