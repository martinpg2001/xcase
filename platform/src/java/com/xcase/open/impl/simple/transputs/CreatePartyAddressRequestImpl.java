/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.CreateAddressData;
import com.xcase.open.transputs.CreatePartyAddressRequest;

public class CreatePartyAddressRequestImpl implements CreatePartyAddressRequest {

    private String entityId;
    private CreateAddressData createAddressData;

    @Override
    public String getEntityId() {
        return this.entityId;
    }

    @Override
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public CreateAddressData getCreateAddressData() {
        return this.createAddressData;
    }

    @Override
    public void setCreateAddressData(CreateAddressData createAddressData) {
        this.createAddressData = createAddressData;
    }

}
