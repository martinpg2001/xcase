/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.transputs.GetUsersRequest;

/**
 *
 * @author martin
 */
public class GetUsersRequestImpl extends BoxRequestImpl implements GetUsersRequest {

    /**
     * @return action name
     */
    public String getActionName() {
        return "GetUsersRequestImpl";
    }
}
