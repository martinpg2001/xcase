/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.MatterUserData;
import com.xcase.open.transputs.AddMatterUsersRequest;

/**
 *
 * @author martin
 */
public class AddMatterUsersRequestImpl extends OpenRequestImpl implements AddMatterUsersRequest {

    private String clientId;
    private String matterId;
    private MatterUserData[] matterUserDataArray;

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getMatterId() {
        return this.matterId;
    }

    public void setMatterId(String matterId) {
        this.matterId = matterId;
    }

    public MatterUserData[] getMatterUserDataArray() {
        return this.matterUserDataArray;
    }

    public void setMatterUserDataArray(MatterUserData[] matterUserDataArray) {
        this.matterUserDataArray = matterUserDataArray;
    }
}
