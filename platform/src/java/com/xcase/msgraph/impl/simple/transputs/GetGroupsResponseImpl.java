/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.objects.MSGraphGroup;
import com.xcase.msgraph.transputs.GetGroupsResponse;

/**
 *
 * @author martin
 */
public class GetGroupsResponseImpl extends MSGraphResponseImpl implements GetGroupsResponse {
    private MSGraphGroup[] groups;

    public MSGraphGroup[] getGroups() {
        return this.groups;
    }

    public void setGroups(MSGraphGroup[] groups) {
        this.groups = groups;
    }

}
