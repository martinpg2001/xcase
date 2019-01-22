/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.SetUserRolesRequest;
import com.xcase.open.transputs.SetUserRolesResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class SetUserRolesMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public SetUserRolesMethod() {
        super();
//        LOGGER.debug("finishing SetUserRolesMethod()");
    }

    public SetUserRolesResponse setUserRoles(SetUserRolesRequest setUserRolesRequest) {
        LOGGER.debug("starting setUserRoles()");
        try {
            String userId = setUserRolesRequest.getUserId();
            String[] roles = setUserRolesRequest.getRoleArray();
            SetUserRolesResponse setUserRolesResponse = OpenResponseFactory.createSetUserRolesResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the SetUserRoles() method */
            commonApiWebProxy.SetUserRoles(userId, roles);
            LOGGER.debug("set user roles");
            return setUserRolesResponse;
        } catch (Exception e) {
            LOGGER.warn("exception setting user roles: " + e.getMessage());
        }

        return null;
    }
}
