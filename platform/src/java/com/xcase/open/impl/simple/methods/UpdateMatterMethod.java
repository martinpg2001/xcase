/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.UpdateMatterData;
import com.xcase.open.transputs.UpdateMatterRequest;
import com.xcase.open.transputs.UpdateMatterResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class UpdateMatterMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public UpdateMatterMethod() {
        super();
//        LOGGER.debug("finishing UpdateMatterMethod()");
    }

    public UpdateMatterResponse updateMatter(UpdateMatterRequest updateMatterRequest) {
        LOGGER.debug("starting updateMatter()");
        try {
            String clientId = updateMatterRequest.getClientId();
            String matterId = updateMatterRequest.getMatterId();
            UpdateMatterData updateMatterData = updateMatterRequest.getUpdateMatterData();
            LOGGER.debug("got updateMatterData");
            UpdateMatterResponse updateMatterResponse = OpenResponseFactory.createUpdateMatterResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the UpdateMatter() method */
            commonApiWebProxy.UpdateMatter(matterId, updateMatterData, clientId);
            LOGGER.debug("updated matter");
            return updateMatterResponse;
        } catch (Exception e) {
            LOGGER.warn("exception updating matter: " + e.getMessage());
        }

        return null;
    }
}
