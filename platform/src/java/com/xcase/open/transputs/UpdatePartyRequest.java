/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.UpdatePartyData;

/**
 *
 * @author martin
 */
public interface UpdatePartyRequest {

    String getPartyId();

    void setPartyId(String partyId);

    UpdatePartyData getUpdatePartyData();

    void setUpdatePartyData(UpdatePartyData updatePartyData);
}
