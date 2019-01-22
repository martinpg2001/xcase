/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.UpdateAddressData;
import com.xcase.open.transputs.UpdatePartyAddressRequest;
import com.xcase.open.transputs.UpdatePartyAddressResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class UpdatePartyAddressMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public UpdatePartyAddressMethod() {
        super();
//        LOGGER.debug("finishing UpdatePartyAddressMethod()");
    }

    public UpdatePartyAddressResponse updatePartyAddress(UpdatePartyAddressRequest updateAddressRequest) {
        LOGGER.debug("starting updatePartyAddress()");
        try {
            String entityId = updateAddressRequest.getEntityId();
            String addressType = updateAddressRequest.getAddressType();
            UpdateAddressData updateAddressData = updateAddressRequest.getUpdateAddressData();
            String remoteId = updateAddressRequest.getRemoteId();
            UpdatePartyAddressResponse updatePartyAddressResponse = OpenResponseFactory.createUpdatePartyAddressResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the UpdatePartyAddress() method */
            commonApiWebProxy.UpdatePartyAddress(entityId, addressType, updateAddressData, remoteId);
            return updatePartyAddressResponse;
        } catch (Exception e) {
            LOGGER.warn("exception updating client address: " + e.getMessage());
        }

        return null;
    }
}
