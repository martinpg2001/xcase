/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.DeleteEntityWarningentityType;
import com.xcase.open.transputs.DeleteClientWarningRequest;

public class DeleteClientWarningRequestImpl implements DeleteClientWarningRequest {

    private DeleteEntityWarningentityType deleteEntityWarningentityType;
    private String entityId;
    private int id;

    @Override
    public String getEntityId() {
        return this.entityId;
    }

    @Override
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public int getWarningId() {
        return this.id;
    }

    @Override
    public void setWarningId(int id) {
        this.id = id;
    }

}
