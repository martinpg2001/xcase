/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.DeleteUserEntitySecurityentityType;
import com.xcase.open.transputs.DeleteUserEntitySecurityRequest;

public class DeleteUserEntitySecurityRequestImpl implements DeleteUserEntitySecurityRequest {

    private DeleteUserEntitySecurityentityType deleteUserEntitySecurityentityType;
    private String entityId;
    private String parentEntityId;
    private String[] userIds;

    @Override
    public DeleteUserEntitySecurityentityType getDeleteUserEntitySecurityentityType() {
        return this.deleteUserEntitySecurityentityType;
    }

    @Override
    public void setDeleteUserEntitySecurityentityType(DeleteUserEntitySecurityentityType deleteUserEntitySecurityentityType) {
        this.deleteUserEntitySecurityentityType = deleteUserEntitySecurityentityType;
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
    public String[] getUserIds() {
        return this.userIds;
    }

    @Override
    public void setUserIds(String[] userIds) {
        this.userIds = userIds;
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
