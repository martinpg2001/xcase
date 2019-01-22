/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.DeactivateUserRequest;

public class DeactivateUserRequestImpl extends OpenRequestImpl implements DeactivateUserRequest {

    private String userId;

    @Override
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
