/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.DeleteEntitySecurityentityType;
import com.xcase.open.transputs.DeleteEntitySecurityRequest;

public class DeleteEntitySecurityRequestImpl implements DeleteEntitySecurityRequest {

    private DeleteEntitySecurityentityType deleteEntitySecurityentityType;
    private String entityId;
    private String parentEntityId;
    private String[] groupIds;

    @Override
    public DeleteEntitySecurityentityType getDeleteEntitySecurityentityType() {
        return this.deleteEntitySecurityentityType;
    }

    @Override
    public void setDeleteEntitySecurityentityType(DeleteEntitySecurityentityType deleteEntitySecurityentityType) {
        this.deleteEntitySecurityentityType = deleteEntitySecurityentityType;
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
    public String getParentEntityId() {
        return this.parentEntityId;
    }

    @Override
    public void setParentEntityId(String parentEntityId) {
        this.parentEntityId = parentEntityId;
    }

    @Override
    public String[] getGroupIds() {
        return this.groupIds;
    }

    @Override
    public void setGroupIds(String[] groupIds) {
        this.groupIds = groupIds;
    }

}
