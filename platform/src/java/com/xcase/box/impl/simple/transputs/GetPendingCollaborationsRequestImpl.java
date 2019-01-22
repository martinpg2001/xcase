/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.transputs.GetPendingCollaborationsRequest;

/**
 *
 * @author martin
 */
public class GetPendingCollaborationsRequestImpl extends BoxRequestImpl implements GetPendingCollaborationsRequest {

    /**
     * @return action name
     */
    public String getActionName() {
        return BoxConstant.ACTION_NAME_GET_PENDING_COLLABORATIONS;
    }
}
