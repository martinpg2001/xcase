/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.CreatePartyData;

/**
 *
 * @author martin
 */
public interface CreatePartiesRequest {

    CreatePartyData[] getCreatePartyDataArray();

    void setCreatePartyDataArray(CreatePartyData[] createClientDataArray);
}
