/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.CreateAttachmentData;
import com.xcase.open.transputs.CreatePartyAttachmentRequest;

public class CreatePartyAttachmentRequestImpl implements CreatePartyAttachmentRequest {

    private String entityId;
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

}
