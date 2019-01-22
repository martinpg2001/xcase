/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

import com.xcase.box.objects.BoxMembership;

/**
 *
 * @author martin
 */
public interface GetMembershipResponse {

    /**
     *
     * @return id
     */
    public String getId();

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
