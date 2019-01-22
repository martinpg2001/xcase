/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.DeleteAddressentityType;

/**
 *
 * @author martin
 */
public interface DeleteClientAddressRequest {

    DeleteAddressentityType getDeleteAddressentityType();

    void setDeleteAddressentityType(DeleteAddressentityType deleteAddressentityType);

    String getEntityId();

    void setEntityId(String entityId);

    String getAddressType();

    void setAddressType(String addressType);

    String getParentEntityId();

    void setParentEntityId(String parentEntityId);

    String getRemoteId();

    void setRemoteId(String remoteId);
}
