/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.UpdateWarningData;
import com.xcase.open.transputs.UpdateClientWarningRequest;

public class UpdateClientWarningRequestImpl implements UpdateClientWarningRequest {

    private int id;
    private String entityId;
    private UpdateWarningData updateWarningData;

    @Override
    public String getEntityId() {
        return this.entityId;
    }

    @Override
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public UpdateWarningData getUpdateWarningData() {
        return this.updateWarningData;
    }

    @Override
    public void setUpdateWarningData(UpdateWarningData updateWarningData) {
        this.updateWarningData = updateWarningData;
    }

}
