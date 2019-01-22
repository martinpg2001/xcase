/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

/**
 *
 * @author martin
 */
public interface CreateCollaborationRequest extends BoxRequest {

    /**
     * @return the accessibleBy
     */
    public String getAccessibleBy();

    /**
     * @param accessibleBy the accessibleBy to set
     */
    public void setAccessibleBy(String accessibleBy);

    /**
     * @return the folderId
     */
    public String getFolderId();

    /**
     * @param folderId the folderId to set
     */
    public void setFolderId(String folderId);

    /**
     * @return the notify
     */
    public boolean getNotify();

    /**
     * @param notify the notify to set
     */
    public void setNotify(boolean notify);

    /**
     * @return the role
     */
    public String getRole();

    /**
     * @param role the role to set
     */
    public void setRole(String role);
}
