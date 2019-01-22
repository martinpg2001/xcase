/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.DeleteAliasentityType;
import com.xcase.open.transputs.DeleteClientAliasRequest;
import com.xcase.open.transputs.DeleteClientAliasResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class DeleteClientAliasMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public DeleteClientAliasMethod() {
        super();
//        LOGGER.debug("finishing DeleteAliasMethod()");
    }

    public DeleteClientAliasResponse deleteAlias(DeleteClientAliasRequest deleteAliasRequest) {
        LOGGER.debug("starting deleteAlias()");
        try {
            DeleteClientAliasResponse deleteAliasResponse = OpenResponseFactory.createDeleteAliasResponse();
            DeleteAliasentityType deleteAliasentityType = deleteAliasRequest.getDeleteAliasentityType();
            String entityId = deleteAliasRequest.getEntityId();
            String aliasName = deleteAliasRequest.getAliasName();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the DeleteClientAlias() method */
            commonApiWebProxy.DeleteClientAlias(entityId, aliasName);
            LOGGER.debug("deleted client alias");
            return deleteAliasResponse;
        } catch (Exception e) {
            LOGGER.warn("exception deleting alias: " + e.getMessage());
        }

        return null;
    }
}
