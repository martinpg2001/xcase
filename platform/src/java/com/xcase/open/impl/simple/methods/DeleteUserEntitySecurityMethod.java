/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.DeleteUserEntitySecurityentityType;
import com.xcase.open.transputs.DeleteUserEntitySecurityRequest;
import com.xcase.open.transputs.DeleteUserEntitySecurityResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class DeleteUserEntitySecurityMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public DeleteUserEntitySecurityMethod() {
        super();
//        LOGGER.debug("finishing DeleteUserEntitySecurityMethod()");
    }

    public DeleteUserEntitySecurityResponse deleteUserEntitySecurity(DeleteUserEntitySecurityRequest deleteUserEntitySecurityRequest) {
        LOGGER.debug("starting deleteUserEntitySecurity()");
        try {
            DeleteUserEntitySecurityResponse deleteUserEntitySecurityResponse = OpenResponseFactory.createDeleteUserEntitySecurityResponse();
            DeleteUserEntitySecurityentityType deleteUserEntitySecurityentityType = deleteUserEntitySecurityRequest.getDeleteUserEntitySecurityentityType();
            String entityId = deleteUserEntitySecurityRequest.getEntityId();
            String[] userIds = deleteUserEntitySecurityRequest.getUserIds();
            String parentEntityId = deleteUserEntitySecurityRequest.getParentEntityId();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the DeleteUserEntitySecurityMethod() method */
//            commonApiWebProxy.DeleteUserEntitySecurity(entityId, deleteUserEntitySecurityentityType, userIds, parentEntityId);
//            LOGGER.debug("deleted user entity security");
            return deleteUserEntitySecurityResponse;
        } catch (Exception e) {
            LOGGER.warn("exception deleting user entity security: " + e.getMessage());
        }

        return null;
    }
}
