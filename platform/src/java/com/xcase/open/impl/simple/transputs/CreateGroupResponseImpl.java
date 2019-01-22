/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.CreateGroupResponse;

public class CreateGroupResponseImpl extends OpenResponseImpl implements CreateGroupResponse {

    private int Id;

    public int getId() {
        return this.Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

}
