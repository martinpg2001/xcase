/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

import com.xcase.box.objects.BoxGroup;

/**
 *
 * @author martin
 */
public interface UpdateGroupResponse extends BoxResponse {

    /**
     * @return the boxGroup
     */
    public BoxGroup getGroup();

    /**
     * @param boxGroup the boxGroup to set
     */
    public void setGroup(BoxGroup boxGroup);
}
