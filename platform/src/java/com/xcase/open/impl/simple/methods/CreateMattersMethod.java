/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.CreateMatterData;
import com.xcase.open.transputs.CreateMattersRequest;
import com.xcase.open.transputs.CreateMattersResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreateMattersMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreateMattersMethod() {
        super();
//        LOGGER.debug("finishing CreateMattersMethod()");
    }

    public CreateMattersResponse createMatters(CreateMattersRequest createMattersRequest) {
        LOGGER.debug("starting createMatters()");
        try {
            CreateMatterData[] createMatterDataArray = createMattersRequest.getCreateMatterDataArray();
            LOGGER.debug("got createMatterDataArray");
            CreateMattersResponse createMattersResponse = OpenResponseFactory.createCreateMattersResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the CreateMatters() method */
            commonApiWebProxy.CreateMatters(createMatterDataArray);
            LOGGER.debug("created matters");
            return createMattersResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating matters: " + e.getMessage());
        }

        return null;
    }
}
