/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.RemoveUserRolesRequest;
import com.xcase.open.transputs.RemoveUserRolesResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class RemoveUserRolesMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public RemoveUserRolesMethod() {
        super();
//        LOGGER.debug("finishing RemoveUserRolesMethod()");
    }

    public RemoveUserRolesResponse removeUserRoles(RemoveUserRolesRequest removeUserRolesRequest) {
        LOGGER.debug("starting removeUserRoles()");
        try {
            String userId = removeUserRolesRequest.getUserId();
            String[] roles = removeUserRolesRequest.getRoleArray();
            RemoveUserRolesResponse removeUserRolesResponse = OpenResponseFactory.createRemoveUserRolesResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the RemoveUserRoles() method */
            commonApiWebProxy.RemoveUserRoles(userId, roles);
            LOGGER.debug("removed user roles");
            return removeUserRolesResponse;
        } catch (Exception e) {
            LOGGER.warn("exception removing user roles: " + e.getMessage());
        }

        return null;
    }
}
