/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.GetEntitySecurityentityType;

/**
 *
 * @author martin
 */
public interface GetEntitySecurityRequest extends OpenRequest {

    String getEntityId();

    void setEntityId(String entityId);

    GetEntitySecurityentityType getEntitySecurityentityType();

    void setEntitySecurityentityType(GetEntitySecurityentityType getEntitySecurityentityType);

    String getUserIds();

    void setUserIds(String userIds);

    String getParentEntityId();

    void setParentEntityId(String parentEntityId);
}
