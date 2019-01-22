/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.CreateAddressData;
import com.xcase.open.impl.simple.core.CreateAddressentityType;
import com.xcase.open.transputs.CreateClientAddressRequest;

public class CreateClientAddressRequestImpl extends OpenRequestImpl implements CreateClientAddressRequest {

    private String entityId;
    private String parentEntityId;
    private CreateAddressData createAddressData;
    private CreateAddressentityType createAddressentityType;

    @Override
    public CreateAddressentityType getCreateAddressentityType() {
        return this.createAddressentityType;
    }

    @Override
    public void setCreateAddressentityType(CreateAddressentityType createAddressentityType) {
        this.createAddressentityType = createAddressentityType;
    }

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

    @Override
    public String getParentEntityId() {
        return this.parentEntityId;
    }

    @Override
    public void setParentEntityId(String parentEntityId) {
        this.parentEntityId = parentEntityId;
    }

}
