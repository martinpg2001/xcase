/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.DeleteEntitySecurityentityType;
import com.xcase.open.transputs.DeleteEntitySecurityRequest;
import com.xcase.open.transputs.DeleteEntitySecurityResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class DeleteEntitySecurityMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public DeleteEntitySecurityMethod() {
        super();
//        LOGGER.debug("finishing DeleteEntitySecurityMethod()");
    }

    public DeleteEntitySecurityResponse deleteEntitySecurity(DeleteEntitySecurityRequest deleteEntitySecurityRequest) {
        LOGGER.debug("starting deleteEntitySecurity()");
        try {
            DeleteEntitySecurityResponse deleteEntitySecurityResponse = OpenResponseFactory.createDeleteEntitySecurityResponse();
            DeleteEntitySecurityentityType deleteEntitySecurityentityType = deleteEntitySecurityRequest.getDeleteEntitySecurityentityType();
            String entityId = deleteEntitySecurityRequest.getEntityId();
            String[] groupIds = deleteEntitySecurityRequest.getGroupIds();
            String parentEntityId = deleteEntitySecurityRequest.getParentEntityId();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the DeleteEntitySecurityMethod() method */
//            commonApiWebProxy.DeleteEntitySecurity(entityId, deleteEntitySecurityentityType, groupIds, parentEntityId);
//            LOGGER.debug("deleted entity security");
            return deleteEntitySecurityResponse;
        } catch (Exception e) {
            LOGGER.warn("exception deleting entity security: " + e.getMessage());
        }

        return null;
    }
}
