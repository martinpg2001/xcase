/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.RemoveGroupUsersRequest;

public class RemoveGroupUsersRequestImpl extends OpenRequestImpl implements RemoveGroupUsersRequest {

    private String groupId;
    private String[] userArray;

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
        return this.userArray;
    }

    @Override
    public void setUserArray(String[] userArray) {
        this.userArray = userArray;
    }

}
