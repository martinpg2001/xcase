/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.UpdateAliasData;
import com.xcase.open.transputs.UpdatePartyAliasRequest;

public class UpdatePartyAliasRequestImpl implements UpdatePartyAliasRequest {

    private String aliasName;
    private String entityId;
    private UpdateAliasData updateAliasData;

    @Override
    public String getEntityId() {
        return this.entityId;
    }

    @Override
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public String getAliasName() {
        return this.aliasName;
    }

    @Override
    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    @Override
    public UpdateAliasData getUpdateAliasData() {
        return this.updateAliasData;
    }

    @Override
    public void setUpdateAliasData(UpdateAliasData updateAliasData) {
        this.updateAliasData = updateAliasData;
    }

}
