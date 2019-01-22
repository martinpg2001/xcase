/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.CreateWarningData;
import com.xcase.open.transputs.CreateMatterWarningRequest;
import com.xcase.open.transputs.CreateMatterWarningResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreateMatterWarningMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreateMatterWarningMethod() {
        super();
//        LOGGER.debug("finishing CreateMatterWarningMethod()");
    }

    public CreateMatterWarningResponse createMatterWarning(CreateMatterWarningRequest createEntityWarningRequest) {
        LOGGER.debug("starting createMatterWarning()");
        try {
            String entityId = createEntityWarningRequest.getEntityId();
            CreateWarningData createWarningData = createEntityWarningRequest.getCreateWarningData();
            String parentEntityId = createEntityWarningRequest.getParentEntityId();
            CreateMatterWarningResponse createMatterWarningResponse = OpenResponseFactory.createCreateMatterWarningResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the CreateMatterWarning() method */
            int entityWarningID = commonApiWebProxy.CreateMatterWarning(entityId, createWarningData, parentEntityId);
            LOGGER.debug("entityWarningID is " + entityWarningID);
            createMatterWarningResponse.setId(entityWarningID);
            return createMatterWarningResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating matter warning: " + e.getMessage());
        }

        return null;
    }
}
