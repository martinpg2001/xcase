/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.CreateGroupData;
import com.xcase.open.transputs.CreateGroupRequest;

public class CreateGroupRequestImpl extends OpenRequestImpl implements CreateGroupRequest {

    private CreateGroupData createGroupData;

    @Override
    public CreateGroupData getCreateGroupData() {
        return this.createGroupData;
    }

    @Override
    public void setCreateGroupData(CreateGroupData createGroupData) {
        this.createGroupData = createGroupData;
    }

}
