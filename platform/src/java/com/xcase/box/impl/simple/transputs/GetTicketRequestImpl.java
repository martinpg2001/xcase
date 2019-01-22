/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.transputs.GetTicketRequest;

/**
 * @author martin
 *
 */
public class GetTicketRequestImpl extends BoxRequestImpl implements GetTicketRequest {

    /**
     * @return action name
     */
    public String getActionName() {
        return BoxConstant.ACTION_NAME_GET_TICKET;
    }
}
