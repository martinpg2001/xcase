/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.CreatePartyResponse;

/**
 *
 * @author martin
 */
public class CreatePartyResponseImpl extends OpenRequestImpl implements CreatePartyResponse {

    private int Id;

    public int getId() {
        return this.Id;
    }

    public void setId(int id) {
        this.Id = id;
    }
}
