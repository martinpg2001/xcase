/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.CreateAddressData;
import com.xcase.open.impl.simple.core.CreateAddressentityType;

/**
 *
 * @author martin
 */
public interface CreateClientAddressRequest {

    CreateAddressentityType getCreateAddressentityType();

    void setCreateAddressentityType(CreateAddressentityType createAddressentityType);

    String getEntityId();

    void setEntityId(String entityId);

    CreateAddressData getCreateAddressData();

    void setCreateAddressData(CreateAddressData createAddressData);

    String getParentEntityId();

    void setParentEntityId(String parentEntityId);
}
