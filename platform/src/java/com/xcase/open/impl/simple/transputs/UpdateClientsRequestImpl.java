/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.UpdateClientData;
import com.xcase.open.transputs.UpdateClientsRequest;

/**
 *
 * @author martin
 */
public class UpdateClientsRequestImpl extends OpenRequestImpl implements UpdateClientsRequest {

    private UpdateClientData[] updateClientDataArray;

    public UpdateClientData[] getUpdateClientDataArray() {
        return this.updateClientDataArray;
    }

    public void setUpdateClientDataArray(UpdateClientData[] updateClientDataArray) {
        this.updateClientDataArray = updateClientDataArray;
    }
}
