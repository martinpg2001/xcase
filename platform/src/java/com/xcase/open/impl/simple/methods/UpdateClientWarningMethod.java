/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.UpdateWarningData;
import com.xcase.open.transputs.UpdateClientWarningRequest;
import com.xcase.open.transputs.UpdateClientWarningResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class UpdateClientWarningMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public UpdateClientWarningMethod() {
        super();
//        LOGGER.debug("finishing UpdateClientWarningMethod()");
    }

    public UpdateClientWarningResponse updateClientWarning(UpdateClientWarningRequest updateClientWarningRequest) {
        LOGGER.debug("starting updateClientWarningRequest()");
        try {
            String entityId = updateClientWarningRequest.getEntityId();
            int warningId = updateClientWarningRequest.getId();
            UpdateWarningData updateWarningData = updateClientWarningRequest.getUpdateWarningData();
            UpdateClientWarningResponse updateClientWarningResponse = OpenResponseFactory.createUpdateClientWarningResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the UpdateClientWarning() method */
            commonApiWebProxy.UpdateClientWarning(entityId, warningId, updateWarningData);
            LOGGER.debug("updated client warning");
            return updateClientWarningResponse;
        } catch (Exception e) {
            LOGGER.warn("exception updating client warning: " + e.getMessage());
        }

        return null;
    }
}
