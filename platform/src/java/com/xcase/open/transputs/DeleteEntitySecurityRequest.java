/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.DeleteEntitySecurityentityType;

/**
 *
 * @author martin
 */
public interface DeleteEntitySecurityRequest {

    DeleteEntitySecurityentityType getDeleteEntitySecurityentityType();

    void setDeleteEntitySecurityentityType(DeleteEntitySecurityentityType deleteEntitySecurityentityType);

    String getEntityId();

    void setEntityId(String entityId);

    String getParentEntityId();

    void setParentEntityId(String parentEntityId);

    String[] getGroupIds();

    void setGroupIds(String[] groupIds);
}
