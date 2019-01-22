/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.UpdateRoleData;
import com.xcase.open.transputs.UpdateRoleRequest;

public class UpdateRoleRequestImpl implements UpdateRoleRequest {

    private String roleId;
    private UpdateRoleData updateRoleData;

    @Override
    public String getId() {
        return this.roleId;
    }

    @Override
    public void setId(String id) {
        this.roleId = roleId;
    }

    @Override
    public UpdateRoleData getUpdateRoleData() {
        return this.updateRoleData;
    }

    @Override
    public void setUpdateRoleData(UpdateRoleData updateRoleData) {
        this.updateRoleData = updateRoleData;
    }

}
