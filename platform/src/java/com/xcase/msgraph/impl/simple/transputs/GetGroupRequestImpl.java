/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.transputs.GetGroupRequest;

/**
 *
 * @author martin
 */
public class GetGroupRequestImpl extends MSGraphRequestImpl implements GetGroupRequest {
    private String groupId;

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
