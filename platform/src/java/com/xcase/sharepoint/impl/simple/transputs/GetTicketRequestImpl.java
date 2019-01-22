/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.transputs;

import com.xcase.sharepoint.constant.SharepointConstant;
import com.xcase.sharepoint.transputs.GetTicketRequest;

/**
 * @author martin
 *
 */
public class GetTicketRequestImpl extends SharepointRequestImpl implements GetTicketRequest {

    /**
     * @return action name
     */
    public String getActionName() {
        return SharepointConstant.ACTION_NAME_GET_TICKET;
    }
}
