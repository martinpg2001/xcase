/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.objects.BoxGroup;
import com.xcase.box.transputs.UpdateGroupResponse;

/**
 *
 * @author martin
 */
public class UpdateGroupResponseImpl extends BoxResponseImpl implements UpdateGroupResponse {

    /**
     * updated group.
     */
    private BoxGroup boxGroup;

    /**
     *
     * @return group id if not null
     */
    public String getId() {
        if (boxGroup != null) {
            return boxGroup.getId();
        }

        return null;
    }

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
}
