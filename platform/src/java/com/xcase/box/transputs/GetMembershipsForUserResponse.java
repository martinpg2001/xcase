/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

import com.xcase.box.objects.BoxMembership;
import java.util.List;

/**
 *
 * @author martin
 */
public interface GetMembershipsForUserResponse extends BoxResponse {

    /**
     *
     * @return boxMembershipList
     */
    public List<BoxMembership> getMemberships();

    /**
     *
     * @param boxMembershipList
     */
    public void setMemberships(List<BoxMembership> boxMembershipList);

    /**
     *
     * @return totalCount
     */
    public String getTotalCount();

    /**
     *
     * @param totalCount
     */
    public void setTotalCount(String totalCount);
}
