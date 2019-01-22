/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.ChangePartyType;

/**
 *
 * @author martin
 */
public interface ChangePartyTypeRequest {

    String getPartyId();

    void setPartyId(String partyId);

    ChangePartyType getChangePartyType();

    void setChangePartyType(ChangePartyType changePartyType);
}
