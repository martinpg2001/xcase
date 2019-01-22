/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.transputs.DeleteCollaborationRequest;

/**
 *
 * @author martin
 */
public class DeleteCollaborationRequestImpl extends BoxRequestImpl implements DeleteCollaborationRequest {

    /**
     * parent folder id.
     */
    private String collaborationId;

    /**
     * @return the collaborationId
     */
    public String getCollaborationId() {
        return this.collaborationId;
    }

    /**
     * @param collaborationId the collaborationId to set
     */
    public void setCollaborationId(String collaborationId) {
        this.collaborationId = collaborationId;
    }

    /**
     * @return action name
     */
    public String getActionName() {
        return BoxConstant.ACTION_NAME_DELETE_COLLABORATION;
    }
}
