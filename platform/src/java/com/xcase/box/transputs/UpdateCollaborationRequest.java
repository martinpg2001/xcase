/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

/**
 *
 * @author martin
 */
public interface UpdateCollaborationRequest extends BoxRequest {

    /**
     * @return the id
     */
    public String getId();

    /**
     * @param id the id to set
     */
    public void setId(String id);

    /**
     * @return the role
     */
    public String getRole();

    /**
     * @param role the role to set
     */
    public void setRole(String role);

    /**
     * @return the status
     */
    public String getStatus();

    /**
     * @param status the status to set
     */
    public void setStatus(String status);
}
