/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.CreateAddressData;

/**
 *
 * @author martin
 */
public interface CreatePartyAddressRequest {

    String getEntityId();

    void setEntityId(String entityId);

    CreateAddressData getCreateAddressData();

    void setCreateAddressData(CreateAddressData createAddressData);
}
