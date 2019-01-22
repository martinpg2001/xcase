/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.UpdateAddressData;
import com.xcase.open.transputs.UpdateClientAddressRequest;
import com.xcase.open.transputs.UpdateClientAddressResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class UpdateClientAddressMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public UpdateClientAddressMethod() {
        super();
//        LOGGER.debug("finishing UpdateClientAddressMethod()");
    }

    public UpdateClientAddressResponse updateClientAddress(UpdateClientAddressRequest updateAddressRequest) {
        LOGGER.debug("starting updateClientAddress()");
        try {
            String entityId = updateAddressRequest.getEntityId();
            String addressType = updateAddressRequest.getAddressType();
            UpdateAddressData updateAddressData = updateAddressRequest.getUpdateAddressData();
            String remoteId = updateAddressRequest.getRemoteId();
            UpdateClientAddressResponse updateClientAddressResponse = OpenResponseFactory.createUpdateClientAddressResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the UpdateClientAddress() method */
            commonApiWebProxy.UpdateClientAddress(entityId, addressType, updateAddressData, remoteId);
            return updateClientAddressResponse;
        } catch (Exception e) {
            LOGGER.warn("exception updating client address: " + e.getMessage());
        }

        return null;
    }
}
