/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.AddRoleCapabilitiesRequest;

public class AddRoleCapabilitiesRequestImpl extends OpenRequestImpl implements AddRoleCapabilitiesRequest {

    private String roleId;
    private String[] capabilitiesArray;

    @Override
    public String getRoleId() {
        return this.roleId;
    }

    @Override
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String[] getCapabilitiesArray() {
        return this.capabilitiesArray;
    }

    @Override
    public void setCapabilitiesArray(String[] capabilitiesArray) {
        this.capabilitiesArray = capabilitiesArray;
    }

}
