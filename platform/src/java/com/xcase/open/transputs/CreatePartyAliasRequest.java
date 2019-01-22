/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.CreateAliasData;

/**
 *
 * @author martin
 */
public interface CreatePartyAliasRequest {

    String getEntityId();

    void setEntityId(String entityId);

    CreateAliasData getCreateAliasData();

    void setCreateAliasData(CreateAliasData createAliasData);
}
