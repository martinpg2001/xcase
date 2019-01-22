/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.ChangePartyType;
import com.xcase.open.transputs.ChangePartyTypeRequest;

public class ChangePartyTypeRequestImpl extends OpenRequestImpl implements ChangePartyTypeRequest {

    private String partyId;
    private ChangePartyType changePartyType;

    @Override
    public String getPartyId() {
        return this.partyId;
    }

    @Override
    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    @Override
    public ChangePartyType getChangePartyType() {
        return this.changePartyType;
    }

    @Override
    public void setChangePartyType(ChangePartyType changePartyType) {
        this.changePartyType = changePartyType;
    }

}
