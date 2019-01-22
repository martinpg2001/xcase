/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.GetEntitySecurityentityType;
import com.xcase.open.transputs.GetEntitySecurityRequest;

/**
 *
 * @author martin
 */
public class GetEntitySecurityRequestImpl extends OpenRequestImpl implements GetEntitySecurityRequest {

    private String entityId;
    private GetEntitySecurityentityType getEntitySecurityentityType;
    private String userIds;
    private String parentEntityId;

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public GetEntitySecurityentityType getEntitySecurityentityType() {
        return this.getEntitySecurityentityType;
    }

    public void setEntitySecurityentityType(GetEntitySecurityentityType getEntitySecurityentityType) {
        this.getEntitySecurityentityType = getEntitySecurityentityType;
    }

    public String getUserIds() {
        return this.userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public String getParentEntityId() {
        return this.parentEntityId;
    }

    public void setParentEntityId(String parentEntityId) {
        this.parentEntityId = parentEntityId;
    }
}
