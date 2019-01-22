/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.EntitySecurityData;
import com.xcase.open.transputs.GetEntitySecurityResponse;

/**
 *
 * @author martin
 */
public class GetEntitySecurityResponseImpl extends OpenResponseImpl implements GetEntitySecurityResponse {

    private EntitySecurityData entitySecurityData;

    public EntitySecurityData getEntitySecurityData() {
        return this.entitySecurityData;
    }

    public void setEntitySecurityData(EntitySecurityData entitySecurityData) {
        this.entitySecurityData = entitySecurityData;
    }
}
