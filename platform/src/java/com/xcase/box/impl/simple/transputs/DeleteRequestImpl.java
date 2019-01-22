/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.transputs.DeleteRequest;

/**
 * @author martin
 *
 */
public class DeleteRequestImpl extends BoxRequestImpl implements DeleteRequest {

    /**
     * auth token.
     */
    private String authToken;

    /**
     * etag token.
     */
    private String eTag;

    /**
     * recursive token.
     */
    private boolean recursive;

    /**
     * target, could be 'file' or 'folder'.
     */
    private String target;

    /**
     * target id, file id or folder id.
     */
    private String targetId;

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
     * @return the eTag
     */
    public String getETag() {
        return this.eTag;
    }

    /**
     * @param eTag the eTag to set
     */
    public void setETag(String eTag) {
        this.eTag = eTag;
    }

    /**
     * @return the recursive
     */
    public boolean getRecursive() {
        return this.recursive;
    }

    /**
     * @param recursive the recursive to set
     */
    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
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
     * @return action name
     */
    public String getActionName() {
        return BoxConstant.ACTION_NAME_DELETE;
    }
}
