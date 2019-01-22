/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.CreateWarningData;

/**
 *
 * @author martin
 */
public interface CreatePartyWarningRequest {

    String getEntityId();

    void setEntityId(String entityId);

    CreateWarningData getCreateWarningData();

    void setCreateWarningData(CreateWarningData createWarningData);
}
