/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.webservice;

import com.xcase.webservice.impl.simple.methods.InvokeWebServiceMethod;
import com.xcase.webservice.objects.WebServiceException;
import com.xcase.webservice.impl.simple.core.WebServiceConfigurationManager;
import com.xcase.webservice.transputs.InvokeWebServiceRequest;
import com.xcase.webservice.transputs.InvokeWebServiceResponse;
import java.io.IOException;
import java.lang.invoke.*;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class WebServiceImpl implements WebServiceExternalAPI {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * configuration manager
     */
    public WebServiceConfigurationManager LocalConfigurationManager = WebServiceConfigurationManager.getConfigurationManager();

    /**
     * invoke Web service method action implementation.
     */
    private InvokeWebServiceMethod invokeWebServiceMethod = new InvokeWebServiceMethod();

    public InvokeWebServiceResponse invokeWebService(InvokeWebServiceRequest invokeWebServiceRequest) throws IOException, WebServiceException {
        return this.invokeWebServiceMethod.invokeWebService(invokeWebServiceRequest);
    }
}
