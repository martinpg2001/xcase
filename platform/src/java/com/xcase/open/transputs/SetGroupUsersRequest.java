/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

/**
 *
 * @author martin
 */
public interface SetGroupUsersRequest {

    String getGroupId();

    void setGroupId(String groupId);

    String[] getUserArray();

    void setUserArray(String[] groupUserArray);
}
