/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.objects;

/**
 *
 * @author martin
 */
public interface BoxCollaboration {

    /**
     * @return the accessibleBy
     */
    public BoxAccessibleBy getAccessibleBy();

    /**
     * @param accessibleBy the accessibleBy to set
     */
    public void setAccessibleBy(BoxAccessibleBy accessibleBy);

    /**
     * @return the id
     */
    public String getId();

    /**
     * @param id the id to set
     */
    public void setId(String id);

    /**
     * @return the login
     */
    public String getLogin();

    /**
     * @return the role
     */
    public String getRole();

    /**
     * @param role the role to set
     */
    public void setRole(String role);
}
