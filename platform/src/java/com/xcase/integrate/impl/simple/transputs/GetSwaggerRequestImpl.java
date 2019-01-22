/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.transputs.GetSwaggerRequest;

/**
 *
 * @author martinpg
 */
public class GetSwaggerRequestImpl extends IntegrateRequestImpl implements GetSwaggerRequest {

    private String detail;

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
