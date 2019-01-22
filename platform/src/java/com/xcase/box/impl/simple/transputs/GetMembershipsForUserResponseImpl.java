/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.objects.BoxMembership;
import com.xcase.box.transputs.GetMembershipsForUserResponse;
import java.util.List;

/**
 *
 * @author martin
 */
public class GetMembershipsForUserResponseImpl extends BoxResponseImpl implements GetMembershipsForUserResponse {

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
