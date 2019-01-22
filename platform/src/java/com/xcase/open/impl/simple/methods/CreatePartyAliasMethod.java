/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.CreateAliasData;
import com.xcase.open.transputs.CreatePartyAliasRequest;
import com.xcase.open.transputs.CreatePartyAliasResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreatePartyAliasMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreatePartyAliasMethod() {
        super();
//        LOGGER.debug("finishing CreatePartyAliasMethod()");
    }

    public CreatePartyAliasResponse createPartyAlias(CreatePartyAliasRequest createAliasRequest) {
        LOGGER.debug("starting createPartyAlias()");
        try {
            String entityId = createAliasRequest.getEntityId();
            CreateAliasData createAliasData = createAliasRequest.getCreateAliasData();
            CreatePartyAliasResponse createAliasResponse = OpenResponseFactory.createCreatePartyAliasResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the CreatePartyAlias() method */
            int aliasID = commonApiWebProxy.CreatePartyAlias(entityId, createAliasData);
            LOGGER.debug("aliasID is " + aliasID);
            createAliasResponse.setId(aliasID);
            return createAliasResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating party alias: " + e.getMessage());
        }

        return null;
    }
}
