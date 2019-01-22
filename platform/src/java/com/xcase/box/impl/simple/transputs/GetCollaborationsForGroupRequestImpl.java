/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.transputs.GetCollaborationsForGroupRequest;

/**
 *
 * @author martin
 */
public class GetCollaborationsForGroupRequestImpl extends BoxRequestImpl implements GetCollaborationsForGroupRequest {

    private String id;

    public String getId() {
        return this.id;
    }

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
