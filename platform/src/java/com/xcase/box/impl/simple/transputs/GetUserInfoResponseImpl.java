/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.objects.BoxUser;
import com.xcase.box.transputs.GetUserInfoResponse;

/**
 *
 * @author martin
 */
public class GetUserInfoResponseImpl extends BoxResponseImpl implements GetUserInfoResponse {

    private BoxUser boxUser;

    /**
     * @return the user
     */
    public BoxUser getUser() {
        return this.boxUser;
    }

    /**
     * @param boxUser
     */
    public void setUser(BoxUser boxUser) {
        this.boxUser = boxUser;
    }
}
