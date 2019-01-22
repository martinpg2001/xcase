/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.ClientData;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.GetClientRequest;
import com.xcase.open.transputs.GetClientResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetClientMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetClientMethod() {
        super();
//        LOGGER.debug("finishing GetClientMethod()");
    }

    public GetClientResponse getClient(GetClientRequest getClientRequest) {
        LOGGER.debug("starting getClient()");
        try {
            GetClientResponse getClientResponse = OpenResponseFactory.createGetClientResponse();
            LOGGER.debug("swaggerApiUrl is " + swaggerApiUrl);
            CommonApiWebProxy commonApiWebProxy = null;
            if (getClientRequest.getAccessToken() != null) {
                commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl), getClientRequest.getAccessToken());
            } else {
                commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            }
            
            LOGGER.debug("created commonApiWebProxy");
            String clientId = getClientRequest.getClientId();
            String[] properties = getClientRequest.getProperties();
            /* Invoke the GetClient() method */
            ClientData clientData = commonApiWebProxy.GetClient(clientId, properties);
            LOGGER.debug("got client");
            getClientResponse.setClientData(clientData);
            return getClientResponse;
        } catch (Exception e) {
            LOGGER.warn("exception getting client: " + e.getMessage());
        }

        return null;
    }
}
