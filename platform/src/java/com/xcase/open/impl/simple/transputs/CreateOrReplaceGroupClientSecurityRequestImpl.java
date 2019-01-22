/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.CreateOrReplaceGroupEntitySecurityentityType;
import com.xcase.open.impl.simple.core.GroupSecurityData;
import com.xcase.open.transputs.CreateOrReplaceGroupClientSecurityRequest;

public class CreateOrReplaceGroupClientSecurityRequestImpl extends OpenRequestImpl implements CreateOrReplaceGroupClientSecurityRequest {

    private String entityId;
    private String parentEntityId;
    private CreateOrReplaceGroupEntitySecurityentityType createOrReplaceGroupEntitySecurityentityType;
    private GroupSecurityData[] groupSecurityDataArray;

    @Override
    public CreateOrReplaceGroupEntitySecurityentityType getCreateOrReplaceGroupEntitySecurityentityType() {
        return this.createOrReplaceGroupEntitySecurityentityType;
    }

    @Override
    public void setCreateOrReplaceGroupEntitySecurityentityType(CreateOrReplaceGroupEntitySecurityentityType createOrReplaceGroupEntitySecurityentityType) {
        this.createOrReplaceGroupEntitySecurityentityType = createOrReplaceGroupEntitySecurityentityType;
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
    public GroupSecurityData[] getGroupSecurityDataArray() {
        return this.groupSecurityDataArray;
    }

    @Override
    public void setGroupSecurityDataArray(GroupSecurityData[] groupSecurityDataArray) {
        this.groupSecurityDataArray = groupSecurityDataArray;
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
