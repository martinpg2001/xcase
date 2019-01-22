/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.CreateMatterData;
import com.xcase.open.transputs.CreateMatterRequest;
import com.xcase.open.transputs.CreateMatterResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreateMatterMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreateMatterMethod() {
        super();
//        LOGGER.debug("finishing CreateMatterMethod()");
    }

    public CreateMatterResponse createMatter(CreateMatterRequest createMatterRequest) {
        LOGGER.debug("starting createMatter()");
        try {
            CreateMatterData createMatterData = createMatterRequest.getCreateMatterData();
            LOGGER.debug("got createMatterData");
            CreateMatterResponse createMatterResponse = OpenResponseFactory.createCreateMatterResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the CreateMatter() method */
            int matterID = commonApiWebProxy.CreateMatter(createMatterData);
            LOGGER.debug("matterID is " + matterID);
            createMatterResponse.setId(matterID);
            return createMatterResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating matter: " + e.getMessage());
        }

        return null;
    }
}
