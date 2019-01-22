/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.objects.BoxMembership;
import com.xcase.box.transputs.CreateMembershipResponse;

/**
 *
 * @author martin
 */
public class CreateMembershipResponseImpl extends BoxResponseImpl implements CreateMembershipResponse {

    private BoxMembership boxMembership;

    public String getId() {
        if (boxMembership != null) {
            return boxMembership.getId();
        }

        return null;
    }

    public BoxMembership getMembership() {
        return this.boxMembership;
    }

    public void setMembership(BoxMembership boxMembership) {
        this.boxMembership = boxMembership;
    }
}
