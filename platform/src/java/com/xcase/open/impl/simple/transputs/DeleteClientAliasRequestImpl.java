/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.DeleteAliasentityType;
import com.xcase.open.transputs.DeleteClientAliasRequest;

public class DeleteClientAliasRequestImpl extends OpenRequestImpl implements DeleteClientAliasRequest {

    private String aliasName;
    private DeleteAliasentityType deleteAliasentityType;
    private String entityId;

    @Override
    public DeleteAliasentityType getDeleteAliasentityType() {
        return this.deleteAliasentityType;
    }

    @Override
    public void setDeleteAliasentityType(DeleteAliasentityType deleteAliasentityType) {
        this.deleteAliasentityType = deleteAliasentityType;
    }

    @Override
    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public String getAliasName() {
        return this.aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }
}
