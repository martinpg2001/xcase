/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.objects.BoxMembership;
import com.xcase.box.transputs.UpdateMembershipResponse;

/**
 *
 * @author martin
 */
public class UpdateMembershipResponseImpl extends BoxResponseImpl implements UpdateMembershipResponse {

    /**
     * updated membership.
     */
    private BoxMembership boxMembership;

    /**
     *
     * @return id if not null
     */
    public String getId() {
        if (boxMembership != null) {
            return boxMembership.getId();
        }

        return null;
    }

    /**
     * @return the boxMembership
     */
    public BoxMembership getMembership() {
        return this.boxMembership;
    }

    /**
     * @param boxMembership the boxMembership to set
     */
    public void setMembership(BoxMembership boxMembership) {
        this.boxMembership = boxMembership;
    }
}
