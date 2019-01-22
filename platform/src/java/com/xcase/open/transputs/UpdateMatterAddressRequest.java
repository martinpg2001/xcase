/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.UpdateAddressData;

/**
 *
 * @author martin
 */
public interface UpdateMatterAddressRequest {

    String getEntityId();

    void setEntityId(String entityId);

    String getParentEntityId();

    void setParentEntityId(String parentEntityId);

    String getAddressType();

    void setAddressType(String addressType);

    UpdateAddressData getUpdateAddressData();

    void setUpdateAddressData(UpdateAddressData updateAddressData);

    String getRemoteId();

    void setRemoteId(String remoteId);
}
