/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.MatterUserData;
import com.xcase.open.transputs.SetMatterUsersRequest;

public class SetMatterUsersRequestImpl extends OpenRequestImpl implements SetMatterUsersRequest {

    private String clientId;
    private String matterId;
    private MatterUserData[] matterUserDataArray;

    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String getMatterId() {
        return this.matterId;
    }

    @Override
    public void setMatterId(String matterId) {
        this.matterId = matterId;
    }

    @Override
    public MatterUserData[] getMatterUserDataArray() {
        return this.matterUserDataArray;
    }

    @Override
    public void setMatterUserDataArray(MatterUserData[] matterUserDataArray) {
        this.matterUserDataArray = matterUserDataArray;
    }

}
