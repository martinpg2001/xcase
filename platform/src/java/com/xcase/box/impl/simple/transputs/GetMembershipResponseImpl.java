/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.objects.BoxMembership;
import com.xcase.box.transputs.GetMembershipResponse;

/**
 *
 * @author martin
 */
public class GetMembershipResponseImpl extends BoxResponseImpl implements GetMembershipResponse {

    private BoxMembership boxMembership;

    public BoxMembership getMembership() {
        return this.boxMembership;
    }

    public void setMembership(BoxMembership boxMembership) {
        this.boxMembership = boxMembership;
    }

    public String getId() {
        if (this.boxMembership != null) {
            return this.boxMembership.getId();
        }

        return null;
    }
}
