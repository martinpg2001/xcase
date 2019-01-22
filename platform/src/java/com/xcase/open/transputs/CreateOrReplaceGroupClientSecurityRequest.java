/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.CreateOrReplaceGroupEntitySecurityentityType;
import com.xcase.open.impl.simple.core.GroupSecurityData;

/**
 *
 * @author martin
 */
public interface CreateOrReplaceGroupClientSecurityRequest {

    CreateOrReplaceGroupEntitySecurityentityType getCreateOrReplaceGroupEntitySecurityentityType();

    void setCreateOrReplaceGroupEntitySecurityentityType(CreateOrReplaceGroupEntitySecurityentityType createOrReplaceGroupEntitySecurityentityType);

    String getEntityId();

    void setEntityId(String entityId);

    GroupSecurityData[] getGroupSecurityDataArray();

    void setGroupSecurityDataArray(GroupSecurityData[] groupSecurityDataArray);

    String getParentEntityId();

    void setParentEntityId(String parentEntityId);
}
