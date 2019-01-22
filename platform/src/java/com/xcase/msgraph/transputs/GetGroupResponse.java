/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.msgraph.transputs;

import com.xcase.msgraph.objects.MSGraphGroup;

/**
 *
 * @author martin
 */
public interface GetGroupResponse extends MSGraphResponse {
    MSGraphGroup getGroup();
    void setGroup(MSGraphGroup group);
}
