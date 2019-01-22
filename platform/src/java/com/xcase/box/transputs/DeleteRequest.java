/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

/**
 *
 * @author martin
 */
public interface DeleteRequest extends BoxRequest {

    /**
     * @return the authToken
     */
    public String getAuthToken();

    /**
     * @param authToken the authToken to set
     */
    public void setAuthToken(String authToken);

    /**
     * @return the eTag
     */
    public String getETag();

    /**
     * @param eTag the eTag to set
     */
    public void setETag(String eTag);

    /**
     * @return the recursive
     */
    public boolean getRecursive();

    /**
     * @param recursive the recursive to set
     */
    public void setRecursive(boolean recursive);

    /**
     * @return the target
     */
    public String getTarget();

    /**
     * @param target the target to set
     */
    public void setTarget(String target);

    /**
     * @return the targetId
     */
    public String getTargetId();

    /**
     * @param targetId the targetId to set
     */
    public void setTargetId(String targetId);

}
