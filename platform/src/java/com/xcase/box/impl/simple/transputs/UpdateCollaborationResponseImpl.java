/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.objects.BoxCollaboration;
import com.xcase.box.transputs.UpdateCollaborationResponse;

/**
 *
 * @author martin
 */
public class UpdateCollaborationResponseImpl extends BoxResponseImpl implements UpdateCollaborationResponse {

    private BoxCollaboration boxCollaboration;

    public BoxCollaboration getCollaboration() {
        return this.boxCollaboration;
    }

    public void setCollaboration(BoxCollaboration boxCollaboration) {
        this.boxCollaboration = boxCollaboration;
    }

    public String getId() {
        if (boxCollaboration != null) {
            return boxCollaboration.getId();
        }

        return null;
    }
}
