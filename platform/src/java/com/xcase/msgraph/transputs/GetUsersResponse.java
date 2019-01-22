/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.msgraph.transputs;

import com.xcase.msgraph.objects.MSGraphUser;

/**
 *
 * @author martinpg
 */
public interface GetUsersResponse extends MSGraphResponse {
    MSGraphUser[] getUsers();
    void setUsers(MSGraphUser[] users);
}
