/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.AddUserRolesRequest;
import com.xcase.open.transputs.AddUserRolesResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class AddUserRolesMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public AddUserRolesMethod() {
        super();
//        LOGGER.debug("finishing AddUserRolesMethod()");
    }

    public AddUserRolesResponse addUserRoles(AddUserRolesRequest addUserRolesRequest) {
        LOGGER.debug("starting addUserRoles()");
        try {
            String userId = addUserRolesRequest.getUserId();
            String[] roles = addUserRolesRequest.getRoleArray();
            AddUserRolesResponse addUserRolesResponse = OpenResponseFactory.createAddUserRolesResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the AddUserRoles() method */
            commonApiWebProxy.AddUserRoles(userId, roles, null);
            LOGGER.debug("added user roles");
            return addUserRolesResponse;
        } catch (Exception e) {
            LOGGER.warn("exception adding user roles: " + e.getMessage());
        }

        return null;
    }
}
