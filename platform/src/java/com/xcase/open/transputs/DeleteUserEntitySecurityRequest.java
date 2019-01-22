/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.DeleteUserEntitySecurityentityType;

/**
 *
 * @author martin
 */
public interface DeleteUserEntitySecurityRequest {

    DeleteUserEntitySecurityentityType getDeleteUserEntitySecurityentityType();

    void setDeleteUserEntitySecurityentityType(DeleteUserEntitySecurityentityType deleteUserEntitySecurityentityType);

    String getEntityId();

    void setEntityId(String entityId);

    String[] getUserIds();

    void setUserIds(String[] userIds);

    String getParentEntityId();

    void setParentEntityId(String parentEntityId);
}
