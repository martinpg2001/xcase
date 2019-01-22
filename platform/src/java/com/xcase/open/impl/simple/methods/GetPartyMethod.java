/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.PartyData;
import com.xcase.open.transputs.GetPartyRequest;
import com.xcase.open.transputs.GetPartyResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetPartyMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetPartyMethod() {
        super();
//        LOGGER.debug("finishing GetPartyMethod()");
    }

    public GetPartyResponse getParty(GetPartyRequest getPartyRequest) {
        LOGGER.debug("starting getParty()");
        try {
            GetPartyResponse getPartyResponse = OpenResponseFactory.createGetPartyResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            String partyId = getPartyRequest.getPartyId();
            String[] properties = getPartyRequest.getProperties();
            /* Invoke the GetParty() method */
            PartyData partyData = commonApiWebProxy.GetParty(partyId, properties);
            LOGGER.debug("got client");
            getPartyResponse.setPartyData(partyData);
            return getPartyResponse;
        } catch (Exception e) {
            LOGGER.warn("exception getting party: " + e.getMessage());
        }

        return null;
    }
}
