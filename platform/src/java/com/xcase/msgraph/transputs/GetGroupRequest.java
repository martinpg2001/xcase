/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.msgraph.transputs;

/**
 *
 * @author martin
 */
public interface GetGroupRequest extends MSGraphRequest {
    String getGroupId();
    void setGroupId(String groupId);
}
