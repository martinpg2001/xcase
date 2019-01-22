/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.CreateWarningData;
import com.xcase.open.transputs.CreatePartyWarningRequest;
import com.xcase.open.transputs.CreatePartyWarningResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreatePartyWarningMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreatePartyWarningMethod() {
        super();
//        LOGGER.debug("finishing CreatePartyWarningMethod()");
    }

    public CreatePartyWarningResponse createPartyWarning(CreatePartyWarningRequest createEntityWarningRequest) {
        LOGGER.debug("starting createPartyWarning()");
        try {
            String entityId = createEntityWarningRequest.getEntityId();
            CreateWarningData createWarningData = createEntityWarningRequest.getCreateWarningData();
            CreatePartyWarningResponse createEntityWarningResponse = OpenResponseFactory.createCreatePartyWarningResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the CreatePartyWarning() method */
            int partyWarningID = commonApiWebProxy.CreatePartyWarning(entityId, createWarningData);
            LOGGER.debug("entityWarningID is " + partyWarningID);
            createEntityWarningResponse.setId(partyWarningID);
            return createEntityWarningResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating client warning: " + e.getMessage());
        }

        return null;
    }
}
