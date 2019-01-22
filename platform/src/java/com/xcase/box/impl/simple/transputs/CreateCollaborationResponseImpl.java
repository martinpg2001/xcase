/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.objects.BoxCollaboration;
import com.xcase.box.transputs.CreateCollaborationResponse;

/**
 *
 * @author martin
 */
public class CreateCollaborationResponseImpl extends BoxResponseImpl implements CreateCollaborationResponse {

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
