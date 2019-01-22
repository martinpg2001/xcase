/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

import com.xcase.box.objects.BoxUser;

/**
 *
 * @author martin
 */
public interface CreateUserResponse extends BoxResponse {

    /**
     * @return the id of the created user
     */
    public String getId();

    /**
     * @return the boxUser
     */
    public BoxUser getUser();

    /**
     * @param boxUser the boxUser to set
     */
    public void setUser(BoxUser boxUser);
}
