/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.UpdateAddressData;
import com.xcase.open.transputs.UpdateMatterAddressRequest;

public class UpdateMatterAddressRequestImpl implements UpdateMatterAddressRequest {

    private String addressType;
    private String entityId;
    private String parentEntityId;
    private String remoteId;
    private UpdateAddressData updateAddressData;

    @Override
    public String getEntityId() {
        return this.entityId;
    }

    @Override
    public void setEntityId(String entityId) {
        this.entityId = entityId;
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
    public String getAddressType() {
        return this.addressType;
    }

    @Override
    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    @Override
    public UpdateAddressData getUpdateAddressData() {
        return this.updateAddressData;
    }

    @Override
    public void setUpdateAddressData(UpdateAddressData updateAddressData) {
        this.updateAddressData = updateAddressData;
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
