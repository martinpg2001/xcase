/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.objects.BoxGroup;
import com.xcase.box.transputs.GetGroupResponse;

/**
 *
 * @author martin
 */
public class GetGroupResponseImpl extends BoxResponseImpl implements GetGroupResponse {

    private BoxGroup boxGroup;

    /**
     * @return the boxGroup
     */
    public BoxGroup getGroup() {
        return this.boxGroup;
    }

    /**
     * @param boxGroup the boxGroup to set
     */
    public void setGroup(BoxGroup boxGroup) {
        this.boxGroup = boxGroup;
    }

    /**
     * @return the boxGroup id
     */
    public String getId() {
        if (this.boxGroup != null) {
            return this.boxGroup.getId();
        }

        return null;
    }
}
