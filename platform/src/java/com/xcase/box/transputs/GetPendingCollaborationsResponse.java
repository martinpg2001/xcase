/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

import com.xcase.box.objects.BoxCollaboration;
import java.util.List;

/**
 *
 * @author martin
 */
public interface GetPendingCollaborationsResponse extends BoxResponse {

    /**
     * @return the collaborations
     */
    public List<BoxCollaboration> getCollaborations();

    /**
     * @param boxCollaborationList the collaborations
     */
    public void setCollaborations(List<BoxCollaboration> boxCollaborationList);
}
