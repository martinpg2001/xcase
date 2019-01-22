/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.CreatePartyData;
import com.xcase.open.transputs.CreatePartyRequest;

/**
 *
 * @author martin
 */
public class CreatePartyRequestImpl extends OpenRequestImpl implements CreatePartyRequest {

    private CreatePartyData createPartyData;

    public CreatePartyData getCreatePartyData() {
        return this.createPartyData;
    }

    public void setCreatePartyData(CreatePartyData createPartyData) {
        this.createPartyData = createPartyData;
    }
}
