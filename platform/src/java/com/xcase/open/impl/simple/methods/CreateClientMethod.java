/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.CreateClientData;
import com.xcase.open.impl.simple.core.ClientData;
import com.xcase.open.transputs.CreateClientRequest;
import com.xcase.open.transputs.CreateClientResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreateClientMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreateClientMethod() {
        super();
//        LOGGER.debug("finishing CreateClientMethod()");
    }

    public CreateClientResponse createClient(CreateClientRequest request) {
        LOGGER.debug("starting createClient()");
        try {
            CreateClientData createClientData = request.getCreateClientData();
            LOGGER.debug("got createClientData");
            CreateClientResponse createClientResponse = OpenResponseFactory.createCreateClientResponse();
            CommonApiWebProxy commonApiWebProxy = null;
            if (request.getAccessToken() != null) {
                commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl), request.getAccessToken());
            } else {
                commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            }
            
            /* Invoke the CreateClient() method */
            ClientData clientData = commonApiWebProxy.CreateClient(createClientData);
            LOGGER.debug("clientID is " + clientData.clientId);
            createClientResponse.setId(clientData.clientId);
            return createClientResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating client: " + e.getMessage());
        }

        return null;
    }
}
