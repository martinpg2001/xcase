/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.transputs.UpdateMembershipRequest;

/**
 *
 * @author martin
 */
public class UpdateMembershipRequestImpl extends BoxRequestImpl implements UpdateMembershipRequest {

    private String id;
    private String role;

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

    /**
     * @return the role
     */
    public String getRole() {
        return this.role;
    }

    /**
     * @param role the name to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return action name
     */
    public String getActionName() {
        return BoxConstant.ACTION_NAME_ADD_TO_MYBOX;
    }
}
