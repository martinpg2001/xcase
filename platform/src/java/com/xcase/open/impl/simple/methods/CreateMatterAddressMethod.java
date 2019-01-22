/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.CreateAddressData;
import com.xcase.open.transputs.CreateMatterAddressRequest;
import com.xcase.open.transputs.CreateMatterAddressResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreateMatterAddressMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreateMatterAddressMethod() {
        super();
//        LOGGER.debug("finishing CreateMatterAddressMethod()");
    }

    public CreateMatterAddressResponse createMatterAddress(CreateMatterAddressRequest createAddressRequest) {
        LOGGER.debug("starting createMatterAddress()");
        try {
            String entityId = createAddressRequest.getEntityId();
            CreateAddressData createAddressData = createAddressRequest.getCreateAddressData();
            String parentEntityId = createAddressRequest.getParentEntityId();
            CreateMatterAddressResponse createMatterAddressResponse = OpenResponseFactory.createCreateMatterAddressResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the CreateMatterAddress() method */
            int addressID = commonApiWebProxy.CreateMatterAddress(entityId, createAddressData, parentEntityId);
            LOGGER.debug("addressID is " + addressID);
            createMatterAddressResponse.setId(addressID);
            return createMatterAddressResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating matter address: " + e.getMessage());
        }

        return null;
    }
}
