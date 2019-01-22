/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.UpdateWarningData;
import com.xcase.open.transputs.UpdatePartyWarningRequest;
import com.xcase.open.transputs.UpdatePartyWarningResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class UpdatePartyWarningMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public UpdatePartyWarningMethod() {
        super();
//        LOGGER.debug("finishing UpdatePartyWarningMethod()");
    }

    public UpdatePartyWarningResponse updatePartyWarning(UpdatePartyWarningRequest updatePartyWarningRequest) {
        LOGGER.debug("starting updatePartyWarningRequest()");
        try {
            String entityId = updatePartyWarningRequest.getEntityId();
            int warningId = updatePartyWarningRequest.getId();
            UpdateWarningData updateWarningData = updatePartyWarningRequest.getUpdateWarningData();
            UpdatePartyWarningResponse updatePartyWarningResponse = OpenResponseFactory.createUpdatePartyWarningResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the UpdatePartyWarning() method */
            commonApiWebProxy.UpdatePartyWarning(entityId, warningId, updateWarningData);
            LOGGER.debug("updated client warning");
            return updatePartyWarningResponse;
        } catch (Exception e) {
            LOGGER.warn("exception updating client warning: " + e.getMessage());
        }

        return null;
    }
}
