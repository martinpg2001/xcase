/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

/**
 *
 * @author martin
 */
public interface RemoveUserRolesRequest {

    String getUserId();

    void setUserId(String userId);

    String[] getRoleArray();

    void setRoleArray(String[] roleArray);
}
