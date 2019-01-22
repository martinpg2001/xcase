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
public interface GetCollaborationsResponse {

    /**
     * @return the collaborations
     */
    public List<BoxCollaboration> getCollaborations();

    /**
     * @param boxCollaborationList the collaborations
     */
    public void setCollaborations(List<BoxCollaboration> boxCollaborationList);
}
