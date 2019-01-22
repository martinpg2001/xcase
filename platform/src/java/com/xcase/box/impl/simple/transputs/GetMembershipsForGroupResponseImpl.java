/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.objects.BoxMembership;
import com.xcase.box.transputs.GetMembershipsForGroupResponse;
import java.util.*;

/**
 *
 * @author martin
 */
public class GetMembershipsForGroupResponseImpl extends BoxResponseImpl implements GetMembershipsForGroupResponse {

    private List<BoxMembership> boxMembershipList;
    private String totalCount;

    public List<BoxMembership> getMemberships() {
        return this.boxMembershipList;
    }

    public void setMemberships(List<BoxMembership> boxMembershipList) {
        this.boxMembershipList = boxMembershipList;
    }

    public String getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }
}
