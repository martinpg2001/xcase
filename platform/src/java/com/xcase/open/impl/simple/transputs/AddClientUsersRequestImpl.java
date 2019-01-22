/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.ClientUserData;
import com.xcase.open.transputs.AddClientUsersRequest;

/**
 *
 * @author martin
 */
public class AddClientUsersRequestImpl extends OpenRequestImpl implements AddClientUsersRequest {

    private String clientId;
    private ClientUserData[] clientUserDataArray;

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public ClientUserData[] getClientUserDataArray() {
        return this.clientUserDataArray;
    }

    public void setClientUserDataArray(ClientUserData[] matterUserDataArray) {
        this.clientUserDataArray = clientUserDataArray;
    }
}
