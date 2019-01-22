/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.CreateWarningData;
import com.xcase.open.transputs.CreateClientWarningRequest;

public class CreateClientWarningRequestImpl extends OpenRequestImpl implements CreateClientWarningRequest {

    private String entityId;
    private CreateWarningData createWarningData;

    @Override
    public String getEntityId() {
        return this.entityId;
    }

    @Override
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public CreateWarningData getCreateWarningData() {
        return this.createWarningData;
    }

    @Override
    public void setCreateWarningData(CreateWarningData createWarningData) {
        this.createWarningData = createWarningData;
    }

}
