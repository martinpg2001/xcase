/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.ClientUserData;

/**
 *
 * @author martin
 */
public interface SetClientUsersRequest {

    String getClientId();

    void setClientId(String clientId);

    ClientUserData[] getClientUserDataArray();

    void setClientUserDataArray(ClientUserData[] clientUserDataArray);
}
