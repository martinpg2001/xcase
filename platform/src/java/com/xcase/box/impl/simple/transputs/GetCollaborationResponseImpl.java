/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.objects.BoxCollaboration;
import com.xcase.box.transputs.GetCollaborationResponse;

/**
 *
 * @author martin
 */
public class GetCollaborationResponseImpl extends BoxResponseImpl implements GetCollaborationResponse {

    private BoxCollaboration boxCollaboration;

    /**
     * @return the collaboration
     */
    public BoxCollaboration getCollaboration() {
        return this.boxCollaboration;
    }

    /**
     * 
     * @param boxCollaboration 
     */
    public void setCollaboration(BoxCollaboration boxCollaboration) {
        this.boxCollaboration = boxCollaboration;
    }

    /**
     * @return the boxCollaboration id
     */
    public String getId() {
        if (this.boxCollaboration != null) {
            return this.boxCollaboration.getId();
        }

        return null;
    }
}
