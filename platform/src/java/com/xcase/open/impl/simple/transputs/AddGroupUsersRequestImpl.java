/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.AddGroupUsersRequest;

/**
 *
 * @author martin
 */
public class AddGroupUsersRequestImpl extends OpenRequestImpl implements AddGroupUsersRequest {

    private String groupId;
    private String[] userArray;

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String[] getUserArray() {
        return this.userArray;
    }

    public void setUserArray(String[] userArray) {
        this.userArray = userArray;
    }
}
