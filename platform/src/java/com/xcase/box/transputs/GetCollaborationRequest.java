/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

/**
 *
 * @author martin
 */
public interface GetCollaborationRequest extends BoxRequest {

    /**
     *
     * @return collaborationId
     */
    public String getCollaborationId();

    /**
     *
     * @param collaborationId
     */
    public void setCollaborationId(String collaborationId);
}
