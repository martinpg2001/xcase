/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.DeleteClientWarningRequest;
import com.xcase.open.transputs.DeleteClientWarningResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class DeleteClientWarningMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public DeleteClientWarningMethod() {
        super();
//        LOGGER.debug("finishing DeleteClientWarningMethod()");
    }

    public DeleteClientWarningResponse deleteClientWarning(DeleteClientWarningRequest deleteEntityWarningRequest) {
        LOGGER.debug("starting deleteClientWarning()");
        try {
            DeleteClientWarningResponse deleteEntityWarningResponse = OpenResponseFactory.createDeleteEntityWarningResponse();
            String entityId = deleteEntityWarningRequest.getEntityId();
            int warningId = deleteEntityWarningRequest.getWarningId();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the DeleteClientWarning() method */
            commonApiWebProxy.DeleteClientWarning(entityId, warningId);
            LOGGER.debug("deleted client warning");
            return deleteEntityWarningResponse;
        } catch (Exception e) {
            LOGGER.warn("exception deleting client warning: " + e.getMessage());
        }

        return null;
    }
}
