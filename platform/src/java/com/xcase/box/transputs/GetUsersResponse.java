/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

import com.xcase.box.objects.BoxUser;
import java.util.List;

/**
 *
 * @author martin
 */
public interface GetUsersResponse extends BoxResponse {

    /**
     * @return the users
     */
    public List<BoxUser> getUsers();

    /**
     * @param users the users to set
     */
    public void setUsers(List<BoxUser> users);
}
