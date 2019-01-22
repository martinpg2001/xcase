/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.UpdateClientData;
import com.xcase.open.transputs.UpdateClientRequest;
import com.xcase.open.transputs.UpdateClientResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class UpdateClientMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public UpdateClientMethod() {
        super();
//        LOGGER.debug("finishing UpdateClientMethod()");
    }

    public UpdateClientResponse updateClient(UpdateClientRequest updateClientRequest) {
        LOGGER.debug("starting updateClient()");
        try {
            String clientId = updateClientRequest.getClientId();
            UpdateClientData updateClientData = updateClientRequest.getUpdateClientData();
            LOGGER.debug("got updateClientData");
            UpdateClientResponse updateClientResponse = OpenResponseFactory.createUpdateClientResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the UpdateClient() method */
            commonApiWebProxy.UpdateClient(clientId, updateClientData);
            LOGGER.debug("updated client");
            return updateClientResponse;
        } catch (Exception e) {
            LOGGER.warn("exception updating client: " + e.getMessage());
        }

        return null;
    }
}
