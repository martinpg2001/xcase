/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.CreatePartyAddressResponse;

public class CreatePartyAddressResponseImpl implements CreatePartyAddressResponse {

    private int id;

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int addressID) {
        this.id = id;
    }

}
