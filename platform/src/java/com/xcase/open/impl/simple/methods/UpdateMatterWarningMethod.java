/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.UpdateWarningData;
import com.xcase.open.transputs.UpdateMatterWarningRequest;
import com.xcase.open.transputs.UpdateMatterWarningResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class UpdateMatterWarningMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public UpdateMatterWarningMethod() {
        super();
//        LOGGER.debug("finishing UpdateMatterWarningMethod()");
    }

    public UpdateMatterWarningResponse updateMatterWarning(UpdateMatterWarningRequest updateMatterWarningRequest) {
        LOGGER.debug("starting updateMatterWarning()");
        try {
            String entityId = updateMatterWarningRequest.getEntityId();
            int warningId = updateMatterWarningRequest.getId();
            UpdateWarningData updateWarningData = updateMatterWarningRequest.getUpdateWarningData();
            String parentEntityId = updateMatterWarningRequest.getParentEntityId();
            UpdateMatterWarningResponse updateEntityWarningResponse = OpenResponseFactory.createUpdateMatterWarningResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the UpdateMatterWarning() method */
            commonApiWebProxy.UpdateMatterWarning(entityId, warningId, updateWarningData, parentEntityId);
            LOGGER.debug("updated matter warning");
            return updateEntityWarningResponse;
        } catch (Exception e) {
            LOGGER.warn("exception updating matter warning: " + e.getMessage());
        }

        return null;
    }
}
