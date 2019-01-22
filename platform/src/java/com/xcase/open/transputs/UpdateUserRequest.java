/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.UpdateUserData;

/**
 *
 * @author martin
 */
public interface UpdateUserRequest {

    String getUserId();

    void setUserId(String userId);

    UpdateUserData getUpdateUserData();

    void setUpdateUserData(UpdateUserData updateUserData);
}
