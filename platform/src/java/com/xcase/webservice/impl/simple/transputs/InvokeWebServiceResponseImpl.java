/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.webservice.impl.simple.transputs;

import com.xcase.webservice.WebServiceStub;
import com.xcase.webservice.transputs.InvokeWebServiceResponse;

/**
 *
 * @author martin
 */
public class InvokeWebServiceResponseImpl implements InvokeWebServiceResponse {

    private WebServiceStub.Output_WebService output;

    public WebServiceStub.Output_WebService getOutput() {
        return this.output;
    }

    public void setOutput(WebServiceStub.Output_WebService output) {
        this.output = output;
    }
}
