/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.UpdateGroupData;
import com.xcase.open.transputs.UpdateGroupRequest;

public class UpdateGroupRequestImpl implements UpdateGroupRequest {

    private String groupId;
    private UpdateGroupData updateGroupData;

    @Override
    public String getId() {
        return groupId;
    }

    @Override
    public void setId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public UpdateGroupData getUpdateGroupData() {
        return this.updateGroupData;
    }

    @Override
    public void setUpdateGroupData(UpdateGroupData updateGroupData) {
        this.updateGroupData = updateGroupData;
    }

}
