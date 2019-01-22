/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.UpdatePartyData;
import com.xcase.open.transputs.UpdatePartyRequest;

/**
 *
 * @author martin
 */
public class UpdatePartyRequestImpl extends OpenRequestImpl implements UpdatePartyRequest {

    private String partyId;
    private UpdatePartyData updatePartData;

    public String getPartyId() {
        return this.partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public UpdatePartyData getUpdatePartyData() {
        return this.updatePartData;
    }

    public void setUpdatePartyData(UpdatePartyData updatePartyData) {
        this.updatePartData = updatePartyData;
    }
}
