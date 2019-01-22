/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.UpdateMatterData;
import com.xcase.open.transputs.UpdateMattersRequest;
import com.xcase.open.transputs.UpdateMattersResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class UpdateMattersMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public UpdateMattersMethod() {
        super();
//        LOGGER.debug("finishing UpdateMattersMethod()");
    }

    public UpdateMattersResponse updateMatters(UpdateMattersRequest updateMattersRequest) {
        LOGGER.debug("starting updateMatters()");
        try {
            UpdateMatterData[] updateMatterDataArray = updateMattersRequest.getUpdateMatterDataArray();
            LOGGER.debug("got updateMatterDataArray");
            UpdateMattersResponse updateMattersResponse = OpenResponseFactory.createUpdateMattersResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the UpdateMatters() method */
            commonApiWebProxy.UpdateMatters(updateMatterDataArray);
            LOGGER.debug("updated matters");
            return updateMattersResponse;
        } catch (Exception e) {
            LOGGER.warn("exception updating matters: " + e.getMessage());
        }

        return null;
    }
}
