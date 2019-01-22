/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.CreateOrReplaceUserEntitySecurityentityType;
import com.xcase.open.impl.simple.core.SecurityData;

/**
 *
 * @author martin
 */
public interface CreateOrReplaceUserClientSecurityRequest {

    CreateOrReplaceUserEntitySecurityentityType getCreateOrReplaceUserEntitySecurityentityType();

    void setCreateOrReplaceUserEntitySecurityentityType(CreateOrReplaceUserEntitySecurityentityType createOrReplaceUserEntitySecurityentityType);

    String getEntityId();

    void setEntityId(String entityId);

    SecurityData[] getSecurityDataArray();

    void setSecurityDataArray(SecurityData[] securityDataArray);

    String getParentEntityId();

    void setParentEntityId(String parentEntityId);
}
