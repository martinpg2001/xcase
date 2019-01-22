/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.transputs.CreateCollaborationRequest;

/**
 *
 * @author martin
 */
public class CreateCollaborationRequestImpl extends BoxRequestImpl implements CreateCollaborationRequest {

    private String accessibleBy;
    private String folderId;
    private boolean notify;
    private String role;

    /**
     * @return the accessibleBy
     */
    public String getAccessibleBy() {
        return this.accessibleBy;
    }

    /**
     * @param accessibleBy the accessibleBy to set
     */
    public void setAccessibleBy(String accessibleBy) {
        this.accessibleBy = accessibleBy;
    }

    /**
     * @return the folderId
     */
    public String getFolderId() {
        return this.folderId;
    }

    /**
     * @param folderId the folderId to set
     */
    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    /**
     * @return the notify
     */
    public boolean getNotify() {
        return this.notify;
    }

    /**
     * @param role
     */
    public void setNotify(boolean role) {
        this.notify = notify;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return this.role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return action name
     */
    public String getActionName() {
        return BoxConstant.ACTION_NAME_CREATE_COLLABORATION;
    }
}
