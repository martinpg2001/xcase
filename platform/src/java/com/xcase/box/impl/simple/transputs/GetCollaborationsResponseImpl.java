/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.objects.BoxCollaboration;
import com.xcase.box.transputs.GetCollaborationsResponse;
import java.util.*;

/**
 *
 * @author martin
 */
public class GetCollaborationsResponseImpl extends BoxResponseImpl implements GetCollaborationsResponse {

    private List<BoxCollaboration> boxCollaborationList;

    public List<BoxCollaboration> getCollaborations() {
        return this.boxCollaborationList;
    }

    public void setCollaborations(List<BoxCollaboration> boxCollaborationList) {
        this.boxCollaborationList = boxCollaborationList;
    }
}
