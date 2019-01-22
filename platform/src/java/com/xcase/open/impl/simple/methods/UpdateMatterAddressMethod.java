/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.UpdateAddressData;
import com.xcase.open.transputs.UpdateMatterAddressRequest;
import com.xcase.open.transputs.UpdateMatterAddressResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class UpdateMatterAddressMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public UpdateMatterAddressMethod() {
        super();
//        LOGGER.debug("finishing UpdateMatterAddressMethod()");
    }

    public UpdateMatterAddressResponse updateMatterAddress(UpdateMatterAddressRequest updateMatterAddressRequest) {
        LOGGER.debug("starting updateMatterAddress()");
        try {
            String entityId = updateMatterAddressRequest.getEntityId();
            String parentEntityId = updateMatterAddressRequest.getParentEntityId();
            String addressType = updateMatterAddressRequest.getAddressType();
            UpdateAddressData updateAddressData = updateMatterAddressRequest.getUpdateAddressData();
            String remoteId = updateMatterAddressRequest.getRemoteId();
            UpdateMatterAddressResponse updateMatterAddressResponse = OpenResponseFactory.createUpdateMatterAddressResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the UpdateMatterAddress() method */
            commonApiWebProxy.UpdateMatterAddress(entityId, addressType, updateAddressData, parentEntityId, remoteId);
            return updateMatterAddressResponse;
        } catch (Exception e) {
            LOGGER.warn("exception updating matter address: " + e.getMessage());
        }

        return null;
    }
}
