/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.UpdatePartyData;
import com.xcase.open.transputs.UpdatePartyRequest;
import com.xcase.open.transputs.UpdatePartyResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class UpdatePartyMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public UpdatePartyMethod() {
        super();
//        LOGGER.debug("finishing UpdatePartyMethod()");
    }

    public UpdatePartyResponse updateParty(UpdatePartyRequest updatePartyRequest) {
        LOGGER.debug("starting updateParty()");
        try {
            String partyId = updatePartyRequest.getPartyId();
            UpdatePartyData updatePartyData = updatePartyRequest.getUpdatePartyData();
            LOGGER.debug("got updatePartyData");
            UpdatePartyResponse updatePartyResponse = OpenResponseFactory.createUpdatePartyResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the UpdateParty() method */
            commonApiWebProxy.UpdateParty(partyId, updatePartyData);
            LOGGER.debug("updated party");
            return updatePartyResponse;
        } catch (Exception e) {
            LOGGER.warn("exception updating party: " + e.getMessage());
        }

        return null;
    }
}
