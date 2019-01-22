/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

/**
 *
 * @author martin
 */
public interface DeleteCollaborationRequest extends BoxRequest {

    /**
     * @return the collaborationId
     */
    public String getCollaborationId();

    /**
     * @param collaborationId the collaborationId to set
     */
    public void setCollaborationId(String collaborationId);
}
