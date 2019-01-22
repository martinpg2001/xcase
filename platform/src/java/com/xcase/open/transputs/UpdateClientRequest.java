/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.UpdateClientData;

/**
 *
 * @author martin
 */
public interface UpdateClientRequest {

    String getClientId();

    void setClientId(String clientId);

    UpdateClientData getUpdateClientData();

    void setUpdateClientData(UpdateClientData updateClientData);
}
