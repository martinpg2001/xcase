/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.objects.MSGraphGroup;
import com.xcase.msgraph.transputs.GetGroupResponse;

/**
 *
 * @author martin
 */
public class GetGroupResponseImpl extends MSGraphResponseImpl implements GetGroupResponse {
    private MSGraphGroup group;

    public MSGraphGroup getGroup() {
        return this.group;
    }

    public void setGroup(MSGraphGroup group) {
        this.group = group;
    }

}
