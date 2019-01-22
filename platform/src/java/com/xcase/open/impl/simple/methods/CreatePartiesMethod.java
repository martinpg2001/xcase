/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.CreatePartyData;
import com.xcase.open.transputs.CreatePartiesRequest;
import com.xcase.open.transputs.CreatePartiesResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreatePartiesMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreatePartiesMethod() {
        super();
//        LOGGER.debug("finishing CreatePartiesMethod()");
    }

    public CreatePartiesResponse createParties(CreatePartiesRequest createPartiesRequest) {
        LOGGER.debug("starting createParties()");
        try {
            CreatePartyData[] createPartyDataArray = createPartiesRequest.getCreatePartyDataArray();
            LOGGER.debug("got createPartyDataArray");
            CreatePartiesResponse createPartiesResponse = OpenResponseFactory.createCreatePartiesResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the CreateParties() method */
            commonApiWebProxy.CreateParties(createPartyDataArray);
            LOGGER.debug("created parties");
            return createPartiesResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating parties: " + e.getMessage());
        }

        return null;
    }
}
