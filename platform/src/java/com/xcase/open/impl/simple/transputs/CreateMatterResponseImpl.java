/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.CreateMatterResponse;

/**
 *
 * @author martin
 */
public class CreateMatterResponseImpl extends OpenResponseImpl implements CreateMatterResponse {

    private int Id;

    public int getId() {
        return this.Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }
}
