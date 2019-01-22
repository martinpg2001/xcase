/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.CreateClientData;
import com.xcase.open.transputs.CreateClientsRequest;

/**
 *
 * @author martin
 */
public class CreateClientsRequestImpl extends OpenRequestImpl implements CreateClientsRequest {

    private CreateClientData[] createClientDataArray;

    public CreateClientData[] getCreateClientDataArray() {
        return this.createClientDataArray;
    }

    public void setCreateClientDataArray(CreateClientData[] createClientDataArray) {
        this.createClientDataArray = createClientDataArray;
    }
}
