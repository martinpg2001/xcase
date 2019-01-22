/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.CreateClientData;
import com.xcase.open.transputs.CreateClientRequest;

/**
 *
 * @author martin
 */
public class CreateClientRequestImpl extends OpenRequestImpl implements CreateClientRequest {

    private CreateClientData createClientData;

    public CreateClientData getCreateClientData() {
        return this.createClientData;
    }

    public void setCreateClientData(CreateClientData createClientData) {
        this.createClientData = createClientData;
    }
}
