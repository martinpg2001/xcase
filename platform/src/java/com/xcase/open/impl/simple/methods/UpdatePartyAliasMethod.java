/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.UpdateAliasData;
import com.xcase.open.transputs.UpdatePartyAliasRequest;
import com.xcase.open.transputs.UpdatePartyAliasResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class UpdatePartyAliasMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public UpdatePartyAliasMethod() {
        super();
//        LOGGER.debug("finishing UpdatePartyAliasMethod()");
    }

    public UpdatePartyAliasResponse updatePartyAlias(UpdatePartyAliasRequest updatePartyAliasRequest) {
        LOGGER.debug("starting updatePartyAlias()");
        try {
            String entityId = updatePartyAliasRequest.getEntityId();
            String aliasName = updatePartyAliasRequest.getAliasName();
            UpdateAliasData updateAliasData = updatePartyAliasRequest.getUpdateAliasData();
            UpdatePartyAliasResponse updatePartyAliasResponse = OpenResponseFactory.createUpdatePartyAliasResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the UpdatePartyAlias() method */
            commonApiWebProxy.UpdatePartyAlias(entityId, aliasName, updateAliasData);
            return updatePartyAliasResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating client alias: " + e.getMessage());
        }

        return null;
    }
}
