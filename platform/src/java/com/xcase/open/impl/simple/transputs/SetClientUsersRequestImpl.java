/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.ClientUserData;
import com.xcase.open.transputs.SetClientUsersRequest;

public class SetClientUsersRequestImpl extends OpenRequestImpl implements SetClientUsersRequest {

    private String clientId;
    private ClientUserData[] clientUserDataArray;

    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public ClientUserData[] getClientUserDataArray() {
        return this.clientUserDataArray;
    }

    @Override
    public void setClientUserDataArray(ClientUserData[] clientUserDataArray) {
        this.clientUserDataArray = clientUserDataArray;
    }

}
