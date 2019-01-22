/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

import com.xcase.box.objects.BoxMembership;

/**
 *
 * @author martin
 */
public interface UpdateMembershipResponse extends BoxResponse {

    /**
     *
     * @return boxMembership
     */
    public BoxMembership getMembership();

    /**
     *
     * @param boxMembership
     */
    public void setMembership(BoxMembership boxMembership);
}
