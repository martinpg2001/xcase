/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.GroupData;
import com.xcase.open.transputs.GetGroupResponse;

public class GetGroupResponseImpl implements GetGroupResponse {

    private GroupData groupData;

    @Override
    public GroupData getGroupData() {
        return this.groupData;
    }

    @Override
    public void setGroupData(GroupData groupData) {
        this.groupData = groupData;
    }

}
