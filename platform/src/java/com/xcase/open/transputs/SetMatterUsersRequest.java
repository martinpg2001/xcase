/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.MatterUserData;

/**
 *
 * @author martin
 */
public interface SetMatterUsersRequest {

    String getClientId();

    void setClientId(String clientId);

    String getMatterId();

    void setMatterId(String matterId);

    MatterUserData[] getMatterUserDataArray();

    void setMatterUserDataArray(MatterUserData[] matterUserDataArray);
}
