/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

import com.xcase.box.objects.BoxUser;

/**
 *
 * @author martin
 */
public interface GetUserInfoResponse extends BoxResponse {

    /**
     * @return the user
     */
    public BoxUser getUser();

    /**
     * @param boxUser the user to set
     */
    public void setUser(BoxUser boxUser);
}
