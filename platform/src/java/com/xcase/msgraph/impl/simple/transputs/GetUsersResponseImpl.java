/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.objects.MSGraphUser;
import com.xcase.msgraph.transputs.GetUsersResponse;

/**
 *
 * @author martinpg
 */
public class GetUsersResponseImpl extends MSGraphResponseImpl implements GetUsersResponse {
    private MSGraphUser[] users;

    public MSGraphUser[] getUsers() {
        return this.users;
    }

    public void setUsers(MSGraphUser[] users) {
        this.users = users;
    }

}
