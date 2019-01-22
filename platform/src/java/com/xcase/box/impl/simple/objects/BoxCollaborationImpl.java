/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.objects;

import com.xcase.box.objects.BoxAccessibleBy;
import com.xcase.box.objects.BoxCollaboration;

/**
 *
 * @author martin
 */
public class BoxCollaborationImpl implements BoxCollaboration {

    private BoxAccessibleBy accessibleBy;
    /**
     * id.
     */
    private String id;

    /**
     * role.
     */
    private String role;

    /**
     * @return the accessibleBy
     */
    public BoxAccessibleBy getAccessibleBy() {
        return this.accessibleBy;
    }

    /**
     * @param accessibleBy the accessibleBy to set
     */
    public void setAccessibleBy(BoxAccessibleBy accessibleBy) {
        this.accessibleBy = accessibleBy;
    }

    /**
     * @return the id
     */
    public String getId() {
        return this.id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        BoxAccessibleBy accessibleBy = getAccessibleBy();
        if (accessibleBy != null) {
            return accessibleBy.getLogin();
        }

        return null;
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
}
