/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.CreatePartyWarningResponse;

public class CreatePartyWarningResponseImpl implements CreatePartyWarningResponse {

    private int id;

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int entityWarningID) {
        this.id = id;
    }

}
