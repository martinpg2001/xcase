/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.DisableUserLoginRequest;

public class DisableUserLoginRequestImpl extends OpenRequestImpl implements DisableUserLoginRequest {

    private String userId;

    @Override
    public String getUserId() {
        return this.userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

}
