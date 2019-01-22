/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.transputs.MoveRequest;

/**
 * @author martin
 *
 */
public class MoveRequestImpl extends BoxRequestImpl implements MoveRequest {

    /**
     * auth token.
     */
    private String authToken;

    /**
     * target, could be 'file' or 'folder'.
     */
    private String target;

    /**
     * target id, could be file id or folder id.
     */
    private String targetId;

    /**
     * folder id, the folder will move item to.
     */
    private String destinationId;

    /**
     * @return the authToken
     */
    public String getAuthToken() {
        return this.authToken;
    }

    /**
     * @param authToken the authToken to set
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * @return the target
     */
    public String getTarget() {
        return this.target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * @return the targetId
     */
    public String getTargetId() {
        return this.targetId;
    }

    /**
     * @param targetId the targetId to set
     */
    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    /**
     * @return the destinationId
     */
    public String getDestinationId() {
        return this.destinationId;
    }

    /**
     * @param destinationId the destinationId to set
     */
    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    /**
     * @return action name
     */
    public String getActionName() {
        return BoxConstant.ACTION_NAME_MOVE;
    }
}
