/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.webservice.factories;

import com.xcase.webservice.transputs.InvokeWebServiceResponse;
import java.lang.invoke.*;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class WebServiceResponseFactory extends BaseWebServiceFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * create response object.
     *
     * @return response object
     */
    public static InvokeWebServiceResponse createInvokeWebServiceResponse() {
        Object obj = newInstanceOf("webservice.config.responsefactory.InvokeWebServiceResponse");
        return (InvokeWebServiceResponse) obj;
    }
}
