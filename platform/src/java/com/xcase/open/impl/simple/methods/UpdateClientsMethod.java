/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.UpdateClientData;
import com.xcase.open.transputs.UpdateClientsRequest;
import com.xcase.open.transputs.UpdateClientsResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class UpdateClientsMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public UpdateClientsMethod() {
        super();
//        LOGGER.debug("finishing UpdateClientsMethod()");
    }

    public UpdateClientsResponse updateClients(UpdateClientsRequest updateClientsRequest) {
        LOGGER.debug("starting updateClients()");
        try {
            UpdateClientData[] updateClientDataArray = updateClientsRequest.getUpdateClientDataArray();
            LOGGER.debug("got updateClientDataArray");
            UpdateClientsResponse updateClientsResponse = OpenResponseFactory.createUpdateClientsResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the UpdateClients() method */
            commonApiWebProxy.UpdateClients(updateClientDataArray);
            LOGGER.debug("updated clients");
            return updateClientsResponse;
        } catch (Exception e) {
            LOGGER.warn("exception updating clients: " + e.getMessage());
        }

        return null;
    }
}
