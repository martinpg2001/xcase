/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.CreatePartyData;
import com.xcase.open.transputs.CreatePartiesRequest;

/**
 *
 * @author martin
 */
public class CreatePartiesRequestImpl extends OpenRequestImpl implements CreatePartiesRequest {

    private CreatePartyData[] createPartyDataArray;

    public CreatePartyData[] getCreatePartyDataArray() {
        return this.createPartyDataArray;
    }

    public void setCreatePartyDataArray(CreatePartyData[] createPartyDataArray) {
        this.createPartyDataArray = createPartyDataArray;
    }
}
