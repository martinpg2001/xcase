/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.CreatePartyAliasResponse;

public class CreatePartyAliasResponseImpl implements CreatePartyAliasResponse {

    private int id;

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int aliasID) {
        this.id = id;
    }

}
