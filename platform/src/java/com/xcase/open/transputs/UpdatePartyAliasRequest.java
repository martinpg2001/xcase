/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.UpdateAliasData;

/**
 *
 * @author martin
 */
public interface UpdatePartyAliasRequest {

    String getEntityId();

    void setEntityId(String entityId);

    String getAliasName();

    void setAliasName(String aliasName);

    UpdateAliasData getUpdateAliasData();

    void setUpdateAliasData(UpdateAliasData updateAliasData);
}
