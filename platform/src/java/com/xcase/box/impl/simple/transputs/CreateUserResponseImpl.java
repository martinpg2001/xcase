/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.objects.BoxUser;
import com.xcase.box.transputs.CreateUserResponse;

/**
 *
 * @author martin
 */
public class CreateUserResponseImpl extends BoxResponseImpl implements CreateUserResponse {

    /**
     * created user.
     */
    private BoxUser boxUser;

    public String getId() {
        if (boxUser != null) {
            return boxUser.getUserId();
        }

        return null;
    }

    /**
     * @return the folder
     */
    public BoxUser getUser() {
        return this.boxUser;
    }

    /**
     */
    public void setUser(BoxUser boxUser) {
        this.boxUser = boxUser;
    }
}
