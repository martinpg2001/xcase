/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.CreateClientData;
import com.xcase.open.transputs.CreateClientsRequest;
import com.xcase.open.transputs.CreateClientsResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreateClientsMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreateClientsMethod() {
        super();
//        LOGGER.debug("finishing CreateClientsMethod()");
    }

    public CreateClientsResponse createClients(CreateClientsRequest createClientsRequest) {
        LOGGER.debug("starting createClients()");
        try {
            CreateClientData[] createClientDataArray = createClientsRequest.getCreateClientDataArray();
            LOGGER.debug("got createClientDataArray");
            CreateClientsResponse createClientsResponse = OpenResponseFactory.createCreateClientsResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the CreateClients() method */
            commonApiWebProxy.CreateClients(createClientDataArray);
            LOGGER.debug("created clients");
            return createClientsResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating clients: " + e.getMessage());
        }

        return null;
    }
}
