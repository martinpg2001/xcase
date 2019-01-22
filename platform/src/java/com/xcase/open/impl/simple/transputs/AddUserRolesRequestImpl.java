/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.AddUserRolesRequest;

public class AddUserRolesRequestImpl extends OpenRequestImpl implements AddUserRolesRequest {

    private String userId;
    private String[] roleArray;

    @Override
    public String getUserId() {
        return this.userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String[] getRoleArray() {
        return this.roleArray;
    }

    @Override
    public void setRoleArray(String[] roleArray) {
        this.roleArray = roleArray;
    }

}
