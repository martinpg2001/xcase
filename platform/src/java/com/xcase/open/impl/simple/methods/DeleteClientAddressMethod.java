/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.DeleteAddressentityType;
import com.xcase.open.transputs.DeleteClientAddressRequest;
import com.xcase.open.transputs.DeleteClientAddressResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class DeleteClientAddressMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public DeleteClientAddressMethod() {
        super();
//        LOGGER.debug("finishing DeleteAddressMethod()");
    }

    public DeleteClientAddressResponse deleteAddress(DeleteClientAddressRequest deleteAddressRequest) {
        LOGGER.debug("starting deleteAddress()");
        try {
            DeleteAddressentityType deleteAddressentityType = deleteAddressRequest.getDeleteAddressentityType();
            String entityId = deleteAddressRequest.getEntityId();
            String addressType = deleteAddressRequest.getAddressType();
            String parentEntityId = deleteAddressRequest.getParentEntityId();
            String remoteId = deleteAddressRequest.getRemoteId();
            DeleteClientAddressResponse deleteAddressResponse = OpenResponseFactory.createDeleteAddressResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the DeleteClientAddress() method */
            commonApiWebProxy.DeleteClientAddress(entityId, addressType, remoteId);
            return deleteAddressResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating address: " + e.getMessage());
        }

        return null;
    }
}
