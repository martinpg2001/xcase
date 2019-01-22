/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.SetGroupUsersRequest;

public class SetGroupUsersRequestImpl extends OpenRequestImpl implements SetGroupUsersRequest {

    private String groupId;
    private String[] groupUserArray;

    @Override
    public String getGroupId() {
        return this.groupId;
    }

    @Override
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String[] getUserArray() {
        return this.groupUserArray;
    }

    @Override
    public void setUserArray(String[] groupUserArray) {
        this.groupUserArray = groupUserArray;
    }

}
