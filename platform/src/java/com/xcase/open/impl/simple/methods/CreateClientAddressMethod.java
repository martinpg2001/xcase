/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.CreateAddressData;
import com.xcase.open.impl.simple.core.CreateAddressentityType;
import com.xcase.open.transputs.CreateClientAddressRequest;
import com.xcase.open.transputs.CreateClientAddressResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreateClientAddressMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreateClientAddressMethod() {
        super();
//        LOGGER.debug("finishing CreateClientAddressMethod()");
    }

    public CreateClientAddressResponse createClientAddress(CreateClientAddressRequest createAddressRequest) {
        LOGGER.debug("starting createClientAddress()");
        try {
            CreateAddressentityType createAddressentityType = createAddressRequest.getCreateAddressentityType();
            String entityId = createAddressRequest.getEntityId();
            CreateAddressData createAddressData = createAddressRequest.getCreateAddressData();
            String parentEntityId = createAddressRequest.getParentEntityId();
            CreateClientAddressResponse createAddressResponse = OpenResponseFactory.createCreateClientAddressResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the CreateClientAddress() method */
            int addressID = commonApiWebProxy.CreateClientAddress(entityId, createAddressData);
            LOGGER.debug("addressID is " + addressID);
            createAddressResponse.setId(addressID);
            return createAddressResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating client address: " + e.getMessage());
        }

        return null;
    }
}
