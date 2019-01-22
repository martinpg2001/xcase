/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.CreateUserData;
import com.xcase.open.transputs.CreateUserRequest;

public class CreateUserRequestImpl implements CreateUserRequest {

    private CreateUserData createUserData;

    @Override
    public CreateUserData getCreateUserData() {
        return this.createUserData;
    }

    @Override
    public void setCreateUserData(CreateUserData createUserData) {
        this.createUserData = createUserData;
    }

}
