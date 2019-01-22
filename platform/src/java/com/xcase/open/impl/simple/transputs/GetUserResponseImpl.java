/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.UserData;
import com.xcase.open.transputs.GetUserResponse;

public class GetUserResponseImpl implements GetUserResponse {

    private UserData userData;

    @Override
    public UserData getUserData() {
        return this.userData;
    }

    @Override
    public void setUserData(UserData userData) {
        this.userData = userData;
    }

}
