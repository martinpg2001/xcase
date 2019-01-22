/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.CreateAliasData;
import com.xcase.open.transputs.CreateClientAliasRequest;

public class CreateClientAliasRequestImpl extends OpenRequestImpl implements CreateClientAliasRequest {

    private String aliasName;
    private String entityId;
    private CreateAliasData createAliasData;

    @Override
    public String getAliasName() {
        return this.aliasName;
    }

    @Override
    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
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
    public CreateAliasData getCreateAliasData() {
        return this.createAliasData;
    }

    @Override
    public void setCreateAliasData(CreateAliasData createAliasData) {
        this.createAliasData = createAliasData;
    }

}
