/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.CreateMatterWarningResponse;

public class CreateMatterWarningResponseImpl implements CreateMatterWarningResponse {

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
