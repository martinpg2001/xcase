/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.CreateAliasData;
import com.xcase.open.transputs.CreateClientAliasRequest;
import com.xcase.open.transputs.CreateClientAliasResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreateClientAliasMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreateClientAliasMethod() {
        super();
//        LOGGER.debug("finishing CreateClientAliasMethod()");
    }

    public CreateClientAliasResponse createClientAlias(CreateClientAliasRequest createClientAliasRequest) {
        LOGGER.debug("starting createClientAlias()");
        try {
            String entityId = createClientAliasRequest.getEntityId();
            CreateAliasData createAliasData = createClientAliasRequest.getCreateAliasData();
            CreateClientAliasResponse createAliasResponse = OpenResponseFactory.createCreateClientAliasResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the CreateClientAlias() method */
            int aliasID = commonApiWebProxy.CreateClientAlias(entityId, createAliasData);
            LOGGER.debug("aliasID is " + aliasID);
            createAliasResponse.setId(aliasID);
            return createAliasResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating client alias: " + e.getMessage());
        }

        return null;
    }
}
