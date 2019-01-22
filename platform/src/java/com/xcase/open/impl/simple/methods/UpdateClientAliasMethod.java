/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.UpdateAliasData;
import com.xcase.open.transputs.UpdateClientAliasRequest;
import com.xcase.open.transputs.UpdateClientAliasResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class UpdateClientAliasMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public UpdateClientAliasMethod() {
        super();
//        LOGGER.debug("finishing UpdateClientAliasMethod()");
    }

    public UpdateClientAliasResponse updateClientAlias(UpdateClientAliasRequest updateClientAliasRequest) {
        LOGGER.debug("starting updateClientAlias()");
        try {
            String entityId = updateClientAliasRequest.getEntityId();
            String aliasName = updateClientAliasRequest.getAliasName();
            UpdateAliasData updateAliasData = updateClientAliasRequest.getUpdateAliasData();
            UpdateClientAliasResponse updateClientAliasResponse = OpenResponseFactory.createUpdateClientAliasResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the UpdateClientAlias() method */
            commonApiWebProxy.UpdateClientAlias(entityId, aliasName, updateAliasData);
            return updateClientAliasResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating client alias: " + e.getMessage());
        }

        return null;
    }
}
