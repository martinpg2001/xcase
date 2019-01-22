/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.CreateAttachmentData;
import com.xcase.open.impl.simple.core.CreateAttachmententityType;

/**
 *
 * @author martin
 */
public interface CreateClientAttachmentRequest {

    CreateAttachmententityType getCreateAttachmententityType();

    void setCreateAttachmententityType(CreateAttachmententityType createAttachmententityType);

    String getEntityId();

    void setEntityId(String entityId);

    CreateAttachmentData getCreateAttachmentData();

    void setCreateAttachmentData(CreateAttachmentData createAttachmentData);

    String getParentEntityId();

    void setParentEntityId(String parentEntityId);
}
