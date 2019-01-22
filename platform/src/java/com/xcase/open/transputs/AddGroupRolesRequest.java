/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

/**
 *
 * @author martin
 */
public interface AddGroupRolesRequest {

    String getGroupId();

    void setGroupId(String groupId);

    String[] getRoleArray();

    void setRoleArray(String[] roleArray);
}
