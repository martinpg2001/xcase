/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.CreateAttachmentData;

/**
 *
 * @author martin
 */
public interface CreateMatterAttachmentRequest {

    String getEntityId();

    void setEntityId(String entityId);

    CreateAttachmentData getCreateAttachmentData();

    void setCreateAttachmentData(CreateAttachmentData createAttachmentData);

    String getParentEntityId();

    void setParentEntityId(String parentEntityId);
}
