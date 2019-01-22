/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

/**
 *
 * @author martin
 */
public interface CreateMembershipRequest extends BoxRequest {

    /**
     * @return the groupId
     */
    public String getGroupId();

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(String groupId);

    /**
     * @return the userId
     */
    public String getUserId();

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId);

    /**
     * @return the role
     */
    public String getRole();

    /**
     * @param role the role to set
     */
    public void setRole(String role);
}
