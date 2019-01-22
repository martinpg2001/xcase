/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.objects.BoxCollaboration;
import com.xcase.box.transputs.GetPendingCollaborationsResponse;
import java.util.List;

/**
 *
 * @author martin
 */
public class GetPendingCollaborationsResponseImpl extends BoxResponseImpl implements GetPendingCollaborationsResponse {

    private List<BoxCollaboration> boxCollaborationList;

    /**
     * @return the collaborations
     */
    public List<BoxCollaboration> getCollaborations() {
        return boxCollaborationList;
    }

    /**
     * 
     * @param boxCollaborationList 
     */
    public void setCollaborations(List<BoxCollaboration> boxCollaborationList) {
        this.boxCollaborationList = boxCollaborationList;
    }
}
