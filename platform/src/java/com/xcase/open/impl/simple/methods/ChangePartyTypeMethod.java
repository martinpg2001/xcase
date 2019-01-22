/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.ChangePartyType;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.ChangePartyTypeRequest;
import com.xcase.open.transputs.ChangePartyTypeResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class ChangePartyTypeMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public ChangePartyTypeMethod() {
        super();
//        LOGGER.debug("finishing ChangePartyTypeMethod()");
    }

    public ChangePartyTypeResponse changePartyType(ChangePartyTypeRequest changePartyTypeRequest) {
        LOGGER.debug("starting changePartyType()");
        try {
            String partyId = changePartyTypeRequest.getPartyId();
            ChangePartyType changePartyType = changePartyTypeRequest.getChangePartyType();
            LOGGER.debug("got changePartyTypeData");
            ChangePartyTypeResponse changePartyTypeResponse = OpenResponseFactory.createChangePartyTypeResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the ChangePartyType() method */
//            commonApiWebProxy.ChangePartyType(partyId, changePartyType);
//            LOGGER.debug("changed party type");
            return changePartyTypeResponse;
        } catch (Exception e) {
            LOGGER.warn("exception changing party type: " + e.getMessage());
        }

        return null;
    }
}
