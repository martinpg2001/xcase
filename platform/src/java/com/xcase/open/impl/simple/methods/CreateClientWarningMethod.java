/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.CreateWarningData;
import com.xcase.open.transputs.CreateClientWarningRequest;
import com.xcase.open.transputs.CreateClientWarningResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreateClientWarningMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreateClientWarningMethod() {
        super();
//        LOGGER.debug("finishing CreateClientWarningMethod()");
    }

    public CreateClientWarningResponse createClientWarning(CreateClientWarningRequest createEntityWarningRequest) {
        LOGGER.debug("starting createClientWarning()");
        try {
            String entityId = createEntityWarningRequest.getEntityId();
            CreateWarningData createWarningData = createEntityWarningRequest.getCreateWarningData();
            CreateClientWarningResponse createEntityWarningResponse = OpenResponseFactory.createCreateEntityWarningResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the CreateClientWarning() method */
            int entityWarningID = commonApiWebProxy.CreateClientWarning(entityId, createWarningData);
            LOGGER.debug("entityWarningID is " + entityWarningID);
            createEntityWarningResponse.setId(entityWarningID);
            return createEntityWarningResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating client warning: " + e.getMessage());
        }

        return null;
    }
}
