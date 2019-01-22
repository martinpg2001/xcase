/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

/**
 *
 * @author martin
 */
public interface SetUserDepartmentsRequest {

    String getUserId();

    void setUserId(String userId);

    String[] getDepartmentArray();

    void setDepartmentArray(String[] userDepartmentArray);
}
