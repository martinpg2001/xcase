/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.webservice.factories;

import com.xcase.webservice.transputs.InvokeWebServiceRequest;
import java.lang.invoke.*;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class WebServiceRequestFactory extends BaseWebServiceFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * create request object.
     *
     * @return request object
     */
    public static InvokeWebServiceRequest createInvokeWebServiceRequest() {
        Object obj = newInstanceOf("webservice.config.requestfactory.InvokeWebServiceRequest");
        return (InvokeWebServiceRequest) obj;
    }

    /**
     * create request object.
     *
     * @param endpoint endpoint
     * @param username username
     * @param password password
     * @return request object
     */
    public static InvokeWebServiceRequest createInvokeWebServiceRequest(String endpoint, String username, String password) {
        InvokeWebServiceRequest invokeWebServiceRequest = createInvokeWebServiceRequest();
        invokeWebServiceRequest.setEndpoint(endpoint);
        invokeWebServiceRequest.setUsername(username);
        invokeWebServiceRequest.setPassword(password);
        return invokeWebServiceRequest;
    }
}
