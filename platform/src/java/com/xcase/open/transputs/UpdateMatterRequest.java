/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.UpdateMatterData;

/**
 *
 * @author martin
 */
public interface UpdateMatterRequest {

    String getClientId();

    void setClientId(String clientId);

    String getMatterId();

    void setMatterId(String matterId);

    UpdateMatterData getUpdateMatterData();

    void setUpdateMatterData(UpdateMatterData updateMatterData);
}
