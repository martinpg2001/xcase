/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.CreateAttachmentData;
import com.xcase.open.transputs.CreateMatterAttachmentRequest;

public class CreateMatterAttachmentRequestImpl implements CreateMatterAttachmentRequest {

    private String entityId;
    private String parentEntityId;
    private CreateAttachmentData createAttachmentData;

    @Override
    public String getEntityId() {
        return this.entityId;
    }

    @Override
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public CreateAttachmentData getCreateAttachmentData() {
        return this.createAttachmentData;
    }

    @Override
    public void setCreateAttachmentData(CreateAttachmentData createAttachmentData) {
        this.createAttachmentData = createAttachmentData;
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
