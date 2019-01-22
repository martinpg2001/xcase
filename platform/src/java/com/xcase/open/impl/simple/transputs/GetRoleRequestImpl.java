/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.GetRoleRequest;

public class GetRoleRequestImpl implements GetRoleRequest {

    private String roleId;

    @Override
    public String getRoleId() {
        return this.roleId;
    }

    @Override
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

}
