/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.CreateRoleData;
import com.xcase.open.transputs.CreateRoleRequest;

public class CreateRoleRequestImpl implements CreateRoleRequest {

    private CreateRoleData createRoleData;

    @Override
    public CreateRoleData getCreateRoleData() {
        return this.createRoleData;
    }

    @Override
    public void setCreateRoleData(CreateRoleData createRoleData) {
        this.createRoleData = createRoleData;
    }

}
