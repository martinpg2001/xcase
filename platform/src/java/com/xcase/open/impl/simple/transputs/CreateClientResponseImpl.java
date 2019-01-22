/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.CreateClientResponse;

/**
 *
 * @author martin
 */
public class CreateClientResponseImpl extends OpenResponseImpl implements CreateClientResponse {

    private String Id;

    public String getId() {
        return this.Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
}
