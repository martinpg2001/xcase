/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.UpdateWarningData;

/**
 *
 * @author martin
 */
public interface UpdateMatterWarningRequest {

    String getEntityId();

    void setEntityId(String entityId);

    int getId();

    void setId(int id);

    UpdateWarningData getUpdateWarningData();

    void setUpdateWarningData(UpdateWarningData updateWarningData);

    String getParentEntityId();

    void setParentEntityId(String parentEntityId);
}
