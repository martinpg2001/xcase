/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.UpdateUserData;
import com.xcase.open.transputs.UpdateUserRequest;

/**
 *
 * @author martin
 */
public class UpdateUserRequestImpl extends OpenRequestImpl implements UpdateUserRequest {

    private String userId;
    private UpdateUserData updateUserData;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UpdateUserData getUpdateUserData() {
        return this.updateUserData;
    }

    public void setUpdateUserData(UpdateUserData updateUserData) {
        this.updateUserData = updateUserData;
    }
}
