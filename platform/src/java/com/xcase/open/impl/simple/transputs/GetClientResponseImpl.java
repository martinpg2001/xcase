/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.ClientData;
import com.xcase.open.transputs.GetClientResponse;

/**
 *
 * @author martin
 */
public class GetClientResponseImpl extends OpenResponseImpl implements GetClientResponse {

    private ClientData clientData;

    public ClientData getClientData() {
        return this.clientData;
    }

    public void setClientData(ClientData clientData) {
        this.clientData = clientData;
    }
}
