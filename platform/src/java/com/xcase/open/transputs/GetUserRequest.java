/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

/**
 *
 * @author martin
 */
public interface GetUserRequest {

    String getUserId();

    void setUserId(String userId);

    String[] getProperties();

    void setProperties(String[] properties);
}
