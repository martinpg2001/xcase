/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.EntitySecurityData;

/**
 *
 * @author martin
 */
public interface GetEntitySecurityResponse extends OpenResponse {

    EntitySecurityData getEntitySecurityData();

    void setEntitySecurityData(EntitySecurityData entitySecurityData);
}
