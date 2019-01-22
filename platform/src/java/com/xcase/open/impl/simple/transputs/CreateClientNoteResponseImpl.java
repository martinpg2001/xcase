/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.CreateClientNoteResponse;

public class CreateClientNoteResponseImpl extends OpenResponseImpl implements CreateClientNoteResponse {

    private int id;

    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

}
