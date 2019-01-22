/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.RoleData;
import com.xcase.open.transputs.GetRoleResponse;

public class GetRoleResponseImpl implements GetRoleResponse {

    private RoleData roleData;

    @Override
    public RoleData getRoleData() {
        return this.roleData;
    }

    @Override
    public void setRoleData(RoleData roleData) {
        this.roleData = roleData;
    }

}
