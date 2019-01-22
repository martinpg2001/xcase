/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.transputs.GetCollaborationRequest;

/**
 *
 * @author martin
 */
public class GetCollaborationRequestImpl extends BoxRequestImpl implements GetCollaborationRequest {

    private String collaborationId;

    /**
     * @return the collaborationId
     */
    public String getCollaborationId() {
        return this.collaborationId;
    }

    /**
     */
    public void setCollaborationId(String collaborationId) {
        this.collaborationId = collaborationId;
    }

    /**
     * @return action name
     */
    public String getActionName() {
        return BoxConstant.ACTION_NAME_GET_COLLABORATION;
    }
}
