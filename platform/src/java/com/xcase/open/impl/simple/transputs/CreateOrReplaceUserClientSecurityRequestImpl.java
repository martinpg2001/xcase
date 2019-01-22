/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.CreateOrReplaceUserEntitySecurityentityType;
import com.xcase.open.impl.simple.core.SecurityData;
import com.xcase.open.transputs.CreateOrReplaceUserClientSecurityRequest;

public class CreateOrReplaceUserClientSecurityRequestImpl extends OpenRequestImpl implements CreateOrReplaceUserClientSecurityRequest {

    private String entityId;
    private String parentEntityId;
    private CreateOrReplaceUserEntitySecurityentityType createOrReplaceUserEntitySecurityentityType;
    private SecurityData[] securityDataArray;

    @Override
    public CreateOrReplaceUserEntitySecurityentityType getCreateOrReplaceUserEntitySecurityentityType() {
        return this.createOrReplaceUserEntitySecurityentityType;
    }

    @Override
    public void setCreateOrReplaceUserEntitySecurityentityType(CreateOrReplaceUserEntitySecurityentityType createOrReplaceUserEntitySecurityentityType) {
        this.createOrReplaceUserEntitySecurityentityType = createOrReplaceUserEntitySecurityentityType;
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
    public SecurityData[] getSecurityDataArray() {
        return this.securityDataArray;
    }

    @Override
    public void setSecurityDataArray(SecurityData[] securityDataArray) {
        this.securityDataArray = securityDataArray;
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
