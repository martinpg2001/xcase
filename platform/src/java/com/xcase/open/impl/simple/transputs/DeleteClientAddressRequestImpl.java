/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.DeleteAddressentityType;
import com.xcase.open.transputs.DeleteClientAddressRequest;

public class DeleteClientAddressRequestImpl extends OpenRequestImpl implements DeleteClientAddressRequest {

    private String addressType;
    private DeleteAddressentityType deleteAddressentityType;
    private String entityId;
    private String parentEntityId;
    private String remoteId;

    @Override
    public DeleteAddressentityType getDeleteAddressentityType() {
        return this.deleteAddressentityType;
    }

    @Override
    public void setDeleteAddressentityType(DeleteAddressentityType deleteAddressentityType) {
        this.deleteAddressentityType = deleteAddressentityType;
    }

    @Override
    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public String getAddressType() {
        return this.addressType;
    }

    @Override
    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    @Override
    public String getParentEntityId() {
        return this.parentEntityId;
    }

    @Override
    public void setParentEntityId(String parentEntityId) {
        this.parentEntityId = parentEntityId;
    }

    @Override
    public String getRemoteId() {
        return this.remoteId;
    }

    @Override
    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }
}
