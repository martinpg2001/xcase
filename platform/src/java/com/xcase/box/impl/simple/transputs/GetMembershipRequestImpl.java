/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.transputs.GetMembershipRequest;

/**
 *
 * @author martin
 */
public class GetMembershipRequestImpl extends BoxRequestImpl implements GetMembershipRequest {

    private String id;

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
     * @return action name
     */
    public String getActionName() {
        return BoxConstant.ACTION_NAME_GET_COLLABORATION;
    }
}
