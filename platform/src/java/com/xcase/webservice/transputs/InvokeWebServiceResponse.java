/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.webservice.transputs;

import com.xcase.webservice.WebServiceStub;

/**
 *
 * @author martin
 */
public interface InvokeWebServiceResponse {

    public WebServiceStub.Output_WebService getOutput();

    public void setOutput(WebServiceStub.Output_WebService output);
}
