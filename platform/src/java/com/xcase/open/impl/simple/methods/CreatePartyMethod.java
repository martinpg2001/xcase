/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.CreatePartyData;
import com.xcase.open.transputs.CreatePartyRequest;
import com.xcase.open.transputs.CreatePartyResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreatePartyMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreatePartyMethod() {
        super();
//        LOGGER.debug("finishing CreatePartyMethod()");
    }

    public CreatePartyResponse createParty(CreatePartyRequest createPartyRequest) {
        LOGGER.debug("starting createParty()");
        try {
            CreatePartyData createPartyData = createPartyRequest.getCreatePartyData();
            LOGGER.debug("got createPartyData");
            CreatePartyResponse createPartyResponse = OpenResponseFactory.createCreatePartyResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the CreateParty() method */
            int partyID = commonApiWebProxy.CreateParty(createPartyData);
            LOGGER.debug("partyID is " + partyID);
            createPartyResponse.setId(partyID);
            return createPartyResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating party: " + e.getMessage());
        }

        return null;
    }
}
