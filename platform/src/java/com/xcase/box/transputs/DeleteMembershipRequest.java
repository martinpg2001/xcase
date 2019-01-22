/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

/**
 *
 * @author martin
 */
public interface DeleteMembershipRequest extends BoxRequest {

    /**
     * @return the groupId
     */
    public String getGroupId();

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(String groupId);

    /**
     * @return the id
     */
    public String getId();

    /**
     * @param id the id to set
     */
    public void setId(String id);

    /**
     * @return the userId
     */
    public String getUserId();

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId);
}
