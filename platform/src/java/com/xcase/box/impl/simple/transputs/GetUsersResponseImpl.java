/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.objects.BoxUser;
import com.xcase.box.transputs.GetUsersResponse;
import java.util.List;

/**
 *
 * @author martin
 */
public class GetUsersResponseImpl extends BoxResponseImpl implements GetUsersResponse {

    private List<BoxUser> userList;

    /**
     * @return the users
     */
    public List<BoxUser> getUsers() {
        return userList;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<BoxUser> users) {
        this.userList = users;
    }
}
