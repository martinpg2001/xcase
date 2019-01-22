/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

import com.xcase.box.objects.BoxCollaboration;

/**
 *
 * @author martin
 */
public interface CreateCollaborationResponse extends BoxResponse {

    /**
     *
     * @return boxCollaboration
     */
    public BoxCollaboration getCollaboration();

    /**
     *
     * @param boxCollaboration
     */
    public void setCollaboration(BoxCollaboration boxCollaboration);

    /**
     *
     * @return id
     */
    public String getId();
}
