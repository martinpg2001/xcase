/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.CreateAddressData;
import com.xcase.open.transputs.CreatePartyAddressRequest;
import com.xcase.open.transputs.CreatePartyAddressResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreatePartyAddressMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreatePartyAddressMethod() {
        super();
//        LOGGER.debug("finishing CreatePartyAddressMethod()");
    }

    public CreatePartyAddressResponse createPartyAddress(CreatePartyAddressRequest createAddressRequest) {
        LOGGER.debug("starting createPartyAddress()");
        try {
            String entityId = createAddressRequest.getEntityId();
            CreateAddressData createAddressData = createAddressRequest.getCreateAddressData();
            CreatePartyAddressResponse createAddressResponse = OpenResponseFactory.createCreatePartyAddressResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the CreatePartyAddress() method */
            int addressID = commonApiWebProxy.CreatePartyAddress(entityId, createAddressData);
            LOGGER.debug("addressID is " + addressID);
            createAddressResponse.setId(addressID);
            return createAddressResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating party address: " + e.getMessage());
        }

        return null;
    }
}
