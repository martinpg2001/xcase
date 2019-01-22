/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.GetUserRequest;

public class GetUserRequestImpl implements GetUserRequest {

    private String userId;
    private String[] properties;

    @Override
    public String getUserId() {
        return this.userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String[] getProperties() {
        return this.properties;
    }

    @Override
    public void setProperties(String[] properties) {
        this.properties = properties;
    }

}
