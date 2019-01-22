/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.transputs.GetSwaggerResponse;

/**
 *
 * @author martinpg
 */
public class GetSwaggerResponseImpl extends IntegrateResponseImpl implements GetSwaggerResponse {

    private String swagger;

    public String getSwagger() {
        return this.swagger;
    }

    public void setSwagger(String swagger) {
        this.swagger = swagger;
    }

}
