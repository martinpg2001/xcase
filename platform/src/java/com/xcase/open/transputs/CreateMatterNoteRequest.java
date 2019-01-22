/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.CreateNoteData;

/**
 *
 * @author martin
 */
public interface CreateMatterNoteRequest {

    String getEntityId();

    void setEntityId(String entityId);

    CreateNoteData getCreateNoteData();

    void setCreateNoteData(CreateNoteData createNoteData);

    String getParentEntityId();

    void setParentEntityId(String parentEntityId);
}
