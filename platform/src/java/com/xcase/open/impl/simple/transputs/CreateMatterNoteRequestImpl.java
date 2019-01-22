/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.CreateNoteData;
import com.xcase.open.transputs.CreateMatterNoteRequest;

public class CreateMatterNoteRequestImpl implements CreateMatterNoteRequest {

    private String entityId;
    private String parentEntityId;
    private CreateNoteData createNoteData;

    @Override
    public String getEntityId() {
        return this.entityId;
    }

    @Override
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public CreateNoteData getCreateNoteData() {
        return this.createNoteData;
    }

    @Override
    public void setCreateNoteData(CreateNoteData createNoteData) {
        this.createNoteData = createNoteData;
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
