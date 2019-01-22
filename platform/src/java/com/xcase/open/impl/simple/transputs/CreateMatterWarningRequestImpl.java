/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.CreateWarningData;
import com.xcase.open.transputs.CreateMatterWarningRequest;

public class CreateMatterWarningRequestImpl implements CreateMatterWarningRequest {

    private String entityId;
    private String parentEntityId;
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

    @Override
    public String getParentEntityId() {
        return this.parentEntityId;
    }

    @Override
    public void setParentEntityId(String parentEntityId) {
        this.parentEntityId = parentEntityId;
    }

}
