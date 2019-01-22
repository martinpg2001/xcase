/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.UpdateClientData;
import com.xcase.open.transputs.UpdateClientRequest;

/**
 *
 * @author martin
 */
public class UpdateClientRequestImpl extends OpenRequestImpl implements UpdateClientRequest {

    private String clientId;
    private UpdateClientData updateClientData;

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public UpdateClientData getUpdateClientData() {
        return this.updateClientData;
    }

    public void setUpdateClientData(UpdateClientData updateClientData) {
        this.updateClientData = updateClientData;
    }
}
