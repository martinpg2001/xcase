/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.transputs.GetUserInfoRequest;

/**
 *
 * @author martin
 */
public class GetUserInfoRequestImpl extends BoxRequestImpl implements GetUserInfoRequest {

    /**
     * user id.
     */
    private String userId;

    /**
     * @return the userId
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return action name
     */
    public String getActionName() {
        return BoxConstant.ACTION_NAME_GET_USER_INFO;
    }
}
