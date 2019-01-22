/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.CreateRoleResponse;

public class CreateRoleResponseImpl implements CreateRoleResponse {

    private int id;

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int roleID) {
        this.id = id;
    }

}
